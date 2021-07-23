package com.ifeor.weather.ui.weather

import android.util.Log
import com.ifeor.weather.utils.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import org.json.JSONObject
import java.net.URL

@InjectViewState
class WeatherPresenter : MvpPresenter<WeatherView>() {

    fun fetchWeather(typedCity: String?) {
        //viewState.presentLoading()
        if (typedCity?.trim()?.equals("")!!) {
            viewState.showNoDataText()
        } else {
            val city: String = typedCity
            val key: String = API_KEY
            val url =
                "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"

            GlobalScope.launch (Dispatchers.IO) {
                try {
                    val apiResponse = URL(url).readText()
                    Log.d("INFO", apiResponse)

                    val desc = JSONObject(apiResponse)
                        .getJSONArray("weather")
                        .getJSONObject(0)
                        .getString("description")
                    val temp = JSONObject(apiResponse)
                        .getJSONObject("main")
                        .getString("temp")
                    withContext(Dispatchers.Main) {
                        viewState.showWeather(temp, desc)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    viewState.showLoadErrorText()
                }
            }
        }
    }

    fun loadView() {
        viewState.displayTypedCity()
    }
}
