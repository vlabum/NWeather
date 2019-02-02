package ru.vlabum.android.apps.nweather;

import android.app.Application;

public class App extends Application {

    private static App instance;

    private Repository repository;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        repository = new Repository();
    }

    public Repository getRepository() {
        return repository;
    }
}
