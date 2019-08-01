package com.didik.mcd.presentation.tv;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.model.TvShow;
import com.didik.mcd.model.TvShowResponse;
import com.didik.mcd.rest.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowViewModel extends AndroidViewModel {
    private MutableLiveData<List<TvShow>> tvShows = new MutableLiveData<>();
    private MutableLiveData<Boolean> stateLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public TvShowViewModel(@NonNull Application application) {
        super(application);
    }

    void fetchTvShows() {
        stateLoading.setValue(true);
        final ApiClient apiClient = new ApiClient();
        apiClient.getClient().getTvShows(BuildConfig.API_KEY, "en-US")
                .enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowResponse> call,
                                           @NonNull Response<TvShowResponse> response) {
                        stateLoading.setValue(false);
                        if (response.body() != null) {
                            tvShows.postValue(response.body().getResults());
                        } else {
                            errorMessage.setValue(getApplication()
                                    .getString(R.string.tv_show_not_available));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowResponse> call,
                                          @NonNull Throwable throwable) {
                        Log.d("onFailure", throwable.getMessage());
                        stateLoading.setValue(false);
                        errorMessage.setValue(getApplication()
                                .getString(R.string.failed_load_tv_show));
                    }
                });
    }

    LiveData<List<TvShow>> getTvShows() {
        return tvShows;
    }

    LiveData<Boolean> getStateLoading() {
        return stateLoading;
    }

    LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
