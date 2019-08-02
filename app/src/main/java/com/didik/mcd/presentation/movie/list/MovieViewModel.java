package com.didik.mcd.presentation.movie.list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.model.Movie;
import com.didik.mcd.model.MovieResponse;
import com.didik.mcd.rest.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MovieViewModel extends AndroidViewModel {
    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private MutableLiveData<Boolean> stateLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MovieViewModel(@NonNull Application application) {
        super(application);
    }

    void fetchMovies() {
        stateLoading.setValue(true);
        final ApiClient apiClient = new ApiClient();
        apiClient.getClient()
                .getMovies(BuildConfig.API_KEY, "en-US")
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call,
                                           @NonNull Response<MovieResponse> response) {
                        stateLoading.setValue(false);
                        if (response.body() != null) {
                            movies.postValue(response.body().getResults());
                        } else {
                            errorMessage.setValue(getApplication().getString(R.string.movies_not_available));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call,
                                          @NonNull Throwable throwable) {
                        errorMessage.setValue(getApplication().getString(R.string.failed_load_movies));
                        stateLoading.setValue(false);
                    }
                });
    }

    LiveData<List<Movie>> getMovies() {
        return movies;
    }

    LiveData<Boolean> getStateLoading() {
        return stateLoading;
    }

    LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
