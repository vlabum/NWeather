package ru.vlabum.android.apps.nweather

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.location.Criteria
import android.location.Criteria.ACCURACY_LOW
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.StrictMode
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.google.common.collect.ImmutableList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import ru.vlabum.android.apps.nweather.MediaPlayer.AudioPlayerFragment
import ru.vlabum.android.apps.nweather.data.CityItem
import ru.vlabum.android.apps.nweather.data.DatabaseHelper
import ru.vlabum.android.apps.nweather.util.PermissionsActivity

class MainActivity : PermissionsActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    CityListFragment.OnListFragmentInteractionListener,
    ActivityCompat.OnRequestPermissionsResultCallback {

    val LOG_CLASS_NAME = this::class.java.name

    private val PERMISSION_REQUEST_CODE = 60061
    private var prefereces: SharedPreferences? = null
    private var databaseHelper: DatabaseHelper? = null
    private var database: SQLiteDatabase? = null
    private var locationManager: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        askPermissions(ImmutableList.of(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        setDrawer()

        prefereces = PreferenceManager.getDefaultSharedPreferences(this)
        restoreConfigData()
        supportFragmentManager.beginTransaction().replace(R.id.container, CityListFragment()).commit()
    }

    private fun setDrawer() {
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
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

    @SuppressLint("MissingPermission")
    override fun onAllow() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        criteria.accuracy = ACCURACY_LOW
        val provider = locationManager?.getBestProvider(criteria, true)

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                if (location == null)
                    return
                DataStorage.instance().latitude = location.latitude
                DataStorage.instance().longitude = location.longitude
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

            override fun onProviderEnabled(provider: String?) {}

            override fun onProviderDisabled(provider: String?) {}
        }
        if (provider != null) {
            locationManager?.requestLocationUpdates(provider, 10000, 10f, locationListener)
        }
    }

    override fun onRefuse() {
        super.onRefuse()
    }

    override fun onListFragmentInteraction(item: CityItem?) {
        val bundle = Bundle()
        bundle.putString("cityName", item?.name)
        val dataWeatherFragment = DataWeatherFragment()
        dataWeatherFragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, dataWeatherFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.action_menu_load_icons).isChecked = App.getInstance().repository.isLoadIcon
        menu.findItem(R.id.action_menu_load_forecast).isChecked = App.getInstance().repository.isForecast5
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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
    }

    override fun onPause() {
        super.onPause()
        saveConfigData()
    }

    override fun onDestroy() {
        super.onDestroy()
        databaseHelper?.close()
        database?.close()
    }

}
