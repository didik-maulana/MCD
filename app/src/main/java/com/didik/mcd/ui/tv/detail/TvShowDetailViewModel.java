package com.didik.mcd.ui.tv.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.data.entity.TvShowDetail;
import com.didik.mcd.rest.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailViewModel extends AndroidViewModel {
    private MutableLiveData<TvShowDetail> tvShow = new MutableLiveData<>();
    private MutableLiveData<Boolean> stateLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public TvShowDetailViewModel(@NonNull Application application) {
        super(application);
    }

    void fetchTvShowDetail(Integer id) {
        stateLoading.setValue(true);
        ApiClient apiClient = new ApiClient();
        apiClient.getClient()
                .getTvShowDetail(id, BuildConfig.API_KEY, "en-US")
                .enqueue(new Callback<TvShowDetail>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowDetail> call,
                                           @NonNull Response<TvShowDetail> response) {
                        stateLoading.setValue(false);
                        if (response.body() != null)
                            tvShow.postValue(response.body());
                        else
                            errorMessage.setValue(
                                    getApplication().getString(R.string.tv_show_not_available));
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowDetail> call,
                                          @NonNull Throwable throwable) {
                        stateLoading.setValue(false);
                        errorMessage
                                .setValue(getApplication().getString(R.string.failed_load_tv_show));
                    }
                });
    }

    LiveData<TvShowDetail> getTvShow() {
        return tvShow;
    }

    LiveData<Boolean> getStateLoading() {
        return stateLoading;
    }

    LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
