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

class CenterPointView: View {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

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

        canvas?.scale(1f * width, -1f * height)
        canvas?.translate(1f, -1f)
        paint.style = Paint.Style.FILL
        paint.color = Color.YELLOW
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(-0.5f, 0.5f, 0.05F, paint)

        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(-0.5f, 0.5f, 0.045F, paint)
        //__________________________________-
        paint.color = Color.YELLOW
        paint.style = Paint.Style.STROKE

        paint.strokeWidth = 0.034f
        canvas?.drawLine(-0.39f, 0.5f, 0F,0.5f, paint)
        paint.strokeWidth = 0.034f
        canvas?.drawLine(-1f, 0.5f, -0.61F,0.5f, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.025f // толщина дуги
        paint.color = Color.YELLOW// цвет дуги

        val radius1 = 0.125f
        canvas?.drawArc(
            -0.5f - radius1, 0.5f - radius1, -0.5f + radius1, 0.5f + radius1,
            180f, 180f, false, paint
        )
        val radius2 = 0.115f
        canvas?.drawArc(
            -0.5f - radius2, 0.5f - radius2, -0.5f + radius2, 0.5f + radius2,
            180f, 180f, false, paint
        )

        //__________________________________-

        canvas?.save()

        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE

        paint.strokeWidth = 0.025f
        canvas?.drawLine(-0.39f, 0.5f, 0F,0.5f, paint)
        paint.strokeWidth = 0.025f
        canvas?.drawLine(-1f, 0.5f, -0.61F,0.5f, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.025f // толщина дуги
        paint.color = Color.WHITE// цвет дуги



        val centerX = -0.5f
        val centerY = 0.5f
        val radius = 0.12f

        canvas?.drawArc(
            centerX - radius, centerY - radius, centerX + radius, centerY + radius,
            180f, 180f, false, paint
        )



        canvas?.restore()

        canvas?.restore()
    }

}