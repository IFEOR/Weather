package com.ifeor.weather

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*

class CityChoiceActivity : AppCompatActivity() {

    private var etCity: EditText? = null
    private var btnConfirmCity: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_choice)

        etCity = findViewById(R.id.et_city)
        btnConfirmCity = findViewById(R.id.btn_confirm_city)

        btnConfirmCity?.setOnClickListener { confirmCity() }

        val cityList = ArrayList<String>()
        cityList.add("Москва")
        cityList.add("Омск")
        cityList.add("Анапа")

        val lvCities = findViewById<ListView>(R.id.lv_cities)
        lvCities.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cityList)
        lvCities.setOnItemClickListener { _, _, position, _ ->
            etCity?.setText(cityList[position])
        }
    }

    private fun confirmCity() {
        val typedCity = etCity?.text?.toString()
        if (typedCity?.trim()?.equals("")!!) {
            Toast.makeText(this, R.string.hint_city_name, Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("typedCity", typedCity)
            }
            startActivity(intent)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
