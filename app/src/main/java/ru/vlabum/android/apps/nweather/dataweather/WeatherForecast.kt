package ru.vlabum.android.apps.nweather.dataweather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class WeatherForecast : OpenWeatherMap() {

    var message: String? = null
    var city: City? = null
    var cnt: Int = 0
    var list: List<WList>? = null

}