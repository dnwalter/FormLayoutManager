package com.dnwalter.formlayoutmanager.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ousiyuan
 * @date 2020/2/9
 */
public abstract class BaseFormModel {
    protected List<Integer> textColors = new ArrayList<>();
    protected List<Integer> bgColors = new ArrayList<>();

    public List<Integer> getTextColors() {
        return textColors;
    }

    public void setTextColors(List<Integer> textColors) {
        this.textColors = textColors;
    }

    public List<Integer> getBgColors() {
        return bgColors;
    }

    public void setBgColors(List<Integer> bgColors) {
        this.bgColors = bgColors;
    }
}
