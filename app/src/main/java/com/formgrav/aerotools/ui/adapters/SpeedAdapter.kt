package com.formgrav.aerotools.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.formgrav.aerotools.R

class SpeedAdapter(var speeds: ArrayList<String>) : RecyclerView.Adapter<SpeedViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.speed_level, parent, false)
        return SpeedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return speeds.size
    }

    override fun onBindViewHolder(holder: SpeedViewHolder, position: Int) {
        holder.bind(speeds[position])
    }

}

class SpeedViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {


    private val tvSpeed: TextView = item.findViewById(R.id.speed_value)

    fun bind(model: String) {

        if(model.contains("-")) {
            tvSpeed.text = ""
        } else {
            tvSpeed.text = model
        }
    }
}