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
//-------------------------------------------
        var strokeHight = 0.0025f
        // Цвет, стиль и толщина линии
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeHight + 0.001f

// Начальная позиция Y
        var startY = (angleY * -1f)
        var step1 = 0.2f
// Количество линий
        val numLines = -10
        var currentValue = 1
        var currentValue2 = -1
// Отрисовка линий в цикле
        for (i in -numLines downTo numLines) {

                if (i >= 0) {
                    if(startY < 0.49f) {
                        val step = 0.2f
                        val endY = startY + step

                        if (i % 2 == 0) {
                            paint.strokeWidth = strokeHight + 0.001f
                            canvas?.drawLine(-0.3f, startY, 0.3f, startY, paint)
                            startY = endY

                        } else {
                            paint.strokeWidth = strokeHight
                            paint.strokeWidth = strokeHight
                            canvas?.drawLine(-0.15f, startY, 0.15f, startY, paint)
                            startY = endY
                            if(startY < 0.41f) {
                                // Отрисовка цифр на каждой второй линии
                                val textY =
                                    startY - 0.025f // Позиция Y для отрисовки текста с учетом смещения вверх
                                val textPaint = Paint()
                                textPaint.color = Color.WHITE
                                textPaint.textSize = 0.08f
                                textPaint.textAlign = Paint.Align.CENTER

                                // Переворачиваем Canvas на 180 градусов
                                canvas?.save()
                                canvas?.rotate(180f, 0.0f, textY)
                                canvas?.scale(-1f, 1f, 0.0f, textY) // Отразить по горизонтали
                                val valueString = currentValue.toString()
                                var textX = 0.34f // Центрирование текста относительно X
                                canvas?.drawText(valueString, textX, textY, textPaint)
                                canvas?.drawText("0", textX + 0.05f, textY, textPaint)
                                textX = -0.43f
                                canvas?.drawText(valueString, textX, textY, textPaint)
                                canvas?.drawText("0", textX + 0.05f, textY, textPaint)
                                canvas?.restore()
                                currentValue += 1
                            }
                        }
                    }
                } else {

                    if (i % 2 == 0) {
                        paint.strokeWidth = strokeHight + 0.001f
                        canvas?.drawLine(
                            -0.3f,
                            (angleY * -1f) - step1,
                            0.3f,
                            (angleY * -1f) - step1,
                            paint
                        )
                        step1 += 0.2f

                    } else {
                        paint.strokeWidth = strokeHight
                        paint.strokeWidth = strokeHight
                        canvas?.drawLine(
                            -0.15f,
                            (angleY * -1f) - step1,
                            0.15f,
                            (angleY * -1f) - step1,
                            paint
                        )
                        step1 += 0.2f

                        // Отрисовка цифр на каждой второй линии
                        val textY =
                            (angleY * -1f) - step1 - 0.02f// Позиция Y для отрисовки текста с учетом смещения вверх
                        val textPaint = Paint()
                        textPaint.color = Color.WHITE
                        textPaint.textSize = 0.07f
                        textPaint.textAlign = Paint.Align.CENTER

                        // Переворачиваем Canvas на 180 градусов
                        canvas?.save()
                        canvas?.rotate(180f, 0.0f, textY)
                        canvas?.scale(-1f, 1f, 0.0f, textY) // Отразить по горизонтали
                        if (currentValue2 == 0) {
                            var textX = 0.32f // Центрирование текста относительно X

                            canvas?.drawText("0", textX + 0.04f, textY, textPaint)
                            textX = -0.44f

                            canvas?.drawText("0", textX + 0.04f, textY, textPaint)
                        } else {
                            val valueString2 = abs(currentValue2).toString()
                            var textX = 0.36f // Центрирование текста относительно X
                            canvas?.drawText(valueString2, textX, textY, textPaint)
                            canvas?.drawText("0", textX + 0.04f, textY, textPaint)
                            textX = -0.4f
                            canvas?.drawText(valueString2, textX, textY, textPaint)
                            canvas?.drawText("0", textX + 0.04f, textY, textPaint)
                            textX = -0.465f
                            canvas?.drawText("-", textX + 0.04f, textY, textPaint)
                            textX = 0.28f
                            canvas?.drawText("-", textX + 0.04f, textY, textPaint)
                        }
                        canvas?.restore()
                        currentValue2 -= 1
                    }
                }
        }
