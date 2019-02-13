package ru.vlabum.android.apps.nweather;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import ru.vlabum.android.apps.nweather.MediaPlayer.AudioPlayer;
import ru.vlabum.android.apps.nweather.MediaPlayer.AudioService;
import ru.vlabum.android.apps.nweather.data.Repository;

public class App extends Application {

    private static App instance;

    private Repository repository;

    private AudioPlayer audioPlayer;

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

}
