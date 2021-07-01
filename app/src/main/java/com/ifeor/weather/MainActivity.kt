package com.ifeor.weather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.ifeor.weather.utils.API_KEY
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var etCity: EditText? = null
    private var btnShowWeather: Button? = null
    private var tvWeather: TextView? = null
    private var tvDesc: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCity = findViewById(R.id.et_city)
        btnShowWeather = findViewById(R.id.btn_show_weather)
        tvWeather = findViewById(R.id.tv_weather)
        tvDesc = findViewById(R.id.tv_desc)

        btnShowWeather?.setOnClickListener { showWeather(etCity?.text?.toString(), this) }
    }

    private fun showWeather(typedCity: String?, context: Context) {
        if (typedCity?.trim()?.equals("")!!)
            Toast.makeText(context, R.string.hint_city_name, Toast.LENGTH_LONG).show()
        else {
            val city: String = typedCity
            val key: String = API_KEY
            val url: String = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"

            doAsync {
                val apiResponse = URL(url).readText()
                Log.d("INFO", apiResponse)

                val desc = JSONObject(apiResponse)
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description")
                val temp = JSONObject(apiResponse)
                    .getJSONObject("main")
                    .getString("temp")

                tvWeather?.text = temp
                tvDesc?.text = desc
            }
        }
    }
}