//-------------------------------------------
        //-------------------------------------------
//        currentValue = 4
//// Отрисовка линий в цикле
//        for (i in 0 until 11) {
//            val endY = startY - step
//
//            if (i % 2 == 0) {
//                paint.strokeWidth = strokeHight + 0.001f
//                canvas?.drawLine(-0.3f, -startY, 0.3f, -startY, paint)
//                startY = endY
//
//            } else {
//                paint.strokeWidth = strokeHight
//                paint.strokeWidth = strokeHight
//                canvas?.drawLine(-0.15f, -startY, 0.15f, -startY, paint)
//                startY = endY
//
//                // Отрисовка цифр на каждой второй линии
//                val textY = -startY - 0.02f // Позиция Y для отрисовки текста с учетом смещения вверх
//                val textPaint = Paint()
//                textPaint.color = Color.WHITE
//                textPaint.textSize = 0.07f
//                textPaint.textAlign = Paint.Align.CENTER
//
//                // Переворачиваем Canvas на 180 градусов
//                canvas?.save()
//                canvas?.rotate(180f, 0.0f, textY)
//                canvas?.scale(-1f, 1f, 0.0f, textY) // Отразить по горизонтали
//                if(currentValue == 0) {
//                    val valueString = currentValue.toString()
//                    var textX = 0.32f // Центрирование текста относительно X
//
//                    canvas?.drawText("0", textX + 0.04f, textY, textPaint)
//                    textX = -0.44f
//
//                    canvas?.drawText("0", textX + 0.04f, textY, textPaint)
//                } else {
//                    val valueString = currentValue.toString()
//                    var textX = 0.36f // Центрирование текста относительно X
//                    canvas?.drawText(valueString, textX, textY, textPaint)
//                    canvas?.drawText("0", textX + 0.04f, textY, textPaint)
//                    textX = -0.4f
//                    canvas?.drawText(valueString, textX, textY, textPaint)
//                    canvas?.drawText("0", textX + 0.04f, textY, textPaint)
//                    textX = -0.465f
//                    canvas?.drawText("-", textX + 0.04f, textY, textPaint)
//                    textX = 0.28f
//                    canvas?.drawText("-", textX + 0.04f, textY, textPaint)
//                }
//                canvas?.restore()
//                currentValue -= 1
//            }
//        }
//-------------------------------------------
//        //линии
//        var strokeHight = 0.0025f
//
//        paint.color = Color.WHITE
//        paint.style = Paint.Style.STROKE
//        paint.strokeWidth = strokeHight + 0.001f
//        canvas?.drawLine(-0.4f, angleY * -1f, 0.4F, angleY * -1f, paint)
//
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) + 0.1f, 0.2F, (angleY * -1f) + 0.1f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.25f, (angleY * -1f) + 0.2f, 0.25F, (angleY * -1f) + 0.2f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) + 0.3f, 0.2F, (angleY * -1f) + 0.3f, paint)
//        paint.strokeWidth = strokeHight + 0.001f
//        canvas?.drawLine(-0.4f, (angleY * -1f) + 0.4f, 0.4F, (angleY * -1f) + 0.4f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) + 0.5f, 0.2F, (angleY * -1f) + 0.5f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.25f, (angleY * -1f) + 0.6f, 0.25F, (angleY * -1f) + 0.6f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) + 0.7f, 0.2F, (angleY * -1f) + 0.7f, paint)
//        paint.strokeWidth = strokeHight + 0.001f
//        canvas?.drawLine(-0.4f, (angleY * -1f) + 0.8f, 0.4F, (angleY * -1f) + 0.8f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) + 0.9f, 0.2F, (angleY * -1f) + 0.9f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.25f, (angleY * -1f) + 1f, 0.25F, (angleY * -1f) + 1f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) + 1.1f, 0.2F, (angleY * -1f) + 1.1f, paint)
//        paint.strokeWidth = strokeHight + 0.001f
//        canvas?.drawLine(-0.4f, (angleY * -1f) + 1.2f, 0.4F, (angleY * -1f) + 1.2f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) + 1.3f, 0.2F, (angleY * -1f) + 1.3f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.25f, (angleY * -1f) + 1.4f, 0.25F, (angleY * -1f) + 1.4f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) + 1.6f, 0.2F, (angleY * -1f) + 1.6f, paint)
//        paint.strokeWidth = strokeHight + 0.001f
//        canvas?.drawLine(-0.4f, (angleY * -1f) + 1.7f, 0.4F, (angleY * -1f) + 1.7f, paint)

