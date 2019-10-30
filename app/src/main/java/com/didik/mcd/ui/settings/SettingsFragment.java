package com.didik.mcd.ui.settings;

import android.os.Bundle;
import android.widget.Toast;

import com.didik.mcd.BuildConfig;
import com.didik.mcd.R;
import com.didik.mcd.data.entity.Movie;
import com.didik.mcd.data.entity.MovieResponse;
import com.didik.mcd.data.entity.TvShow;
import com.didik.mcd.rest.ApiClient;
import com.didik.mcd.service.DailyReminderReceiver;
import com.didik.mcd.service.ReleaseReminderReceiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {
    private static final String KEY_DAILY_REMINDER = "daily_reminder";
    private static final String KEY_RELEASE_REMINDER = "release_reminder";
    private SwitchPreferenceCompat switchDailyReminder;
    private SwitchPreferenceCompat switchReleaseReminder;
    private DailyReminderReceiver dailyReminderReceiver;
    private ReleaseReminderReceiver releaseReminderReceiver;
    private List<Movie> movieList = new ArrayList<>();
    private List<TvShow> tvShowList = new ArrayList<>();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dailyReminderReceiver = new DailyReminderReceiver();
        releaseReminderReceiver = new ReleaseReminderReceiver();

        switchDailyReminder = findPreference(KEY_DAILY_REMINDER);
        switchReleaseReminder = findPreference(KEY_RELEASE_REMINDER);

        switchDailyReminder.setOnPreferenceChangeListener(this);
        switchReleaseReminder.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object object) {
        String prefKey = preference.getKey();
        boolean isActive = (boolean) object;

        if (prefKey.equals(KEY_DAILY_REMINDER)) {
            if (isActive) {
                dailyReminderReceiver.setRepeatingReminder(getActivity());
                showMessage("Daily Reminder Active");
            } else {
                dailyReminderReceiver.dismissReminder(getActivity());
                showMessage("Daily Reminder Not Active");
            }
        } else {
            if (isActive) {
                fetchMovieRelease();
                showMessage("Release Reminder Active");
            } else {
                releaseReminderReceiver.dismissReminder(getActivity());
                showMessage("Release Reminder Non Active");
            }
        }
        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void fetchMovieRelease() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String currentDate = dateFormat.format(date);

        final ApiClient apiClient = new ApiClient();
        apiClient.getClient()
                .getMovieRelease(BuildConfig.API_KEY, currentDate, currentDate)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call,
                                           Response<MovieResponse> response) {
                        for (Movie movie : response.body().getResults()) {
                            if (movie.getReleaseDate().equals(currentDate)) {
                                setMovieReminder(movie);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        showMessage("Gagal mengambil data release film");
                        t.printStackTrace();
                    }
                });
    }

    private void setMovieReminder(Movie movies) {
        movieList.clear();
        movieList.add(movies);
        releaseReminderReceiver.setMovieReminder(getActivity(), movieList);
    }
}
