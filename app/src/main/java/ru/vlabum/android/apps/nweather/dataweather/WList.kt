package ru.vlabum.android.apps.nweather.dataweather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class WList {

    var dt: Int = 0
    var main: Main? = null
    var weather: List<Weather>? = null
    var clouds: Clouds? = null
    var wind: Wind? = null
    var rain: Rain? = null
    var snow: Snow? = null
    var dt_txt: String? = null

}