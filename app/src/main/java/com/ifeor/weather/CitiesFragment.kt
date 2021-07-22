package com.ifeor.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CitiesFragment : Fragment() {

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

        // Views initialization
        etCity = view.findViewById(R.id.et_city)
        btnConfirmCity = view.findViewById(R.id.btn_confirm_city)

        // Listeners
        btnConfirmCity.setOnClickListener { confirmCity() }

        // Cities mock
        val cities = ArrayList<CityItemList>()
        cities.add(CityItemList("Анапа"))
        cities.add(CityItemList("Москва"))
        cities.add(CityItemList("Омск"))

        // Bind RecyclerView adapter
        val rvCities = view.findViewById<RecyclerView>(R.id.rv_cities)
        rvCities.hasFixedSize()
        rvCities.layoutManager = LinearLayoutManager(requireContext())
        rvCities.adapter = CitiesAdapter(cities, object : OnItemClickListener {
            override fun onItemClick(city: String) {
                etCity.setText(city)
            }
        })
    }

    private fun confirmCity() {
        val typedCity = etCity.text?.toString()
        if (typedCity?.trim()?.equals("")!!)
            Toast.makeText(requireContext(), R.string.hint_city_name, Toast.LENGTH_LONG).show()
        else
            (context as MainActivity).toWeatherFragment(typedCity)
    }
}
