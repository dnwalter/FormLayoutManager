package com.ousy.formlayoutmanager.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author ousiyuan
 * @date 2019/12/2
 */
public class DensityUtils {
    public static int dp2px(Context context, float value) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (value * metrics.density + 0.5f);
    }
}
