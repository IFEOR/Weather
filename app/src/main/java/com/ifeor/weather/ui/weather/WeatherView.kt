package com.ifeor.weather.ui.weather

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface WeatherView : MvpView {

    fun showWeather(temp: String, desc: String)
    fun showNoDataText()
    fun showLoadErrorText()
    fun presentLoading()
    fun displayTypedCity()
}
