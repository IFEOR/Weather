package com.ifeor.weather.domain

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ifeor.weather.R
import com.ifeor.weather.repository.model.CityItemList

class CitiesViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData
    val typedCityData = MutableLiveData<String>()
    val citiesData = MutableLiveData<ArrayList<CityItemList>>()

    init {
        // Cities mock
        val cities = ArrayList<CityItemList>()
        cities.add(CityItemList("Анапа"))
        cities.add(CityItemList("Москва"))
        cities.add(CityItemList("Омск"))
        citiesData.value = cities
    }

    fun confirmCity(typedCity: String) {
        if (typedCity.trim() == "")
            Toast.makeText(getApplication(), R.string.hint_city_name, Toast.LENGTH_LONG).show()
        else
            typedCityData.value = typedCity
    }
}
