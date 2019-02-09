package ru.vlabum.android.apps.nweather.dataweather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Sys {

    var type: Int = 0
    var id: Int = 0
    var message: Float = 0f
    lateinit var country: String
    var sunrise: Int = 0
    var sunset: Int = 0

}
