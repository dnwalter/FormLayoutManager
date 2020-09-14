package com.ousy.formlayoutmanager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dnwalter.formlayoutmanager.helper.FormScrollHelper;
import com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ousy.formlayoutmanager.adapter.MonsterHAdapter;
import com.ousy.formlayoutmanager.adapter.TitleAdapter;
import com.ousy.formlayoutmanager.entity.DataJson;
import com.ousy.formlayoutmanager.entity.Monster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HFormActivity extends AppCompatActivity {
    private RecyclerView rvTitle;
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hform);

        mRecyclerView = findViewById(R.id.rv_horizontal);
        rvTitle = findViewById(R.id.rv_title);

        rvTitle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        String[] titles = getResources().getStringArray(R.array.form_title);
        List<String> titleList = Arrays.asList(titles);
        TitleAdapter rightTitleAdapter = new TitleAdapter(titleList);
        rvTitle.setAdapter(rightTitleAdapter);

        FormLayoutManager layoutManager = new FormLayoutManager(8, mRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("加载数据中……");
        mProgressDialog.show();
        layoutManager.setHandler(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == FormLayoutManager.FIRST_INIT_FINISH) {
                    mProgressDialog.dismiss();
                }
            }
        });

        List<Monster> list = new Gson().fromJson(DataJson.MONSTER_DATA, new TypeToken<List<Monster>>(){}.getType());
        List<Monster> listxx = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            listxx.add(list.get(i));
        }
        list.addAll(listxx);

        MonsterHAdapter adapter = new MonsterHAdapter(this, list);
        mRecyclerView.setAdapter(adapter);

        FormScrollHelper formScrollHelper = new FormScrollHelper();
        formScrollHelper.connectRecyclerView(mRecyclerView);
        formScrollHelper.connectRecyclerView(rvTitle);
    }
}
