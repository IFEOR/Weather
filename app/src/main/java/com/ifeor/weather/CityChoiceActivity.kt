package com.ifeor.weather

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CityChoiceActivity : AppCompatActivity() {

    private var etCity: EditText? = null
    private var btnConfirmCity: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_choice)

        etCity = findViewById(R.id.et_city)
        btnConfirmCity = findViewById(R.id.btn_confirm_city)

        btnConfirmCity?.setOnClickListener { confirmCity() }

        // Cities mock
        val cities = ArrayList<CityItemList>()
        cities.add(CityItemList("Анапа"))
        cities.add(CityItemList("Москва"))
        cities.add(CityItemList("Омск"))

        // Bind RecyclerView adapter
        val rvCities = findViewById<RecyclerView>(R.id.rv_cities)
        rvCities.hasFixedSize()
        rvCities.layoutManager = LinearLayoutManager(this)
        rvCities.adapter = CityRecyclerAdapter(cities, object : OnItemClickListener{
            override fun onItemClick(city: String) {
                etCity?.setText(city)
            }

        })
    }

    private fun confirmCity() {
        val typedCity = etCity?.text?.toString()
        if (typedCity?.trim()?.equals("")!!)
            Toast.makeText(this, R.string.hint_city_name, Toast.LENGTH_LONG).show()
        else
            launchActivity("typedCity", typedCity)
    }

    // Launch MainActivity with intent data
    @Suppress("SameParameterValue")
    private fun launchActivity(name: String, value: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(name, value)
        }
        startActivity(intent)
    }

    // Hide keyboard after typing
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
