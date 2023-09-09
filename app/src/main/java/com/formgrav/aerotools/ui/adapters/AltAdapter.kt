package com.formgrav.aerotools.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.formgrav.aerotools.R
import com.formgrav.aerotools.domain.model.AttitudeData
import java.text.SimpleDateFormat
import java.util.Locale


class AltAdapter(var tracks: ArrayList<AttitudeData>) : RecyclerView.Adapter<AltViewHolder>() {

    private var value: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AltViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alt_level, parent, false)
        return AltViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: AltViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    fun setValue(newValue: Int) {
        value = newValue
        notifyDataSetChanged()
    }
}

class AltViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {

    private val tvAlt: TextView = item.findViewById(R.id.alt_value)
    private val tvBack: View = item.findViewById(R.id.background)
    private val tvBack2: View = item.findViewById(R.id.background2)

    fun bind(model: AttitudeData) {
        tvAlt.text = model.altitude

        if(tvAlt.text.startsWith("-")) {
            tvBack.visibility = View.VISIBLE
            tvBack2.visibility = View.VISIBLE
        } else {
            tvBack.visibility = View.GONE
            tvBack2.visibility = View.GONE
        }
        if(tvAlt.text == "0") {
            tvBack.visibility = View.VISIBLE
        }
    }
}