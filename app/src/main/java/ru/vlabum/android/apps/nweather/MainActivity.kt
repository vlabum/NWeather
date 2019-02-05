package ru.vlabum.android.apps.nweather

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import ru.vlabum.android.apps.nweather.dummy.CityContent

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    CityListFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: CityContent.CityItem?) {
        Toast.makeText(App.getInstance(), "onListFragmentInteraction", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

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

        supportFragmentManager.beginTransaction().replace(R.id.container, CityListFragment()).commit()
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
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
