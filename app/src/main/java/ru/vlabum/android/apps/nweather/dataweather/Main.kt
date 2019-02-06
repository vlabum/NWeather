package ru.vlabum.android.apps.nweather.dataweather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Main {

    enum class TypeTemp { CEL, FAR, K }

    enum class TypePressure { PA, HG }

    var temp: Float = 0f
    var pressure: Float = 0f
    var humidity: Float = 0f
    var temp_min: Float = 0f
    var temp_max: Float = 0f
    var sea_level: Float = 0f
    var grnd_level: Float = 0f
    var temp_kf: Float = 0f

    fun getTemp(typeTemp: TypeTemp): Int {
        if (typeTemp == TypeTemp.CEL) return Math.round(temp - 273.15f)
        if (typeTemp == TypeTemp.FAR) return Math.round(((temp - 273.15f) * 9f / 5f) + 32f)
        return Math.round(temp)
    }

    fun getHumidityStr(): String {
        return (humidity.toInt()).toString()
    }

    fun getPressureStr(typePressure: TypePressure): String {
        if (typePressure == TypePressure.PA)
            return (pressure.toInt()).toString()
        if (typePressure == TypePressure.HG)
            return (pressure / 1.333f).toInt().toString()
        return ""
    }

}