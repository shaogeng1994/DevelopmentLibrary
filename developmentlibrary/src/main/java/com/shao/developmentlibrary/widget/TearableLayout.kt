package com.shao.developmentlibrary.widget

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.shao.developmentlibrary.R


/**
 * Created by Administrator on 2017/11/30.
 */
class TearableLayout(context: Context, attributeSet: AttributeSet, defStyleAttr: Int = 0):
        ViewGroup(context, attributeSet, defStyleAttr) {
    init {
        setWillNotDraw(false)
        initAttrs(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet): this(context, attributeSet, 0)

    var childSpacing = 40
    var corner = 20
    var tlBackgroundColor = 0xffffffff.toInt()

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    fun initAttrs(attributeSet: AttributeSet) {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.TearableLayout)
        try {
            childSpacing = ta.getDimensionPixelSize(R.styleable.TearableLayout_tl_margin, 40)
            corner = ta.getDimensionPixelSize(R.styleable.TearableLayout_tl_corner, 20)
            tlBackgroundColor = ta.getColor(R.styleable.TearableLayout_tl_background_color, 0xffffffff.toInt())
        } finally {
            ta.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val sizeWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = View.MeasureSpec.getSize(heightMeasureSpec)


        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        var width = 0
        var height = 0

        val cCount = childCount

        var cWidth = 0
        var cHeight = 0
        var cParams: ViewGroup.MarginLayoutParams? = null

        for (i in 0 until cCount) {
            val childView = getChildAt(i)
            cWidth = childView.measuredWidth
            cHeight = childView.measuredHeight
            cParams = childView.layoutParams as ViewGroup.MarginLayoutParams

            width += cWidth + cParams.leftMargin + cParams.rightMargin
            height += cHeight + cParams.topMargin + cParams.bottomMargin + childSpacing * 2
        }

        setMeasuredDimension(if (widthMode == View.MeasureSpec.EXACTLY)
            sizeWidth
        else
            width, height)
//        setMeasuredDimension(sizeWidth, height)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

        val cCount = childCount
        var cWidth = 0
        var cHeight = 0
        var cParams: ViewGroup.MarginLayoutParams? = null

        var startHeight = 0
        /**
         * 遍历所有childView根据其宽和高，以及margin进行布局
         */
        for (i in 0 until cCount) {
            val childView = getChildAt(i)
            cWidth = childView.measuredWidth
            cHeight = childView.measuredHeight
            cParams = childView.layoutParams as ViewGroup.MarginLayoutParams

            var cl = 0
            var ct = 0
            var cr = 0
            var cb = 0

            cl = childSpacing
            cr = cWidth - childSpacing
            ct = startHeight + childSpacing
            cb = cHeight + ct

            startHeight = cb + childSpacing

            childView.layout(cl, ct, cr, cb)
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawBg(canvas)
    }

    fun drawBg(canvas: Canvas?) {

        val mPaint = Paint()
        mPaint.isAntiAlias = true
        val cCount = childCount
        var cWidth = 0
        var cHeight = 0
        var cParams: ViewGroup.LayoutParams? = null
        var contentRectF: RectF? = null
        val r = corner
        val margin = childSpacing

        for (i in 0 until cCount) {
            val childView = getChildAt(i)
            cWidth = childView.measuredWidth
            cHeight = childView.measuredHeight
            cParams = childView.layoutParams as ViewGroup.MarginLayoutParams

            if (contentRectF == null) {
                contentRectF = RectF(cParams.leftMargin.toFloat(),
                        cParams.topMargin.toFloat() + corner,
                        (cParams.leftMargin + cWidth).toFloat(),
                        (cParams.topMargin + cHeight + margin * 2 - corner).toFloat())
            } else {
                contentRectF.left = cParams.leftMargin.toFloat()
                contentRectF.right = (cParams.leftMargin + cWidth).toFloat()
                contentRectF.top = contentRectF.bottom + corner * 2
                contentRectF.bottom = contentRectF.top + cHeight + margin * 2 - corner * 2
            }


//            mPaint.color = 0xffff0000.toInt()
            mPaint.color = tlBackgroundColor
            canvas?.drawRect(contentRectF, mPaint)


            //画圆角
            if (i == 0) {
                val topSpace = RectF(contentRectF?.left,
                        contentRectF?.top - r,
                        contentRectF?.right,
                        contentRectF?.top)

                val tPath1 = Path().apply { addRect(topSpace, Path.Direction.CCW) }
                val tPath2 = Path().apply { addCircle(topSpace.left, topSpace.top, r.toFloat(), Path.Direction.CCW) }
                val tPath3 = Path().apply { addCircle(topSpace.right, topSpace.top, r.toFloat(), Path.Direction.CCW) }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    tPath1.op(tPath2, Path.Op.XOR)
                    tPath1.op(tPath3, Path.Op.XOR)
                } else {
                    canvas?.clipPath(tPath2, Region.Op.DIFFERENCE)
                    canvas?.clipPath(tPath3, Region.Op.DIFFERENCE)
                }
                mPaint.color = tlBackgroundColor
                canvas?.drawPath(tPath1, mPaint)
            }

            val bottomSpace = RectF(contentRectF?.left,
                    contentRectF?.bottom,
                    contentRectF?.right,
                    contentRectF?.bottom + corner * 2)

            val bPath1 = Path().apply { addRect(bottomSpace, Path.Direction.CCW) }
            val bPath2 = Path().apply { addCircle(bottomSpace.left, bottomSpace.top + corner, corner.toFloat(), Path.Direction.CCW) }
            val bPath3 = Path().apply { addCircle(bottomSpace.right, bottomSpace.top + corner, corner.toFloat(), Path.Direction.CCW) }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                bPath1.op(bPath2, Path.Op.XOR)
                bPath1.op(bPath3, Path.Op.XOR)
            } else {
                canvas?.clipPath(bPath2, Region.Op.DIFFERENCE)
                canvas?.clipPath(bPath3, Region.Op.DIFFERENCE)
            }
            mPaint.color = tlBackgroundColor
            canvas?.drawPath(bPath1, mPaint)



            //画分割线
            if (i != cCount -1) {
                val lineSpace = resources.getDimensionPixelSize(R.dimen.x4).toFloat()
                val lineWidth = resources.getDimensionPixelSize(R.dimen.x8).toFloat()
                val linePaint = Paint()
                linePaint.color = 0xffe5e5e5.toInt()
                linePaint.pathEffect = DashPathEffect(floatArrayOf(lineWidth, lineSpace), 4f)
                linePaint.style = Paint.Style.STROKE
                linePaint.strokeWidth = resources.getDimensionPixelSize(R.dimen.x2).toFloat()
                linePaint.isAntiAlias = true

                val linePath = Path()
                linePath.moveTo(bottomSpace.left + childSpacing, bottomSpace.bottom - r)
                linePath.lineTo(bottomSpace.right - childSpacing, bottomSpace.bottom - r)
                canvas?.drawPath(linePath, linePaint)
            }
        }
    }


}