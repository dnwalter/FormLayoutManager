package com.ousy.formlayoutmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.dnwalter.formlayoutmanager.helper.FormScrollHelper;
import com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ousy.formlayoutmanager.adapter.MonsterHAdapter;
import com.ousy.formlayoutmanager.adapter.TitleAdapter;
import com.ousy.formlayoutmanager.entity.DataJson;
import com.ousy.formlayoutmanager.entity.Monster;

import java.util.Arrays;
import java.util.List;

/**
 * @author ousiyuan
 * @date 2019/12/2
 */
public class HVFormActivity extends AppCompatActivity {
    private RecyclerView rvTopTitle;
    private RecyclerView rvLeftTitle;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hvform);

        mRecyclerView = findViewById(R.id.rv_form);
        rvTopTitle = findViewById(R.id.rv_top_title);
        rvLeftTitle = findViewById(R.id.rv_left_title);

        rvLeftTitle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        List<String> leftTitles = Arrays.asList("1","2","3","4","5","6","7","8","9","10","11");
        TitleAdapter leftAdapter = new TitleAdapter(leftTitles);
        rvLeftTitle.setAdapter(leftAdapter);

        rvTopTitle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        String[] titles = getResources().getStringArray(R.array.form_title);
        List<String> titleList = Arrays.asList(titles);
        TitleAdapter topAdapter = new TitleAdapter(titleList);
        rvTopTitle.setAdapter(topAdapter);

        FormLayoutManager layoutManager = new FormLayoutManager(8);
        mRecyclerView.setLayoutManager(layoutManager);

        List<Monster> list = new Gson().fromJson(DataJson.MONSTER_DATA, new TypeToken<List<Monster>>(){}.getType());
        MonsterHAdapter adapter = new MonsterHAdapter(this, list);
        mRecyclerView.setAdapter(adapter);

        FormScrollHelper formScrollHelper = new FormScrollHelper();
        formScrollHelper.connectRecyclerView(mRecyclerView);
        formScrollHelper.connectRecyclerView(rvTopTitle);
        formScrollHelper.connectRecyclerView(rvLeftTitle);
    }
}
