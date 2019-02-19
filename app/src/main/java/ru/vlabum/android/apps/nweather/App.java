package ru.vlabum.android.apps.nweather;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.vlabum.android.apps.nweather.MediaPlayer.AudioPlayer;
import ru.vlabum.android.apps.nweather.MediaPlayer.AudioService;
import ru.vlabum.android.apps.nweather.data.Repository;

public class App extends Application {

    private static App instance;

    private Repository repository;

    private AudioPlayer audioPlayer;

    private Retrofit retrofitCurrent;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        repository = new Repository();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(instance, AudioService.class));
        } else {
            startService(new Intent(instance, AudioService.class));
        }
        retrofitCurrent = new Retrofit.Builder()
                .baseUrl(DataStorage.REQ_WEATHER)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public Repository getRepository() {
        return repository;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public void setAudioPlayer(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    public Retrofit getRetrofitCurrent() {
        return retrofitCurrent;
    }

}
