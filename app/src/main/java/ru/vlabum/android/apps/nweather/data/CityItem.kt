package ru.vlabum.android.apps.nweather.data

class CityItem(var name: String, var temerature: Int?, var humidity: Int?, var pressure: Int?, var descr: String?) {
    override fun toString(): String = name
}