package com.ousy.formlayoutmanager;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnwalter.formlayoutmanager.helper.FormScrollHelper;
import com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ousy.formlayoutmanager.adapter.MonsterVAdapter;
import com.ousy.formlayoutmanager.adapter.TitleAdapter;
import com.ousy.formlayoutmanager.entity.DataJson;
import com.ousy.formlayoutmanager.entity.Monster;

import java.util.Arrays;
import java.util.List;

public class VFormActivity extends AppCompatActivity {
    private RecyclerView rvTitle;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vform);

        mRecyclerView = findViewById(R.id.rv_vertical);
        rvTitle = findViewById(R.id.rv_title);

        rvTitle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        String[] titles = getResources().getStringArray(R.array.form_title);
        List<String> titleList = Arrays.asList(titles);
        TitleAdapter rightTitleAdapter = new TitleAdapter(titleList);
        rvTitle.setAdapter(rightTitleAdapter);

        FormLayoutManager layoutManager = new FormLayoutManager(false, 8);
        mRecyclerView.setLayoutManager(layoutManager);

        List<Monster> list = new Gson().fromJson(DataJson.MONSTER_DATA, new TypeToken<List<Monster>>(){}.getType());
        List<Integer> colorList = Arrays.asList(0xff000000, 0xff000000,0xffff0000,0xff0000ff,0xff0000ff,0xff000000,0xff000000,0xff000000);
        for (Monster monster :list){
            monster.setTextColors(colorList);
        }
        MonsterVAdapter adapter = new MonsterVAdapter(this, list);
        mRecyclerView.setAdapter(adapter);

        FormScrollHelper formScrollHelper = new FormScrollHelper();
        formScrollHelper.connectRecyclerView(mRecyclerView);
        formScrollHelper.connectRecyclerView(rvTitle);
    }
}
