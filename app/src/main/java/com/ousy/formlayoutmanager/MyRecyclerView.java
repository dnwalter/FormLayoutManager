package com.ousy.formlayoutmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager;

/**
 * @author ousiyuan
 * @date 2020/7/14
 */
public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        if (getLayoutManager() instanceof FormLayoutManager) {
            FormLayoutManager layoutManager = (FormLayoutManager) getLayoutManager();
            if (direction < 0){
                return getLayoutManager().canScrollVertically() && !layoutManager.isScrollTop();
            }else {
                return getLayoutManager().canScrollVertically() && !layoutManager.isScrollBottom();
            }
        }else {
            return super.canScrollVertically(direction);
        }
    }
}
