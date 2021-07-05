package com.ifeor.weather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.ifeor.weather.utils.API_KEY
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun showWeather(typedCity: String?, context: Context) {
        if (typedCity?.trim()?.equals("")!!)
            Toast.makeText(context, R.string.hint_city_name, Toast.LENGTH_LONG).show()
        else {
            val city: String = typedCity
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
