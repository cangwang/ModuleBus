package com.cangwang.splash.view

import android.annotation.SuppressLint
import android.text.TextPaint
import com.cangwang.splash.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import java.lang.IllegalArgumentException
import java.lang.reflect.Field

/**
 * ${Author}: 徐坤
 * ${CreateTime}:16/5/24
 * ${Description}:
 * ${EMail}:xukun1007@163.com
 */
class StrokeTextView : TextView {
    var m_TextPaint: TextPaint
    var mInnerColor: Int
    var mOuterColor: Int

    constructor(context: Context?, outerColor: Int, innnerColor: Int) : super(context) {
        m_TextPaint = this.paint
        mInnerColor = innnerColor
        mOuterColor = outerColor

        // TODO Auto-generated constructor stub
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        m_TextPaint = this.paint
        val a = context.obtainStyledAttributes(attrs, R.styleable.splash_StrokeTextView)
        mInnerColor = a.getColor(R.styleable.splash_StrokeTextView_splash_innnerColor, 0xffffff)
        mOuterColor = a.getColor(R.styleable.splash_StrokeTextView_splash_outerColor, 0xffffff)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int, outerColor: Int, innnerColor: Int) : super(context, attrs, defStyle) {
        m_TextPaint = this.paint
        mInnerColor = innnerColor
        mOuterColor = outerColor
        // TODO Auto-generated constructor stub
    }

    private val m_bDrawSideLine = true // 默认采用描边

    /**
     *
     */
    override fun onDraw(canvas: Canvas) {
        if (m_bDrawSideLine) {
            // 描外层
            setTextColorUseReflection(mOuterColor)
            m_TextPaint.strokeWidth = 5f
            m_TextPaint.style = Paint.Style.FILL_AND_STROKE
            super.onDraw(canvas)

            // 描内层，恢复原先的画笔
            setTextColorUseReflection(mInnerColor)
            m_TextPaint.strokeWidth = 0f
            m_TextPaint.style = Paint.Style.FILL_AND_STROKE
        }
        super.onDraw(canvas)
    }

    /**
     * 使用反射的方法进行字体颜色的设置
     * @param color
     */
    @SuppressLint("SoonBlockedPrivateApi")
    private fun setTextColorUseReflection(color: Int) {
        val textColorField: Field
        try {
            textColorField = TextView::class.java.getDeclaredField("mCurTextColor")
            textColorField.isAccessible = true
            textColorField[this] = color
            textColorField.isAccessible = false
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        m_TextPaint.color = color
    }
}