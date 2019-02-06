package ru.vlabum.android.apps.nweather.dataweather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class WeatherCurrent : OpenWeatherMap() {

    lateinit var coord: Coord
    lateinit var weather: List<Weather>
    lateinit var base: String
    lateinit var main: Main
    var visibility: Float = 0f
    lateinit var wind: Wind
    lateinit var clouds: Clouds
    var rain: Rain? = null
    var snow: Snow? = null
    var dt: Int = 0 //  Time of data calculation, unix, UTC
    lateinit var sys: Sys
    var id: Int = 0
    lateinit var name: String

}