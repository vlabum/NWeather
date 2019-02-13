package ru.vlabum.android.apps.nweather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import ru.vlabum.android.apps.nweather.dataweather.Main

class DataWeatherFragment : Fragment() {

    val LOG_CLASS_NAME = this::class.java.name

    var weather_temp: TextView? = null
    var weather_city: TextView? = null
    var weather_precipitation_value: TextView? = null
    var weather_wind_value: TextView? = null
    var weather_humidity_value: TextView? = null
    var weather_pressure_value: TextView? = null
    var imageView1: ImageView? = null

    var cityName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        cityName = arguments?.getString("cityName")
        if (cityName == null) return null
        return inflater.inflate(R.layout.fragment_data_weather, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (cityName == null) return
        weather_temp = view?.findViewById(R.id.weather_temp) as TextView?
        weather_city = view?.findViewById(R.id.weather_city) as TextView?
        weather_precipitation_value = view?.findViewById(R.id.weather_precipitation_value) as TextView?
        weather_wind_value = view?.findViewById(R.id.weather_wind_value) as TextView?
        weather_humidity_value = view?.findViewById(R.id.weather_humidity_value) as TextView?
        weather_pressure_value = view?.findViewById(R.id.weather_pressure_value) as TextView?
        imageView1 = view?.findViewById(R.id.imageView1)

        val listener = object : RequesterWeather.OnRequestListener {
            override fun onComlete(exception: Exception?) {
                if (exception != null)
                    Toast.makeText(App.getInstance(), "Запрос погоды не удался", Toast.LENGTH_LONG).show()
                else
                    updateView()
            }
        }

        val requestWeather = RequesterWeather(listener)
        requestWeather.make(cityName!!)
    }

    fun updateView() {
        if (DataStorage.instance().weatherCurrent == null) return
        if (DataStorage.instance().weatherCurrent?.weather == null) return
        weather_temp?.text = DataStorage.instance().weatherCurrent?.main?.getTemp(Main.TypeTemp.CEL).toString()
        weather_city?.text = DataStorage.instance().weatherCurrent?.name
        weather_precipitation_value?.text = DataStorage.instance().weatherCurrent?.weather?.get(0)?.description
        weather_wind_value?.text = DataStorage.instance().weatherCurrent?.wind?.getSpeedStr()
        weather_humidity_value?.text = DataStorage.instance().weatherCurrent?.main?.getHumidityStr()
        weather_pressure_value?.text = DataStorage.instance().weatherCurrent?.main?.getPressureStr(
            if ("ru".equals(
                    DataStorage.instance().lang
                )
            ) Main.TypePressure.HG else Main.TypePressure.PA
        )
        if (App.getInstance()?.repository?.isLoadIcon == true)
            showImage(DataStorage.instance().getUrlImage(DataStorage.TypeQuery.CURRENT))
        else
            imageView1?.setImageResource(android.R.color.transparent)
    }

    private fun showImage(urlS: String) {
        if (imageView1 == null) return
        Picasso.with(App.getInstance())
            .load(urlS)
            .into(imageView1)
    }

}