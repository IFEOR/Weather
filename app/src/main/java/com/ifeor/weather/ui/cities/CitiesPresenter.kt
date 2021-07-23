package com.ifeor.weather.ui.cities

import com.ifeor.weather.data.repositories.CitiesRepositoryImpl
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class CitiesPresenter : MvpPresenter<CitiesView>() {

    private val citiesRepository = CitiesRepositoryImpl()

    fun fetchCities() {
        viewState.presentLoading()

        val cities = citiesRepository.fetchCourseAsync()
        try {
            viewState.presentCities(data = cities)
        } catch (e: Exception) {
            e.printStackTrace()
            viewState.showLoadErrorText()
        }
    }

    fun confirmCity(typedCity: String?) {
        if (typedCity?.trim()?.equals("")!!)
            viewState.showNoDataText()
        else
            viewState.showWeather(typedCity)
    }
}