//
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) - 0.1f, 0.2F, (angleY * -1f) - 0.1f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.25f, (angleY * -1f) - 0.2f, 0.25F, (angleY * -1f) - 0.2f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) - 0.3f, 0.2F, (angleY * -1f) - 0.3f, paint)
//        paint.strokeWidth = strokeHight + 0.001f
//        canvas?.drawLine(-0.4f, (angleY * -1f) - 0.4f, 0.4F, (angleY * -1f) - 0.4f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) - 0.5f, 0.2F, (angleY * -1f) - 0.5f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.25f, (angleY * -1f) - 0.6f, 0.25F, (angleY * -1f) - 0.6f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) - 0.7f, 0.2F, (angleY * -1f) - 0.7f, paint)
//        paint.strokeWidth = strokeHight + 0.001f
//        canvas?.drawLine(-0.4f, (angleY * -1f) - 0.8f, 0.4F, (angleY * -1f) - 0.8f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) - 0.9f, 0.2F, (angleY * -1f) - 0.9f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.25f, (angleY * -1f) - 1f, 0.25F, (angleY * -1f) - 1f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) - 1.1f, 0.2F, (angleY * -1f) - 1.1f, paint)
//        paint.strokeWidth = strokeHight + 0.001f
//        canvas?.drawLine(-0.4f, (angleY * -1f) - 1.2f, 0.4F, (angleY * -1f) - 1.2f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) - 1.3f, 0.2F, (angleY * -1f) - 1.3f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.25f, (angleY * -1f) - 1.4f, 0.25F, (angleY * -1f) - 1.4f, paint)
//        paint.strokeWidth = strokeHight
//        canvas?.drawLine(-0.2f, (angleY * -1f) - 1.5f, 0.2F, (angleY * -1f) - 1.5f, paint)
//        paint.strokeWidth = strokeHight + 0.001f
//        canvas?.drawLine(-0.4f, (angleY * -1f) - 1.6f, 0.4F, (angleY * -1f) - 1.6f, paint)


        canvas.rotate(140f)
        // цифры
        val scalePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        scalePaint.color = Color.YELLOW
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
                scalePaint.color = Color.YELLOW
                scalePaint.strokeWidth = 0.02F
            }
            canvas.drawLine(x1, y1, x2, y2, scalePaint)
        }


        canvas?.restore()

    }


    fun roll(value: Float) {
        //  angleX = value

        val animator = ValueAnimator.ofFloat(angleX, value)
        animator.duration = (10 + abs(angleX - value) * 2).toLong()
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation ->
            angleX = animation.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    fun pitch(value: Float) {
        //  angleY = value
        val animator = ValueAnimator.ofFloat(angleY, value)
        animator.duration = (100 + abs(angleY - value) * 2).toLong()
        //  Log.d("DURATION_YY", "$angleY")
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation ->
            angleY = animation.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    fun setZZ(value: Float) {
        angleZ = value
    }

}

