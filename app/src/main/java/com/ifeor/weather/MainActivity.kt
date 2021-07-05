package com.ifeor.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.ifeor.weather.utils.API_KEY
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var btnChooseCity: Button? = null
    private var btnShowWeather: Button? = null
    private var tvWeather: TextView? = null
    private var tvDesc: TextView? = null
    private var tvCity: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnChooseCity = findViewById(R.id.btn_choose_city)
        btnShowWeather = findViewById(R.id.btn_show_weather)
        tvWeather = findViewById(R.id.tv_weather)
        tvDesc = findViewById(R.id.tv_desc)
        tvCity = findViewById(R.id.tv_city)
        tvCity?.text = intent.getCharSequenceExtra("typedCity")

        btnChooseCity?.setOnClickListener { launchCityChoiceActivity() }
        btnShowWeather?.setOnClickListener { showWeather(tvCity?.text?.toString()) }
    }

    private fun launchCityChoiceActivity() {
        val intent = Intent(this, CityChoiceActivity::class.java)
        startActivity(intent)
    }

    private fun showWeather(typedCity: String?) {
        if(typedCity?.trim()?.equals("")!!) {
            Toast.makeText(this, R.string.err_city_not_chosen, Toast.LENGTH_LONG).show()
        } else {
            val city: String? = typedCity
            val key: String = API_KEY
            val url =
                "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"

            Thread {
                val apiResponse = URL(url).readText()
                Log.d("INFO", apiResponse)

                val desc = JSONObject(apiResponse)
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description")
                val temp = JSONObject(apiResponse)
                    .getJSONObject("main")
                    .getString("temp")

                runOnUiThread {
                    tvWeather?.text = temp
                    tvDesc?.text = desc
                }
            }.start()
        }
    }
}
