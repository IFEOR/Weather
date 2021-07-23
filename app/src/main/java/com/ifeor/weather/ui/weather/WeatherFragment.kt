package com.ifeor.weather.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.ifeor.weather.R
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class WeatherFragment : MvpAppCompatFragment(), WeatherView {

    @InjectPresenter
    lateinit var weatherPresenter: WeatherPresenter
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
        weatherPresenter.loadView()
        // Listeners
        btnShowWeather.setOnClickListener { weatherPresenter.fetchWeather(tvTypedCity.text?.toString()) }
    }

    // Displays the received weather
    override fun showWeather(temp: String, desc: String) {
        tvWeather.text = temp
        tvDesc.text = desc
    }

    // User didn't type the city name
    override fun showNoDataText() {
        Toast.makeText(requireContext(), R.string.err_city_not_chosen, Toast.LENGTH_LONG).show()
    }

    // Not received current weather
    override fun showLoadErrorText() {
        Toast.makeText(requireContext(), R.string.err_weather_loading, Toast.LENGTH_LONG).show()
    }

    override fun presentLoading() {
        TODO()
    }

    // Получение данных от другого фрагмента
    override fun displayTypedCity() {
        tvTypedCity.text = arguments?.getString("CityArg")
    }
}
