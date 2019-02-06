package ru.vlabum.android.apps.nweather.dataweather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Snow {

    var _1h: Int = 0
        @JsonProperty("1h") set(value) {
            field = value
        }

    var _3h: Int = 0
        @JsonProperty("3h") set(value) {
            field = value
        }

}
