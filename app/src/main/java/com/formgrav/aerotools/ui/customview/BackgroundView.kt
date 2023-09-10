package com.formgrav.aerotools.ui.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlin.math.abs
import kotlin.math.min

class BackgroundView : View {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var angleX: Float = 0f
        // начальный угол дуги
        set(newValue) { // ошибка на этой строке
            field = newValue
            invalidate()
        }
    private var angleY: Float = 0.0f
        // угол дуги
        set(newValue) {
            field = newValue
            invalidate()
        }
    private var angleZ: Float = -45f
        // начальный угол дуги
        set(newValue) {
            field = newValue
            invalidate()
        }


    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
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

        canvas?.rotate(angleX)

        paint.style = Paint.Style.FILL
        paint.color = Color.argb(200,56,73,221)
        val topHalfRect = RectF(-1.3f, angleY * -1f, 1.3f, 1.3f)
        canvas?.drawRect(topHalfRect, paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.argb(200,160,73,2)
        val downHalfRect = RectF(-1.3f, angleY * -1f, 1.3f, -1.3f)
        canvas?.drawRect(downHalfRect, paint)

        //стрелки

        val strokeHight = 0.002f
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.4f, angleY * -1f, 0.4F,angleY * -1f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY  * -1f) + 0.1f, 0.2F,(angleY  * -1f) + 0.1f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.4f, (angleY  * -1f) + 0.2f, 0.4F,(angleY  * -1f) + 0.2f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY  * -1f) + 0.3f, 0.2F,(angleY  * -1f) + 0.3f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.4f, (angleY  * -1f) + 0.4f, 0.4F,(angleY  * -1f) + 0.4f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY  * -1f) + 0.5f, 0.2F,(angleY  * -1f) + 0.5f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.4f, (angleY  * -1f) + 0.6f, 0.4F,(angleY  * -1f) + 0.6f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY  * -1f) + 0.7f, 0.2F,(angleY  * -1f) + 0.7f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.4f, (angleY  * -1f) + 0.8f, 0.4F,(angleY  * -1f) + 0.8f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY  * -1f) + 0.9f, 0.2F,(angleY  * -1f) + 0.9f, paint)


        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY  * -1f) - 0.1f, 0.2F,(angleY  * -1f) - 0.1f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.4f, (angleY  * -1f) - 0.2f, 0.4F,(angleY  * -1f) - 0.2f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY  * -1f) - 0.3f, 0.2F,(angleY  * -1f) - 0.3f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.4f, (angleY  * -1f) - 0.4f, 0.4F,(angleY  * -1f) - 0.4f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY  * -1f) - 0.5f, 0.2F,(angleY  * -1f) - 0.5f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.4f, (angleY  * -1f) - 0.6f, 0.4F,(angleY  * -1f) - 0.6f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY  * -1f) - 0.7f, 0.2F,(angleY  * -1f) - 0.7f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.4f, (angleY  * -1f) - 0.8f, 0.4F,(angleY  * -1f) - 0.8f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY  * -1f) - 0.9f, 0.2F,(angleY  * -1f) - 0.9f, paint)

        var circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        circlePaint.style = Paint.Style.FILL
        circlePaint.color = Color.BLACK// почему этот круг отрисовывается синим цветом?
        circlePaint.style = Paint.Style.FILL
        canvas?.drawCircle(0F, 0F, 0.02F, circlePaint)



        canvas?.restore()

    }


    fun setXX(value: Float) {
        angleX = value

//        ValueAnimator.ofFloat(angleX, value).apply {
//            duration = (100 + abs(angleX - value) * 5).toLong()
//            interpolator = DecelerateInterpolator()
//            addUpdateListener { animation ->
//                angleX = animation.animatedValue as Float
//            }
//            start()
//        }
    }

    fun setYY(value: Float) {
        angleY = value
//
    }

    fun setZZ(value: Float) {
        angleZ = value
    }

}

