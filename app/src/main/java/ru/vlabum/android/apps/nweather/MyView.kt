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
    private val RADIUS_ARC = 2.5f

    private lateinit var paint: Paint
    private var radius: Float = 0f
    private var centerWith: Float = 0f
    private var centerHeight: Float = 0f
    private lateinit var rect: RectF

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
        val rectHalf = (if (w < h) w / RADIUS_ARC else h / RADIUS_ARC)
        rect = RectF(
            centerWith - rectHalf,
            centerHeight - rectHalf,
            centerWith + rectHalf,
            centerHeight + rectHalf
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(centerWith, centerHeight, radius, paint)
        canvas?.drawArc(rect, -10f, 20f, true, paint)
        canvas?.drawArc(rect, 40f, 10f, true, paint)
        canvas?.drawArc(rect, 80f, 20f, true, paint)
        canvas?.drawArc(rect, 130f, 10f, true, paint)
        canvas?.drawArc(rect, 170f, 20f, true, paint)
        canvas?.drawArc(rect, 220f, 10f, true, paint)
        canvas?.drawArc(rect, 260f, 20f, true, paint)
    }
}