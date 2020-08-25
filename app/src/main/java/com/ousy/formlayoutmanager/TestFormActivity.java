package com.ousy.formlayoutmanager;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager;
import com.ousy.formlayoutmanager.adapter.TestAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestFormActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    CheckBox cbV;
    CheckBox cbH;
    private FormLayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_form);

        mRecyclerView = findViewById(R.id.rv_test);
        cbH = findViewById(R.id.cb_h);
        cbV = findViewById(R.id.cb_v);

        mLayoutManager = new FormLayoutManager(10);
        // 起始位置滚动到右底部
        mLayoutManager.startShow(FormLayoutManager.StartShowType.RIGHT | FormLayoutManager.StartShowType.BOTTOM);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i + "");
        }
        TestAdapter adapter = new TestAdapter(this, list);

        mRecyclerView.setAdapter(adapter);

        cbH.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mLayoutManager.setCanScrollH(b);
            }
        });

        cbV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mLayoutManager.setCanScrollV(b);
            }
        });
    }

    public void onTestClick(View view){
        if (mRecyclerView.getVisibility() == View.VISIBLE){
            mRecyclerView.setVisibility(View.GONE);
        }else{
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
