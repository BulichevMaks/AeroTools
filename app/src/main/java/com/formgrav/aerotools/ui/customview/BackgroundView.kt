package com.formgrav.aerotools.ui.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

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

        var trianglePaint = Paint(Paint.CURSOR_AFTER)
        trianglePaint.style = Paint.Style.FILL
        trianglePaint.color = Color.WHITE
        // треугольник
        val path = Path()
        path.moveTo(0F, 0.61F) // Вершина треугольника (верхняя точка)
        path.lineTo(-0.04F, 0.52F) // Левый нижний угол треугольника
        path.lineTo(0.04F, 0.52F) // Правый нижний угол треугольника
        path.close() // Замкнуть путь

        canvas?.drawPath(path, trianglePaint)


        canvas?.rotate(angleX)

        paint.style = Paint.Style.FILL
        paint.color = Color.argb(200, 56, 73, 221)
        val topHalfRect = RectF(-1.6f, angleY * -1f, 1.6f, 1.6f)
        canvas?.drawRect(topHalfRect, paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.argb(200, 160, 73, 2)
        val downHalfRect = RectF(-1.6f, angleY * -1f, 1.6f, -1.6f)
        canvas?.drawRect(downHalfRect, paint)

        //линии
        var strokeHight = 0.0025f
//        val lineCount = 28 // Количество линий (включая вертикальную)
//        val verticalOffset = 0.1f // Вертикальное смещение
//
//        for (i in 0 until lineCount) {
//            val yOffset = (i - lineCount / 2) * verticalOffset // Рассчитываем смещение по вертикали
//
//            if (abs(yOffset - 0.2f) > 0.01f) { // Исключаем вертикальные смещения 0.1, 0.3, 0.5, 0.7 и так далее
//                paint.color = Color.WHITE
//                paint.style = Paint.Style.STROKE
//                paint.strokeWidth = if (i % 4 == 0) strokeHight + 0.001f else strokeHight
//
//                val x1 = if (i % 4 == 0) -0.4f else -0.25f // Альтернирующее начальное положение X
//                val x2 = if (i % 4 == 0) x1 + 0.8f else x1 + 0.5f // Длина линии
//
//                val y1 = (angleY * -1f) + yOffset
//                val y2 = y1
//
//                canvas?.drawLine(x1, y1, x2, y2, paint)
//            }
//        }

//        strokeHight = 0.0025f
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeHight + 0.001f
        canvas?.drawLine(-0.4f, angleY * -1f, 0.4F, angleY * -1f, paint)

        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) + 0.1f, 0.2F, (angleY * -1f) + 0.1f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.25f, (angleY * -1f) + 0.2f, 0.25F, (angleY * -1f) + 0.2f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) + 0.3f, 0.2F, (angleY * -1f) + 0.3f, paint)
        paint.strokeWidth = strokeHight + 0.001f
        canvas?.drawLine(-0.4f, (angleY * -1f) + 0.4f, 0.4F, (angleY * -1f) + 0.4f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) + 0.5f, 0.2F, (angleY * -1f) + 0.5f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.25f, (angleY * -1f) + 0.6f, 0.25F, (angleY * -1f) + 0.6f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) + 0.7f, 0.2F, (angleY * -1f) + 0.7f, paint)
        paint.strokeWidth = strokeHight + 0.001f
        canvas?.drawLine(-0.4f, (angleY * -1f) + 0.8f, 0.4F, (angleY * -1f) + 0.8f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) + 0.9f, 0.2F, (angleY * -1f) + 0.9f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.25f, (angleY * -1f) + 1f, 0.25F, (angleY * -1f) + 1f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) + 1.1f, 0.2F, (angleY * -1f) + 1.1f, paint)
        paint.strokeWidth = strokeHight + 0.001f
        canvas?.drawLine(-0.4f, (angleY * -1f) + 1.2f, 0.4F, (angleY * -1f) + 1.2f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) + 1.3f, 0.2F, (angleY * -1f) + 1.3f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.25f, (angleY * -1f) + 1.4f, 0.25F, (angleY * -1f) + 1.4f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) + 1.6f, 0.2F, (angleY * -1f) + 1.6f, paint)
        paint.strokeWidth = strokeHight + 0.001f
        canvas?.drawLine(-0.4f, (angleY * -1f) + 1.7f, 0.4F, (angleY * -1f) + 1.7f, paint)


        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) - 0.1f, 0.2F, (angleY * -1f) - 0.1f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.25f, (angleY * -1f) - 0.2f, 0.25F, (angleY * -1f) - 0.2f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) - 0.3f, 0.2F, (angleY * -1f) - 0.3f, paint)
        paint.strokeWidth = strokeHight + 0.001f
        canvas?.drawLine(-0.4f, (angleY * -1f) - 0.4f, 0.4F, (angleY * -1f) - 0.4f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) - 0.5f, 0.2F, (angleY * -1f) - 0.5f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.25f, (angleY * -1f) - 0.6f, 0.25F, (angleY * -1f) - 0.6f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) - 0.7f, 0.2F, (angleY * -1f) - 0.7f, paint)
        paint.strokeWidth = strokeHight + 0.001f
        canvas?.drawLine(-0.4f, (angleY * -1f) - 0.8f, 0.4F, (angleY * -1f) - 0.8f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) - 0.9f, 0.2F, (angleY * -1f) - 0.9f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.25f, (angleY * -1f) - 1f, 0.25F, (angleY * -1f) - 1f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) - 1.1f, 0.2F, (angleY * -1f) - 1.1f, paint)
        paint.strokeWidth = strokeHight + 0.001f
        canvas?.drawLine(-0.4f, (angleY * -1f) - 1.2f, 0.4F, (angleY * -1f) - 1.2f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) - 1.3f, 0.2F, (angleY * -1f) - 1.3f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.25f, (angleY * -1f) - 1.4f, 0.25F, (angleY * -1f) - 1.4f, paint)
        paint.strokeWidth = strokeHight
        canvas?.drawLine(-0.2f, (angleY * -1f) - 1.5f, 0.2F, (angleY * -1f) - 1.5f, paint)
        paint.strokeWidth = strokeHight + 0.001f
        canvas?.drawLine(-0.4f, (angleY * -1f) - 1.6f, 0.4F, (angleY * -1f) - 1.6f, paint)



        canvas.rotate(140f)
        // цифры
        val scalePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        scalePaint.color = Color.WHITE
        scalePaint.strokeWidth = 0.02F
        val centerX = 0F // X-координата центра
        val centerY = 0F // Y-координата центра

        val radius = 0.6F // Радиус круга

        // Радиус для расположения шкал (немного сдвинутый от круга)
        val scaleRadius = radius + 0.1F

        // Угол между шкалами (в радианах)
        val angleBetweenScales = Math.toRadians(10.0)

        // Количество шкал
        val numberOfScales = 11

        for (i in 0 until numberOfScales) {
            val x1 = centerX + (scaleRadius - 0.05F) * cos(i * angleBetweenScales).toFloat()
            val y1 = centerY - (scaleRadius - 0.05F) * sin(i * angleBetweenScales).toFloat()
            val x2 = centerX + scaleRadius * cos(i * angleBetweenScales).toFloat()
            val y2 = centerY - scaleRadius * sin(i * angleBetweenScales).toFloat()
            if (i == 5) {
                scalePaint.color = Color.RED
                scalePaint.strokeWidth = 0.035f
            } else {
                scalePaint.color = Color.WHITE
                scalePaint.strokeWidth = 0.02F
            }
            canvas.drawLine(x1, y1, x2, y2, scalePaint)
        }


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

