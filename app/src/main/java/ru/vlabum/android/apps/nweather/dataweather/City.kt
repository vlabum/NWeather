package ru.vlabum.android.apps.nweather.dataweather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class City {

    var id: Int = 0
    var name: String? = null
    var coord: Coord? = null
    var country: String? = null

}