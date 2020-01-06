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
import com.ousy.formlayoutmanager.adapter.MonsterHAdapterByType;
import com.ousy.formlayoutmanager.adapter.TitleAdapterbyType;
import com.ousy.formlayoutmanager.entity.DataJson;
import com.ousy.formlayoutmanager.entity.Monster;

import java.util.Arrays;
import java.util.List;

/**
 * 多类型的表格
 * 这个表演的是列的宽不一样
 */
public class HForm2Activity extends AppCompatActivity {
    private RecyclerView rvTitle;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hform);

        mRecyclerView = findViewById(R.id.rv_horizontal);
        rvTitle = findViewById(R.id.rv_title);

        rvTitle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        String[] titles = getResources().getStringArray(R.array.form_title);
        List<String> titleList = Arrays.asList(titles);
        TitleAdapterbyType rightTitleAdapter = new TitleAdapterbyType(titleList);
        rvTitle.setAdapter(rightTitleAdapter);

        FormLayoutManager layoutManager = new FormLayoutManager(8);
        mRecyclerView.setLayoutManager(layoutManager);

        List<Monster> list = new Gson().fromJson(DataJson.MONSTER_DATA, new TypeToken<List<Monster>>(){}.getType());
        MonsterHAdapterByType adapter = new MonsterHAdapterByType(this, list);
        mRecyclerView.setAdapter(adapter);

        FormScrollHelper formScrollHelper = new FormScrollHelper();
        formScrollHelper.connectRecyclerView(mRecyclerView);
        formScrollHelper.connectRecyclerView(rvTitle);
    }
}
