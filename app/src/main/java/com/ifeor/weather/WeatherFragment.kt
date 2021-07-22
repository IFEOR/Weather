package com.ifeor.weather

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.ifeor.weather.utils.API_KEY
import org.json.JSONObject
import java.net.URL

class WeatherFragment : Fragment() {

    // Fields
    lateinit var btnShowWeather: Button
    lateinit var tvWeather: TextView
    lateinit var tvDesc: TextView
    lateinit var tvTypedCity: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Views initialization
        btnShowWeather = view.findViewById(R.id.btn_show_weather)
        tvWeather = view.findViewById(R.id.tv_weather)
        tvDesc = view.findViewById(R.id.tv_desc)
        tvTypedCity = view.findViewById(R.id.tv_typed_city)

        // Получение данных от другого фрагмента
        tvTypedCity.text = arguments?.getString("CityArg")

        // Listeners
        btnShowWeather.setOnClickListener { showWeather(tvTypedCity.text?.toString()) }
    }

    private fun showWeather(typedCity: String?) {
        if (typedCity?.trim()?.equals("")!!) {
            Toast.makeText(requireContext(), R.string.err_city_not_chosen, Toast.LENGTH_LONG).show()
        } else {
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

                (context as Activity).runOnUiThread {
                    tvWeather.text = temp
                    tvDesc.text = desc
                }
            }.start()
        }
    }
}
