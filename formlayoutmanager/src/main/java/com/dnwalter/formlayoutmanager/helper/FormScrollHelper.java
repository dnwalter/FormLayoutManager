package com.dnwalter.formlayoutmanager.helper;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ousiyuan
 * @date 2019/12/2
 */
public class FormScrollHelper {
    private List<RecyclerView> mRecyclerViews = new ArrayList<>();

    public void connectRecyclerView(RecyclerView recyclerView){
        recyclerView.addOnScrollListener(mScrollListener);
        mRecyclerViews.add(recyclerView);
    }

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            // 以下场景处理的是，滑动过程中手指松开，移到另外一个recyclerview进行滚动
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                // 该recyclerview进入滚动状态的时候，其余四个要先停止滚动
                int currentIndex = mRecyclerViews.indexOf(recyclerView);
                for (int i = 0; i < mRecyclerViews.size(); i++) {
                    if (i != currentIndex) {
                        mRecyclerViews.get(i).stopScroll();
                    }
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                // 手指所放在的当前recyclerview的下标
                int currentIndex = mRecyclerViews.indexOf(recyclerView);
                for (int i = 0; i < mRecyclerViews.size(); i++) {
                    if (i != currentIndex) {
                        mRecyclerViews.get(i).scrollBy(dx, dy);
                    }
                }
            }
        }
    };
}
