package com.ifeor.weather.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ifeor.weather.R
import com.ifeor.weather.domain.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel

    // Fields
    private lateinit var btnShowWeather: Button
    private lateinit var tvWeather: TextView
    private lateinit var tvDesc: TextView
    private lateinit var tvTypedCity: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Binding Fragment and ViewModel
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example observer
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })

        //Views initialization
        btnShowWeather = view.findViewById(R.id.btn_show_weather)
        tvWeather = view.findViewById(R.id.tv_weather)
        tvDesc = view.findViewById(R.id.tv_desc)
        tvTypedCity = view.findViewById(R.id.tv_typed_city)

        // Получение данных от другого фрагмента
        tvTypedCity.text = arguments?.getString("CityArg")

        // Listeners
        btnShowWeather.setOnClickListener { viewModel.showWeather(tvTypedCity.text?.toString()) }

        // Observers

        with(viewModel) {
            // Weather observer
            tempData.observe(viewLifecycleOwner, {
                CoroutineScope(Dispatchers.IO).launch {
                    tvWeather.text = it
                }
            })

            // Description observer
            descData.observe(viewLifecycleOwner, {
                CoroutineScope(Dispatchers.IO).launch {
                    tvDesc.text = it
                }
            })
        }
    }
}
