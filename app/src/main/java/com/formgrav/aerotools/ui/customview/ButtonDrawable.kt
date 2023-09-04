package com.formgrav.aerotools.ui.customview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable

class ButtonDrawable: Drawable() {
    private val outerCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val innerCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var isPressed = false
    var text = "ZERO"
    var outerColor = Color.GRAY

    init {
       // outerCirclePaint.color = Color.GRAY
        innerCirclePaint.color = Color.DKGRAY
        textPaint.color = Color.WHITE
        textPaint.textSize = 40f
        textPaint.textAlign = Paint.Align.CENTER
    }

    override fun draw(canvas: Canvas) {
        val width = bounds.width().toFloat()
        val height = bounds.height().toFloat()

        val centerX = width / 2
        val centerY = height / 2
        val radius = Math.min(centerX, centerY)

        // Отрисовка внешнего круга
        // Отрисовка внешнего круга при нажатии
        if(isPressed) {
            outerCirclePaint.color = Color.DKGRAY
            canvas.drawCircle(centerX, centerY, radius, outerCirclePaint)
        } else {
            outerCirclePaint.color = outerColor
            canvas.drawCircle(centerX, centerY, radius, outerCirclePaint)
        }

        // Отрисовка внутреннего круга
        val innerRadius = radius * 0.8f
        canvas.drawCircle(centerX, centerY, innerRadius, innerCirclePaint)

        // Определение размеров текста
        val textBounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, textBounds)

        // Отрисовка текста в середине
        val textX = centerX
        val textY = centerY + textBounds.height() / 2
        canvas.drawText(text, textX, textY, textPaint)

        // Отрисовка внутренней дуги
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.5f // толщина дуги
        paint.color = Color.GREEN // цвет начала и конца дуги
        val innerArcRadius = innerRadius - 3.01f
        val innerArcRect = RectF(centerX - innerArcRadius, centerY - innerArcRadius, centerX + innerArcRadius, centerY + innerArcRadius)
        canvas.drawArc(innerArcRect, 0f, 360f, false, paint)


    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    fun setPressed(pressed: Boolean) {
        isPressed = pressed
        invalidateSelf()
    }
}
//        // Отрисовка внешнего круга при нажатии
//        if(isPressed) {
//            outerCirclePaint.color = Color.TRANSPARENT
//            canvas.drawCircle(centerX, centerY, radius, outerCirclePaint)
//        } else {
//            outerCirclePaint.color = Color.GRAY
//            canvas.drawCircle(centerX, centerY, radius, outerCirclePaint)
//        }