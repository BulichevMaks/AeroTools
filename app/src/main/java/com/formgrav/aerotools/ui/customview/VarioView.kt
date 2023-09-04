package com.formgrav.aerotools.ui.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlin.math.abs


class VarioView : View {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var value: Int = 40
        set(newValue) {
            field = newValue
            invalidate() // Перерисовываем View при изменении значения
        }

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
    private fun init() {

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas?.save()

        canvas?.scale(.5f * width, -1f * height)
        canvas?.translate(1f, -1f)
        paint.style = Paint.Style.FILL

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.01f // толщина дуги
        paint.color = Color.GRAY // цвет начала и конца дуги
        var oval = RectF(-0.994f, -0.994f, 0.994f, 0.994f)
        var startAngle = 90f // начальный угол дуги
        var sweepAngle = 90f  // угол дуги
        canvas?.drawArc(oval, startAngle, sweepAngle, false, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.01f // толщина дуги
        paint.color = Color.GREEN // цвет начала и конца дуги
        oval = RectF(-0.994f, -0.994f, 0.994f, 0.994f)
        startAngle = 90f // начальный угол дуги
        sweepAngle = -90f  // угол дуги
        canvas?.drawArc(oval, startAngle, sweepAngle, false, paint)

        val max = 80
        val min = 0

        val scale = 0.92f
        val step = Math.PI / max
        for (i in min..max) {
            val x1 = Math.cos(Math.PI - step * i)
            val y1 = Math.sin(Math.PI - step * i)
            var x2 = 0.0
            var y2 = 0.0
            if (i % 10 == 0) {
                x2 = x1 * scale * 0.89f
                y2 = y1 * scale * 0.89f
            } else if (i % 5 == 0) {
                x2 = x1 * scale * 0.95f
                y2 = y1 * scale * 0.95f
            } else {
                x2 = x1 * scale * 1.04f
                y2 = y1 * scale * 1.04f
            }
            paint.color = Color.GREEN
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 0.01f
            canvas?.drawLine(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), paint)
            if(i in 0..40) {
                paint.color = Color.GRAY
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 0.01f
                canvas?.drawLine(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), paint)
            }
        }
        canvas?.save()
        canvas?.rotate(90 - 180f * (value / max.toFloat()))

        paint.color = Color.YELLOW
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.01f
        canvas?.drawLine(-0.05f, 0f, 0F,1f, paint)
        paint.strokeWidth = 0.01f
        canvas?.drawLine(0.05f, 0f, 0F,1f, paint)
        paint.strokeWidth = 0.04f
        canvas?.drawLine(0f, 0f, 0F,0.74f, paint)
        paint.strokeWidth = 0.07f
        canvas?.drawLine(0f, 0f, 0F,0.44f, paint)
        paint.strokeWidth = 0.08f
        canvas?.drawLine(0f, 0f, 0F,0.3f, paint)
        paint.strokeWidth = 0.02f
        canvas?.drawLine(0f, 0f, 0F,1f, paint)

        paint.color = Color.GREEN
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(0F, 0F, .08F, paint)
        canvas?.restore()

        canvas?.restore()
    }


    // Метод для установки угла поворота стрелки
    fun setArrowRotationAnimated(speed: Int) {
        ValueAnimator.ofInt(value, speed).apply {
            duration = (100 + abs(value - speed) * 5).toLong()
            interpolator = DecelerateInterpolator()
            addUpdateListener { animation ->
                value = animation.animatedValue as Int
            }
            start()
        }
    }
}