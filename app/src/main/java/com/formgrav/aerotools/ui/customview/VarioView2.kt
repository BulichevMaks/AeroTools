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
import kotlin.math.min

class VarioView2: View {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var value: Int = 0
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
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val desiredHeight = MeasureSpec.getSize(heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        // Рассчитываем размеры View в зависимости от режимов измерения
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = min(desiredWidth, widthSize)
        } else {
            width = desiredWidth
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = min(desiredHeight, heightSize)
        } else {
            height = desiredHeight
        }
        // Устанавливаем рассчитанные размеры
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas?.save()
        var width = width
        var height = height
        val aspect = width / height
        val normalAspect = 2f / 1f
        if (aspect > normalAspect) {
            width = (normalAspect * height.toFloat()).toInt()
        }
        if (aspect < normalAspect) {
            height = (width / normalAspect).toInt()
        }
        canvas?.scale(.5f * width, -1f * height)
        canvas?.translate(1f, -1f)
        paint.style = Paint.Style.FILL
//        paint.color = Color.argb(255, 48, 47, 47)
//        canvas?.drawCircle(0F, 0F, 1F, paint)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.38f // толщина дуги
        paint.color = Color.argb(255, 18, 17, 17)
        var oval = RectF(-0.8f, -0.8f, 0.80f, 0.80f)
        var startAngle = 180f // начальный угол дуги
        var sweepAngle = -140f  // угол дуги
        canvas?.drawArc(oval, startAngle, sweepAngle, false, paint)

        oval = RectF(-0.8f, -0.8f, 0.80f, 0.80f)
        startAngle = 180f // начальный угол дуги
        sweepAngle = 140f  // угол дуги
        canvas?.drawArc(oval, startAngle, sweepAngle, false, paint)

        paint.strokeWidth = 0.01f // толщина дуги
        paint.color = Color.argb(255, 48, 47, 47)
        oval = RectF(-1f, -1f, 1f, 1f)
        startAngle = 180f // начальный угол дуги
        sweepAngle = 140f  // угол дуги
        canvas?.drawArc(oval, startAngle, sweepAngle, false, paint)

         oval = RectF(-1f, -1f, 1f, 1f)
         startAngle = 180f // начальный угол дуги
         sweepAngle = -140f  // угол дуги
        canvas?.drawArc(oval, startAngle, sweepAngle, false, paint)


        val max = 80
        val min = 0

        val scale = 0.92f
        var step = Math.PI / max
        for (i in min..max) {
            val x1 = Math.cos(Math.PI - step * i)
            val y1 = Math.sin(Math.PI - step * i)
            var x2 = 0.0
            var y2 = 0.0
            if (i % 10 == 0) {
                x2 = x1 * scale * 0.91f
                y2 = y1 * scale * 0.91f
            }  else {
                x2 = x1 * scale
                y2 = y1 * scale
            }

            if(i % 10 == 0 && i < 60) {
                paint.color = Color.WHITE
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 0.035f
                canvas?.drawLine(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), paint)
            }
        }

        step = (Math.PI / max) * -1
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
            }

            if(i % 10 == 0 && i < 50) {
                paint.color = Color.WHITE
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 0.035f
                canvas?.drawLine(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), paint)
            }
            if(i == 50) {
                paint.color = Color.WHITE
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 0.035f
                canvas?.drawLine(x1.toFloat() * .98f, y1.toFloat() * .98f, x2.toFloat(), y2.toFloat(), paint)
            }
        }
        canvas?.save()
        canvas?.rotate(90 - 180f * (value / max.toFloat()))

        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.015f
        canvas?.drawLine(0f, 0f, 0F,0.96f, paint)
        paint.strokeWidth = 0.0225f
        canvas?.drawLine(0f, 0f, 0F,0.95f, paint)
        paint.strokeWidth = 0.03f
        canvas?.drawLine(0f, 0f, 0F,0.94f, paint)
        paint.strokeWidth = 0.0375f
        canvas?.drawLine(0f, 0f, 0F,0.93f, paint)
        paint.strokeWidth = 0.045f
        canvas?.drawLine(0f, 0f, 0F,0.92f, paint)
        paint.strokeWidth = 0.0525f
        canvas?.drawLine(0f, 0f, 0F,0.91f, paint)
        paint.strokeWidth = 0.06f
        canvas?.drawLine(0.0f, 0f, 0F,0.90f, paint)


//        paint.color = Color.BLACK
//        paint.style = Paint.Style.FILL
//        canvas?.drawCircle(0F, 0F, .34F, paint)
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