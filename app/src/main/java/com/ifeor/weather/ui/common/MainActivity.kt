package com.ifeor.weather.ui.common

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.ifeor.weather.R

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // For data transfer between fragments
    private val bundle = Bundle()
    private val defaultCity = "Омск"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Drawer navigation
        val navView = findViewById<NavigationView>(R.id.nav_view_main)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_weather -> toWeatherFragment()
            R.id.nav_cities -> toCitiesFragment()
            R.id.nav_settings -> toSettingsFragment()
        }
        return true
    }

    fun toWeatherFragment(value: String = defaultCity) {
        bundle.putString("CityArg", value)
        findNavController(R.id.nav_host_fragment).navigate(R.id.nav_weather, bundle)
    }

    fun toCitiesFragment() = findNavController(R.id.nav_host_fragment).navigate(R.id.nav_cities)

    fun toSettingsFragment() = findNavController(R.id.nav_host_fragment).navigate(R.id.nav_settings)

    // Hide keyboard after typing
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
