package ru.vlabum.android.apps.nweather

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.StrictMode
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import ru.vlabum.android.apps.nweather.MediaPlayer.AudioPlayerFragment
import ru.vlabum.android.apps.nweather.data.CityContent

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    CityListFragment.OnListFragmentInteractionListener,
    SensorEventListener {

    val LOG_CLASS_NAME = this::class.java.name

    private lateinit var sensorManager: SensorManager
    private var sensor_temp: Sensor? = null
    private var sensor_humidity: Sensor? = null
    private var prefereces: SharedPreferences? = null

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val sensor = event?.sensor ?: return
        when (sensor.type) {
            Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                Log.v(
                    LOG_CLASS_NAME,
                    String.format("onSensorChanged TYPE_AMBIENT_TEMPERATURE value = %f", event.values[0])
                )
                nav_header_main_current_temp?.text =
                        String.format("%s%s", event.values[0].toInt().toString(), getString(R.string.celsium))
                return
            }
            Sensor.TYPE_RELATIVE_HUMIDITY -> {
                Log.v(
                    LOG_CLASS_NAME,
                    String.format("onSensorChanged TYPE_RELATIVE_HUMIDITY value = %f", event.values[0])
                )
                nav_header_main_current_humidity?.text =
                        String.format("%s%s", event.values[0].toInt().toString(), getString(R.string.perc))
                return
            }
        }
    }


    override fun onListFragmentInteraction(item: CityContent.CityItem?) {
        Toast.makeText(
            App.getInstance(),
            String.format("onListFragmentInteraction %s", item?.content),
            Toast.LENGTH_LONG
        ).show()
        val bundle = Bundle()
        bundle.putString("cityName", item?.content)
        val dataWeatherFragment = DataWeatherFragment()
        dataWeatherFragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, dataWeatherFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        prefereces = PreferenceManager.getDefaultSharedPreferences(this)
        restoreConfigData()

        supportFragmentManager.beginTransaction().replace(R.id.container, CityListFragment()).commit()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor_temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        sensor_humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)

        val sensorsList = sensorManager.getSensorList(Sensor.TYPE_ALL)
        sensorsList.forEach {
            Log.v(LOG_CLASS_NAME, String.format("sensor_name = %s", it.name))
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            Log.v(LOG_CLASS_NAME, String.format("TYPE_AMBIENT_TEMPERATURE %s ", "имеется"))
        } else {
            Log.v(LOG_CLASS_NAME, String.format("TYPE_AMBIENT_TEMPERATURE %s ", "отсутствует"))
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
            Log.v(LOG_CLASS_NAME, String.format("TYPE_RELATIVE_HUMIDITY %s ", "имеется"))
        } else {
            Log.v(LOG_CLASS_NAME, String.format("TYPE_RELATIVE_HUMIDITY %s ", "отсутствует"))
        }
    }

    fun saveConfigData() {
        prefereces?.edit()?.putBoolean("isLoadIcon", App.getInstance().repository.isLoadIcon)?.apply()
        prefereces?.edit()?.putBoolean("isForecast5", App.getInstance().repository.isForecast5)?.apply()
    }

    fun restoreConfigData() {
        App.getInstance().repository.isLoadIcon = prefereces?.getBoolean("isLoadIcon", true)
        App.getInstance().repository.isForecast5 = prefereces?.getBoolean("isForecast5", false)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.action_menu_load_icons).isChecked = App.getInstance().repository.isLoadIcon
        menu.findItem(R.id.action_menu_load_forecast).isChecked = App.getInstance().repository.isForecast5
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_menu_load_icons -> {
                App.getInstance().repository.isLoadIcon = !App.getInstance().repository.isLoadIcon
                item.isChecked = App.getInstance().repository.isLoadIcon
                return true
            }
            R.id.action_menu_load_forecast -> {
                App.getInstance().repository.isForecast5 = !App.getInstance().repository.isForecast5
                item.isChecked = App.getInstance().repository.isForecast5
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_weather -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, CityListFragment()).commit()
            }
            R.id.nav_manage_list_cities -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, ManageCityFragment()).commit()
            }
            R.id.nav_about -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, AboutFragment()).commit()
            }
            R.id.nav_player -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, AudioPlayerFragment()).commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()
        sensor_temp?.also { v -> sensorManager.registerListener(this, v, SensorManager.SENSOR_DELAY_NORMAL) }
        sensor_humidity?.also { v -> sensorManager.registerListener(this, v, SensorManager.SENSOR_DELAY_UI) }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        saveConfigData()
    }
}
