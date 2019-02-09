package ru.vlabum.android.apps.nweather.dataweather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Wind {

    var speed: Float = 0f
    var deg: Float = 0f

    fun getSpeedStr(): String {
        return speed.toString()
    }

}