package com.ifeor.weather.ui.cities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ifeor.weather.data.model.CityItemList
import com.ifeor.weather.ui.common.OnItemClickListener
import com.ifeor.weather.R

class CitiesAdapter(cityList: ArrayList<CityItemList>, private val onClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CitiesAdapter.CityViewHolder>() {

    var cities = cityList


    class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvCity = view.findViewById<TextView>(R.id.tv_city)

        fun bind(cityItem: CityItemList) {
            tvCity.text = cityItem.cityName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder =
        CityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        )

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val cityItem = cities[position]
        holder.bind(cityItem)
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(cityItem.cityName)
        }
    }

    override fun getItemCount(): Int = cities.size

}
