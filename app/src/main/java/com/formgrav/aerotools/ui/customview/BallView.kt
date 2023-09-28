package com.formgrav.aerotools.ui.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class BallView : View {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var angleX: Float = 0f
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



        paint.style = Paint.Style.FILL
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.13f // толщина дуги
        paint.color = Color.argb(255, 18, 17, 17)
        var oval = RectF(-0.8f, -0.8f, 0.80f, 0.80f)
        var startAngle = -60f // начальный угол дуги
        var sweepAngle = -60f  // угол дуги
        canvas?.drawArc(oval, startAngle, sweepAngle, false, paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.argb(255, 18, 17, 17)
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(-0.395f, -0.695f, 0.065F, paint)
        paint.style = Paint.Style.FILL
        paint.color = Color.argb(255, 18, 17, 17)
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(0.395f, -0.695f, 0.065F, paint)
//------------------------------------------
        // штрихи
        val scalePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        val centerX = 0F // X-координата центра
        val centerY = 0F // Y-координата центра
        val radius = 0.77F // Радиус круга
        // Радиус для расположения шкал (немного сдвинутый от круга)
        val scaleRadius = radius + 0.1F
        // Угол между шкалами (в радианах)
        val angleBetweenScales = Math.toRadians(12.0)
        // Количество шкал
        val numberOfScales = 9
        for (i in 7 until numberOfScales) {
            val x1 = centerX + (scaleRadius - 0.05F) * cos(i * angleBetweenScales).toFloat()
            val y1 = centerY - (scaleRadius - 0.14F) * sin(i * angleBetweenScales).toFloat()
            val x2 = centerX + scaleRadius * cos(i * angleBetweenScales).toFloat()
            val y2 = centerY - scaleRadius * sin(i * angleBetweenScales).toFloat()
            if (i == 7 || i == 8) {
                scalePaint.color = Color.WHITE
                scalePaint.strokeWidth = 0.012f
            }
            canvas.drawLine(x1, y1, x2, y2, scalePaint)
        }
//------------------------------------------
//шарик
        canvas?.rotate(angleX)
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(-0f, -0.8f, 0.054F, paint)




        canvas?.restore()

    }


    fun roll(value: Float) {
        if (value >= 30) {
            angleX = 30f
        } else if (value <= -30) {
            angleX = -30f
        } else {
            val animator = ValueAnimator.ofFloat(angleX, value)
            animator.duration = (100 + abs(angleX - value) * 5).toLong()
            animator.interpolator = DecelerateInterpolator()
            animator.addUpdateListener { animation ->
                angleX = animation.animatedValue as Float
                invalidate()
            }
            animator.start()
        }

    }

}
