package com.formgrav.aerotools.ui.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlin.math.abs
import kotlin.math.min

class VarioViewDown : View {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var value: Float = 0f
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

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas?.save()


        canvas?.scale(1f * width, 1f * height)
        canvas?.translate(1f, 0f)
        canvas.rotate(180f)
        paint.color = Color.GRAY
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.8f
        canvas?.drawLine(0f, 0f, 0F,value * 0.2f, paint)

        canvas?.save()

        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE

        paint.strokeWidth = 0.01f
        canvas?.drawLine(0f, 0f, 0.5F,0f, paint)
        paint.strokeWidth = 0.01f
        canvas?.drawLine(0f, -0.2f, 0.5F,-0.2f, paint)
        paint.strokeWidth = 0.01f
        canvas?.drawLine(0f, -0.4f, 0.5F,-0.4f, paint)
        paint.strokeWidth = 0.01f
        canvas?.drawLine(0f, -0.6f, 0.5F,-0.6f, paint)
        paint.strokeWidth = 0.01f
        canvas?.drawLine(0f, -0.8f, 0.5F,-0.8f, paint)
        paint.strokeWidth = 0.01f
        canvas?.drawLine(0f, -1f, 0.5F,-1f, paint)



        canvas?.restore()

        canvas?.restore()
    }

    fun setDownAnimated(boost: Float) {
        ValueAnimator.ofFloat(value, boost).apply {
            duration = (100 + abs(value - boost) * 5).toLong()
            interpolator = DecelerateInterpolator()
            addUpdateListener { animation ->
                value = animation.animatedValue as Float
            }
            start()
        }
    }
}