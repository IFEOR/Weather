package com.ifeor.weather.domain

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ifeor.weather.R
import com.ifeor.weather.utils.API_KEY
import org.json.JSONObject
import java.net.URL

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    // Example liveData
    val liveData = MutableLiveData<String>()

    // LiveData
    val tempData = MutableLiveData<String>()
    val descData = MutableLiveData<String>()

    fun showWeather(typedCity: String?) {
        if (typedCity?.trim()?.equals("")!!) {
            Toast.makeText(getApplication(), R.string.err_city_not_chosen, Toast.LENGTH_LONG).show()
        } else {
            Thread {
                val key = API_KEY
                val url =
                    "https://api.openweathermap.org/data/2.5/weather?q=$typedCity&appid=${key}&units=metric&lang=ru"

                val apiResponse = URL(url).readText()
                Log.d("INFO", apiResponse)

                val desc = JSONObject(apiResponse)
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description")
                val temp = JSONObject(apiResponse)
                    .getJSONObject("main")
                    .getString("temp")

                tempData.postValue(temp)
                descData.postValue(desc)

            }.start()
        }
    }
}
