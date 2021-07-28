package com.ifeor.weather.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ifeor.weather.presentation.common.MainActivity
import com.ifeor.weather.presentation.common.OnItemClickListener
import com.ifeor.weather.R
import com.ifeor.weather.domain.CitiesViewModel
import com.ifeor.weather.presentation.adapters.CitiesAdapter

class CitiesFragment : Fragment() {

    private lateinit var viewModel : CitiesViewModel

    // Fields
    private lateinit var etCity: EditText
    private lateinit var btnConfirmCity: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Binding Fragment and ViewModel
        viewModel = ViewModelProvider(this).get(CitiesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Views initialization
        etCity = view.findViewById(R.id.et_city)
        btnConfirmCity = view.findViewById(R.id.btn_confirm_city)

        // Listeners
        btnConfirmCity.setOnClickListener { viewModel.confirmCity(etCity.text.toString()) }

        with(viewModel) {
            // Typed city observer
            typedCityData.observe(viewLifecycleOwner, {
                (context as MainActivity).toWeatherFragment(it)
            })

            // Cities observer
            citiesData.observe(viewLifecycleOwner, {
                // Bind RecyclerView adapter
                val rvCities = view.findViewById<RecyclerView>(R.id.rv_cities)
                rvCities.hasFixedSize()
                rvCities.layoutManager = LinearLayoutManager(requireContext())
                rvCities.adapter = CitiesAdapter(it, object : OnItemClickListener {
                    override fun onItemClick(text: String) {
                        etCity.setText(text)
                    }
                })
            })
        }
    }
}
