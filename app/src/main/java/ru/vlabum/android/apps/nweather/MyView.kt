package ru.vlabum.android.apps.nweather

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

open class MyView : View {

    private val RADIUS_D = 5f
    private val HALF_ARC_W = 2.5f
    private val HALF_ARC_H = 2f

    private lateinit var paint: Paint
    private var radius: Float = 0f
    private var centerWith: Float = 0f
    private var centerHeight: Float = 0f
    private lateinit var rect: RectF
    private lateinit var rectHi: RectF

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        paint = Paint()
        paint.color = Color.YELLOW
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerWith = w / 2f
        centerHeight = h / 2f
        radius = (if (w < h) w / RADIUS_D else h / RADIUS_D)
        val rectHalfW = (if (w < h) w / HALF_ARC_W else h / HALF_ARC_W)
        val rectHalfH = (if (w < h) w / HALF_ARC_H else h / HALF_ARC_H)
        rect = RectF(
            centerWith - rectHalfW,
            centerHeight - rectHalfW,
            centerWith + rectHalfW,
            centerHeight + rectHalfW
        )
        rectHi = RectF(
            centerWith - rectHalfH,
            centerHeight - rectHalfH,
            centerWith + rectHalfH,
            centerHeight + rectHalfH
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(centerWith, centerHeight, radius, paint)
        canvas?.drawArc(rect, 350f, 20f, true, paint)
        canvas?.drawArc(rect, 80f, 20f, true, paint)
        canvas?.drawArc(rect, 170f, 20f, true, paint)
        canvas?.drawArc(rect, 260f, 20f, true, paint)
        canvas?.drawArc(rect, 40f, 10f, true, paint)
        canvas?.drawArc(rect, 220f, 10f, true, paint)
        canvas?.drawArc(rectHi, 120f, 10f, true, paint)
        canvas?.drawArc(rectHi, 300f, 10f, true, paint)
    }
}