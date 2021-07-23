package com.ifeor.weather.data.repositories

import com.ifeor.weather.data.model.CityItemList

class CitiesRepositoryImpl {

    fun fetchCourseAsync(): ArrayList<CityItemList> {

        // Cities mock
        val cities = ArrayList<CityItemList>()
        cities.add(CityItemList("Анапа"))
        cities.add(CityItemList("Москва"))
        cities.add(CityItemList("Омск"))

        return cities
    }
}
