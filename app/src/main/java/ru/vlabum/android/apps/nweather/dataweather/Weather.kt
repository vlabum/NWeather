package ru.vlabum.android.apps.nweather.dataweather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Weather {

    var id: Int = 0
    lateinit var main: String
    lateinit var description: String
    lateinit var icon: String

}
