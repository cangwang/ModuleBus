package com.cangwang.base.util;

import android.graphics.Color;

import java.util.Random;

/**颜色工具
 * Created by cangwang on 2018/2/5.
 */

public class ColorUtil {

    public static int getRandomColor(){
        String r,g,b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length()==1 ? "0" + r : r ;
        g = g.length()==1 ? "0" + g : g ;
        b = b.length()==1 ? "0" + b : b ;
        return Color.parseColor("#" + r + g + b);
    }
}
