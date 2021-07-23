package com.ifeor.weather.ui.cities

import com.ifeor.weather.data.model.CityItemList
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface CitiesView : MvpView {

    fun showNoDataText()
    fun showLoadErrorText()
    fun presentLoading()
    fun presentCities(data: ArrayList<CityItemList>)
    fun showWeather(data: String)
}
