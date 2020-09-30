package com.ousy.formlayoutmanager.editForm;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author ousiyuan
 * @date 2020/9/30
 */
@IntDef({TitleType.NAME, TitleType.ATTRIBUTE, TitleType.LV, TitleType.ATK, TitleType.DEF, TitleType.RACE, TitleType.TYPE1, TitleType.TYPE2})
@Retention(RetentionPolicy.SOURCE)
public @interface TitleType {
    int NAME = 0;
    int ATTRIBUTE = 1;
    int LV = 2;
    int ATK = 3;
    int DEF = 4;
    int RACE = 5;
    int TYPE1 = 6;
    int TYPE2 = 7;
}
