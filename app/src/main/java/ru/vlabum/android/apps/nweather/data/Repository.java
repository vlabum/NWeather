package ru.vlabum.android.apps.nweather.data;

public class Repository {

    public Boolean isLoadIcon = true;

    public Boolean isForecast5 = false;

    public Repository() {
    }

    public Boolean getLoadIcon() {
        return isLoadIcon;
    }

    public Boolean getForecast5() {
        return isForecast5;
    }

}
