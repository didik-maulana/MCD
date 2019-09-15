package com.didik.mcd.ui.movie.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.data.entity.MovieDetail;
import com.didik.mcd.rest.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailViewModel extends AndroidViewModel {
    private MutableLiveData<MovieDetail> movie = new MutableLiveData<>();
    private MutableLiveData<Boolean> stateLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
    }

    void fetchMovieDetail(Integer id) {
        stateLoading.setValue(true);
        final ApiClient apiClient = new ApiClient();
        apiClient.getClient()
                .getMovieDetail(id, BuildConfig.API_KEY, "en-US")
                .enqueue(new Callback<MovieDetail>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieDetail> call,
                                           @NonNull Response<MovieDetail> response) {
                        stateLoading.setValue(false);
                        if (response.body() != null) movie.postValue(response.body());
                        else
                            errorMessage.setValue(getApplication().getString(R.string.movies_not_available));
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieDetail> call,
                                          @NonNull Throwable throwable) {
                        stateLoading.setValue(false);
                        errorMessage.setValue(getApplication().getString(R.string.failed_load_movies));
                    }
                });
    }

    LiveData<MovieDetail> getMovieDetail() {
        return movie;
    }

    LiveData<Boolean> getStateLoading() {
        return stateLoading;
    }

    LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
