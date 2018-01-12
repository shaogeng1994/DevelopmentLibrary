package com.shao.developmentlibrary.widget

import android.content.Context
import android.databinding.BindingAdapter
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.support.v4.content.ContextCompat
import android.support.annotation.ColorRes
import android.support.annotation.ColorInt
import com.shao.developmentlibrary.R
import kotlinx.android.synthetic.main.sg_easy_item.view.*


/**
 * Created by Administrator on 2017/8/16.
 */
open class EasyItem(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
        LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    lateinit var container: LinearLayout
    lateinit var title: TextView
    lateinit var rightTextView: TextView

    constructor(context: Context): this(context, null, 0, 0) {initEasyItem(context, null)}

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0, 0) {initEasyItem(context, attrs)}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs, defStyleAttr, 0) {initEasyItem(context, attrs)}



    fun initEasyItem(context: Context, attrs: AttributeSet?) {
        orientation = VERTICAL
        //标题文本
        var titleText = ""
        //右侧文本
        var rightText = ""
        //标题style
        var titleStyleRec = -1
        //容器右侧内边距
        var paddingRight = -1f
        //容器左侧内边距
        var paddingLeft = -1f
        var backgroundColor = -1
        var backgroundRes = -1
        var leftIconRec = -1
        var rightTitleStyleRec = -1
        var rightIconRec = -1
        var hasBottomLine = false

        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.EasyItem)
            try {
                titleStyleRec = ta.getResourceId(R.styleable.EasyItem_ei_title_style, R.style.eiDefTitle)
                if (ta.hasValue(R.styleable.EasyItem_ei_title_text)) titleText = ta.getString(R.styleable.EasyItem_ei_title_text)
                if (ta.hasValue(R.styleable.EasyItem_ei_right_text)) rightText = ta.getString(R.styleable.EasyItem_ei_right_text)
                rightTitleStyleRec = ta.getResourceId(R.styleable.EasyItem_ei_right_text_style, R.style.eiDefRightText)
                paddingLeft = ta.getDimension(R.styleable.EasyItem_ei_padding_left, dip2px(context, 10f).toFloat())
                paddingRight = ta.getDimension(R.styleable.EasyItem_ei_padding_right, dip2px(context, 10f).toFloat())
                backgroundColor = ta.getColor(R.styleable.EasyItem_ei_background_color, -1)
                leftIconRec = ta.getResourceId(R.styleable.EasyItem_ei_left_icon_src, -1)
                rightIconRec = ta.getResourceId(R.styleable.EasyItem_ei_right_icon_src, -1)
                hasBottomLine = ta.getBoolean(R.styleable.EasyItem_ei_bottom_line, false)
                backgroundRes = ta.getResourceId(R.styleable.EasyItem_ei_background, -1)
            } finally {
                ta.recycle()
            }
        }

        View.inflate(context, R.layout.sg_easy_item, this)
        val relativeLayout = sg_easy_item_title_layout
        relativeLayout.setPadding(paddingLeft.toInt(), 0, paddingRight.toInt(), 0)
        container = sg_easy_item_container

        if (backgroundColor != -1) relativeLayout.setBackgroundColor(backgroundColor)

        if (backgroundRes != -1) relativeLayout.setBackgroundResource(backgroundRes)

        title = sg_easy_item_title
        val leftImageView = sg_easy_item_icon
        val rightImageView = sg_easy_item_right_icon
        rightTextView = sg_easy_item_right_title

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            title.setTextAppearance(titleStyleRec)
            rightTextView.setTextAppearance(rightTitleStyleRec)
        } else {
            title.setTextAppearance(context, titleStyleRec)
            rightTextView.setTextAppearance(context, rightTitleStyleRec)
        }

        if (!TextUtils.isEmpty(titleText)) {
            title.visibility = View.VISIBLE
            title.text = titleText
        }

        if (leftIconRec != -1) leftImageView.setImageResource(leftIconRec)

        if (rightIconRec != -1) rightImageView.setImageResource(rightIconRec)

        if (!TextUtils.isEmpty(rightText)) rightTextView.text = rightText

        if (hasBottomLine) sg_easy_item_line.visibility = View.VISIBLE
    }


    fun setTitle(titleText: String?) {
        if (titleText != null) {
            title.visibility = View.VISIBLE
            title.text = titleText
        }
    }

    fun setTitleColor(@ColorInt color: Int) {
        title.setTextColor(color)
    }

    fun setTitleColorRes(@ColorRes colorRes: Int) {
        title.setTextColor(ContextCompat.getColor(context, colorRes))
    }

    fun setRightText(text: String) {
        if (rightTextView != null) rightTextView.text = text
    }


    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    companion object {
        @BindingAdapter("app:ei_right_text")
        @JvmStatic
        fun setRightText(view: EasyItem, text: String) {
            view.setRightText(text)
        }
    }
}