package com.ousy.formlayoutmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager;
import com.ousy.formlayoutmanager.adapter.TestAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ousiyuan
 * @date 2020/7/13
 */
public class IssuesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FormLayoutManager mLayoutManager;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);

        mRecyclerView = findViewById(R.id.recycler_view);

        mLayoutManager = new FormLayoutManager(10, mRecyclerView);
        // 起始位置滚动到右底部
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i + "");
        }
        TestAdapter adapter = new TestAdapter(this, list);

        mRecyclerView.setAdapter(adapter);

        bottomSheetBehavior = BottomSheetBehavior.from(mRecyclerView);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void onTestClick(View view){
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}
