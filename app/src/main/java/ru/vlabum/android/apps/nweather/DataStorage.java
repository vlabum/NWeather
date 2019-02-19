package ru.vlabum.android.apps.nweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.android.apps.nweather.dataweather.OpenWeatherMap;
import ru.vlabum.android.apps.nweather.dataweather.WeatherCurrent;
import ru.vlabum.android.apps.nweather.dataweather.WeatherForecast;

import java.io.IOException;

/**
 * Класс содержит данные для запроса информации о погоде
 * Информацию о погоде
 */
public class DataStorage {

    public static final String REQ_WEATHER = "https://api.openweathermap.org/data/2.5/";
    public static final String REQ_WEATHER_CURRENT = "weather";
    public static final String REQ_WEATHER_FORECAST = "forecast";
    private static final String REQ_WEATHER_IMAGE = "https://openweathermap.org/img/w/";

    @Nullable
    private final String APPID = "634e19e819dc6d0d194b4b71b82d1e63";

    @Nullable
    public String getAPPID() {
        return APPID;
    }

    private static volatile DataStorage instance;

    @Nullable
    private WeatherCurrent weatherCurrent;
    @Nullable
    private WeatherForecast weatherForecast;
    @Nullable
    private String weatherCurrentResp;
    @Nullable
    private String weatherForecastResp;

    @Nullable
    private String city = "Chelyabinsk";

    @Nullable
    private String lang = "ru";
    @Nullable
    private String cnt = "16"; // TODO: сделать выбор количества периодов
    @NotNull
    private StringBuilder requestWeatherCurrent = new StringBuilder();
    @NotNull
    private StringBuilder requestWeatherForecast = new StringBuilder();

    private DataStorage() {
        buildRequestWeatherCurrent();
        buildRequestWeatherForecast();
    }

    public static DataStorage instance() {
        DataStorage localInstance = instance;
        if (localInstance == null) {
            synchronized (DataStorage.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new DataStorage();
            }
        }
        return localInstance;
    }

    public void storeWeatherCurrentO(@NotNull final WeatherCurrent weather) {
        instance().weatherCurrent = weather;
    }

    private static void storeWeatherCurrent(@NotNull final String weather)
            throws IOException {
        instance().weatherCurrentResp = weather;
        final ObjectMapper mapper = new ObjectMapper();
        instance().weatherCurrent = mapper.readValue(weather, WeatherCurrent.class);
    }

    private static void storeWeatherForecast(@NotNull final String weather) throws IOException {
        instance().weatherForecastResp = weather;
        final ObjectMapper mapper = new ObjectMapper();
        instance().weatherForecast = mapper.readValue(weather, WeatherForecast.class);
    }

    @Nullable
    public String getCity() {
        return city;
    }

    public void setCity(@NotNull final String city) {
        this.city = city;
        buildRequestWeatherCurrent();
        buildRequestWeatherForecast();
    }

    @Nullable
    public String getLang() {
        return lang;
    }

    public void setLang(@NotNull String lang) {
        this.lang = lang;
        buildRequestWeatherCurrent();
        buildRequestWeatherForecast();
    }

    @Nullable
    public WeatherCurrent getWeatherCurrent() {
        return weatherCurrent;
    }

    @Nullable
    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }

    @NotNull
    private String getReqWeatherCurrent() {
        return requestWeatherCurrent.toString();
    }

    @NotNull
    private String getReqWeatherForecast() {
        return requestWeatherForecast.toString();
    }

    @NotNull
    public String getRequestStringWeather(final TypeQuery typeQuery) {
        switch (typeQuery) {
            case CURRENT:
                return getReqWeatherCurrent();
            case FORECAST:
                return getReqWeatherForecast();
        }
        return "";
    }

    /**
     * Строит запрос текущей погоды
     */
    private void buildRequestWeatherCurrent() {
        requestWeatherCurrent.setLength(0);
        requestWeatherCurrent.append(REQ_WEATHER);
        requestWeatherCurrent.append(REQ_WEATHER_CURRENT);
        requestWeatherCurrent.append("?");
        requestWeatherCurrent.append("lang=");
        requestWeatherCurrent.append(lang);
        requestWeatherCurrent.append("&APPID=");
        requestWeatherCurrent.append(APPID);
        requestWeatherCurrent.append("&q=");
        requestWeatherCurrent.append(city);
    }

    /**
     * Строит запрос на прогноз погоды
     */
    private void buildRequestWeatherForecast() {
        requestWeatherForecast.setLength(0);
        requestWeatherForecast.append(REQ_WEATHER);
        requestWeatherForecast.append(REQ_WEATHER_FORECAST);
        requestWeatherForecast.append("?");
        requestWeatherForecast.append("lang=");
        requestWeatherForecast.append(lang);
        requestWeatherForecast.append("&APPID=");
        requestWeatherForecast.append(APPID);
        requestWeatherForecast.append("&q=");
        requestWeatherForecast.append(city);
        requestWeatherForecast.append("&cnt=");
        requestWeatherForecast.append(cnt);
    }

    @NotNull
    public String getUrlImage(@NotNull final TypeQuery typeQuery) {
        switch (typeQuery) {
            case CURRENT:
                if (weatherCurrent == null) return "";
                if (weatherCurrent.weather.get(0) == null) return "";
                return REQ_WEATHER_IMAGE + weatherCurrent.weather.get(0).icon + ".png";
            case FORECAST:
                return "";
        }
        return "";
    }

    public void storeWeather(@NotNull final String weather, @NotNull final TypeQuery typeQuery)
            throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        int cod = mapper.readValue(weather, OpenWeatherMap.class).getCod();
        if (cod != 200) return; // TODO: нужно как-то информировать, что что-то пошло не так
        switch (typeQuery) {
            case CURRENT:
                storeWeatherCurrent(weather);
                break;
            case FORECAST:
                storeWeatherForecast(weather);
                break;
        }
    }

    public enum TypeQuery {CURRENT, FORECAST}

}
