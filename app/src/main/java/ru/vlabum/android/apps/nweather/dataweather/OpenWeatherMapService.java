package ru.vlabum.android.apps.nweather.dataweather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vlabum.android.apps.nweather.DataStorage;

public interface OpenWeatherMapService {

    @GET(DataStorage.REQ_WEATHER_CURRENT)
    Call<WeatherCurrent> getWeatherCurrent(
            @Query("appid") String appid,
            @Query("q") String city,
            @Query("lang") String lang
    );

    @GET(DataStorage.REQ_WEATHER_CURRENT)
    Call<WeatherCurrent> getWeatherCurrentCoord(
            @Query("appid") String appid,
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("lang") String lang
    );

}
