package com.ifeor.weather.ui.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ifeor.weather.data.model.CityItemList
import com.ifeor.weather.ui.common.OnItemClickListener
import com.ifeor.weather.R
import com.ifeor.weather.data.repositories.CitiesRepositoryImpl
import com.ifeor.weather.ui.common.MainActivity
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class CitiesFragment : MvpAppCompatFragment(), CitiesView {

    @InjectPresenter
    lateinit var citiesPresenter: CitiesPresenter
    private val citiesAdapter = CitiesAdapter(CitiesRepositoryImpl().fetchCourseAsync(), object : OnItemClickListener {
        override fun onItemClick(text: String) {
            etCity.setText(text)
        }
    })

    // Fields
    private lateinit var etCity: EditText
    private lateinit var btnConfirmCity: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        citiesPresenter.fetchCities()
        // Views initialization
        etCity = view.findViewById(R.id.et_city)
        btnConfirmCity = view.findViewById(R.id.btn_confirm_city)
        // Listeners
        btnConfirmCity.setOnClickListener { citiesPresenter.confirmCity(etCity.text?.toString()) }
    }

    // Bind RecyclerView adapter
    private fun setupAdapter() {
        val rvCities = view?.findViewById<RecyclerView>(R.id.rv_cities)
        rvCities?.hasFixedSize()
        rvCities?.layoutManager = LinearLayoutManager(requireContext())
        rvCities?.adapter = citiesAdapter
    }

    // User didn't type the city name
    override fun showNoDataText() {
        Toast.makeText(requireContext(), R.string.hint_city_name, Toast.LENGTH_LONG).show()
    }

    // Not received cities list
    override fun showLoadErrorText() {
        Toast.makeText(requireContext(), R.string.err_city_loading, Toast.LENGTH_LONG).show()
    }

    override fun presentLoading() {
        TODO()
    }

    // Load (update) RecyclerView with cities
    override fun presentCities(data: ArrayList<CityItemList>) {
        citiesAdapter.updateCities(data)
    }

    // To WeatherFragment with typed city
    override fun showWeather(data: String) {
        (context as MainActivity).toWeatherFragment(data)
    }
}
