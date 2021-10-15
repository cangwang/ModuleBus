package com.cangwang.base.util

import android.graphics.*
import java.util.*

/**颜色工具
 * Created by cangwang on 2018/2/5.
 */
object ColorUtil {
    @JvmStatic
    val randomColor: Int
        get() {
            var r: String
            var g: String
            var b: String
            val random = Random()
            r = Integer.toHexString(random.nextInt(256)).uppercase()
            g = Integer.toHexString(random.nextInt(256)).uppercase()
            b = Integer.toHexString(random.nextInt(256)).uppercase()
            r = if (r.length == 1) "0$r" else r
            g = if (g.length == 1) "0$g" else g
            b = if (b.length == 1) "0$b" else b
            return Color.parseColor("#$r$g$b")
        }
}