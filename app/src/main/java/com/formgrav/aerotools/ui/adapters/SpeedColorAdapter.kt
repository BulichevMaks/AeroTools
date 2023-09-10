package com.formgrav.aerotools.ui.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.formgrav.aerotools.R

class SpeedColorAdapter(var speeds: ArrayList<String>) : RecyclerView.Adapter<SpeedColorViewHolder>() {

    private var startGrayAngle = 40
    private var sweepGrayAngle = 20
    private var startGreenAngle = 60
    private var sweepGreenAngle = 40
    private var startYellowAngle = 100
    private var sweepYellowAngle = 20
    private var startRedAngle = 120
    private var sweepRedAngle = 40
    private var startRedAngle2 = 0
    private var sweepRedAngle2 = 45

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeedColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.speed_color_level, parent, false)

        val gray = startGrayAngle..startGrayAngle + sweepGrayAngle
        val green = startGreenAngle..startGreenAngle + sweepGreenAngle
        val yellow = startYellowAngle..startYellowAngle + sweepYellowAngle
        val red = startRedAngle..startRedAngle + sweepRedAngle
        val red2 = startRedAngle2..startRedAngle2 + sweepRedAngle2
        return SpeedColorViewHolder(view,gray,green,yellow,red,red2)
    }

    override fun getItemCount(): Int {
        return speeds.size
    }

    override fun onBindViewHolder(holder: SpeedColorViewHolder, position: Int) {
        holder.bind(speeds[position])
    }

    fun setStartGrayAngle(newValue: Int) {
        startGrayAngle = newValue
        notifyDataSetChanged()
    }
    fun setSweepGrayAngle(newValue: Int) {
        sweepGrayAngle = newValue
        notifyDataSetChanged()
    }
    fun setStartGreenAngle(newValue: Int) {
        startGreenAngle = newValue
        notifyDataSetChanged()
    }
    fun setSweepGreenAngle(newValue: Int) {
        sweepGreenAngle = newValue
        notifyDataSetChanged()
    }
    fun setStartYellowAngle(newValue: Int) {
        startYellowAngle = newValue
        notifyDataSetChanged()
    }
    fun setSweepYellowAngle(newValue: Int) {
        sweepYellowAngle = newValue
        notifyDataSetChanged()
    }
    fun setStartRedAngle(newValue: Int) {
        startRedAngle = newValue
        notifyDataSetChanged()
    }
    fun setSweepRedAngle(newValue: Int) {
        sweepRedAngle = newValue
        notifyDataSetChanged()
    }
    fun setStartRedAngle2(newValue: Int) {
        startRedAngle2 = newValue
        notifyDataSetChanged()
    }
    fun setSweepRedAngle2(newValue: Int) {
        sweepRedAngle2 = newValue
        notifyDataSetChanged()
    }
}

class SpeedColorViewHolder(private val item: View, grayRange: IntRange, greenRange: IntRange, yellowRange: IntRange, redRange: IntRange, redRange2: IntRange) : RecyclerView.ViewHolder(item) {
    private var grayRange = grayRange
    private var greenRange = greenRange
    private var yellowRange = yellowRange
    private var redRange = redRange
    private var redRange2 = redRange2


    private val tvColor: View = item.findViewById(R.id.value)
    private val tvSpeed: TextView = item.findViewById(R.id.speed_text)

    fun bind(model: String) {
//        Log.d("grayRange", "$grayRange")
//        Log.d("greenRange", "$greenRange")
//        Log.d("yellowRange", "$yellowRange")
//        Log.d("REDRange2", "$redRange2")
//        Log.d("REDRange", "$redRange")
//        Log.d("MODEL", "$model")
        tvSpeed.text = model
        val color = when {
            grayRange.contains(model.toInt()) -> Color.GRAY
            greenRange.contains(model.toInt()) -> Color.GREEN
            yellowRange.contains(model.toInt()) -> Color.YELLOW
            redRange.contains(model.toInt()) -> Color.RED
            redRange2.contains(model.toInt()) -> Color.argb(205, 201, 19, 20)
            else -> Color.TRANSPARENT
        }
        tvColor.setBackgroundColor(color)
    }
}