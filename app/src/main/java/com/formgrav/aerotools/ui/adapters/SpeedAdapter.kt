package com.formgrav.aerotools.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.formgrav.aerotools.R

class SpeedAdapter(var speeds: ArrayList<String>) : RecyclerView.Adapter<SpeedViewHolder>() {

    private var value: Int = 0

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

    fun setValue(newValue: Int) {
        value = newValue
        notifyDataSetChanged()
    }
}

class SpeedViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {

    private val tvSpeed: TextView = item.findViewById(R.id.speed_value)
    private val tvBack: View = item.findViewById(R.id.background_speed_up)
    private val tvBack2: View = item.findViewById(R.id.background_speed_down)

    fun bind(model: String) {
        tvSpeed.text = model

        if(tvSpeed.text.startsWith("-")) {
            tvSpeed.visibility = View.GONE
            tvBack.visibility = View.GONE
            tvBack2.visibility = View.GONE
        } else {
            tvSpeed.visibility = View.VISIBLE
            tvBack.visibility = View.VISIBLE
            tvBack2.visibility = View.VISIBLE
        }
        if(tvSpeed.text == "0") {
            tvBack.visibility = View.VISIBLE
            tvBack2.visibility = View.GONE
        }
    }
}