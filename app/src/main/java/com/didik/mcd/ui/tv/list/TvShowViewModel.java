package com.didik.mcd.ui.tv.list;

import android.app.Application;

import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.data.entity.TvShow;
import com.didik.mcd.data.entity.TvShowResponse;
import com.didik.mcd.rest.ApiClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
        apiClient.getClient()
                .getTvShows(BuildConfig.API_KEY, "en-US")
                .enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowResponse> call,
                                           @NonNull Response<TvShowResponse> response) {
                        stateLoading.setValue(false);
                        if (response.body() != null) {
                            tvShows.postValue(response.body().getResults());
                        } else {
                            errorMessage.setValue(
                                    getApplication().getString(R.string.tv_show_not_available));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowResponse> call,
                                          @NonNull Throwable throwable) {
                        stateLoading.setValue(false);
                        errorMessage
                                .setValue(getApplication().getString(R.string.failed_load_tv_show));
                    }
                });
    }

    void fetchSearchTvShows(String query) {
        stateLoading.setValue(true);
        final ApiClient apiClient = new ApiClient();
        apiClient.getClient().getSearchTvShows(BuildConfig.API_KEY, "en-US", query)
                .enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowResponse> call,
                                           @NonNull Response<TvShowResponse> response) {
                        if (response.body() != null) {
                            tvShows.postValue(response.body().getResults());
                        } else {
                            errorMessage.setValue(
                                    getApplication().getString(R.string.tv_show_not_available));
                        }
                        stateLoading.setValue(false);
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowResponse> call,
                                          @NonNull Throwable throwable) {
                        errorMessage
                                .setValue(getApplication().getString(R.string.failed_load_tv_show));
                        stateLoading.setValue(false);
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
