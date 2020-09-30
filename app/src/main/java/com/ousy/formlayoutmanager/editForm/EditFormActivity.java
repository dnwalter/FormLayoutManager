package com.ousy.formlayoutmanager.editForm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dnwalter.formlayoutmanager.helper.FormScrollHelper;
import com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ousy.formlayoutmanager.R;
import com.ousy.formlayoutmanager.adapter.TitleAdapter;
import com.ousy.formlayoutmanager.entity.DataJson;
import com.ousy.formlayoutmanager.entity.Monster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ousiyuan
 * @date 2020/9/30
 */
public class EditFormActivity extends AppCompatActivity {
    private RecyclerView rvTitle;
    private RecyclerView mRecyclerView;
    private MonsterEditAdapter mAdapter;
    private Map<Integer, String> mTitleMap = new HashMap<>();
    private TitleAdapter mRightTitleAdapter;
    private FormLayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);

        mRecyclerView = findViewById(R.id.rv_horizontal);
        rvTitle = findViewById(R.id.rv_title);

        mTitleMap.put(TitleType.NAME, "名称");
        mTitleMap.put(TitleType.ATTRIBUTE, "属性");
        mTitleMap.put(TitleType.LV, "等级/阶级");
        mTitleMap.put(TitleType.ATK, "攻击力");
        mTitleMap.put(TitleType.DEF, "防御力");
        mTitleMap.put(TitleType.RACE, "种族");
        mTitleMap.put(TitleType.TYPE1, "类型1");
        mTitleMap.put(TitleType.TYPE2, "类型2");

        List<Monster> list = new Gson().fromJson(DataJson.MONSTER_DATA, new TypeToken<List<Monster>>(){}.getType());
        mAdapter = new MonsterEditAdapter(this, list);

        mLayoutManager = new FormLayoutManager(mAdapter.getColumnCount(), mRecyclerView);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        rvTitle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        mRightTitleAdapter = new TitleAdapter(getTitleList(mAdapter.getTitleTypeList()));
        rvTitle.setAdapter(mRightTitleAdapter);

        FormScrollHelper formScrollHelper = new FormScrollHelper();
        formScrollHelper.connectRecyclerView(mRecyclerView);
        formScrollHelper.connectRecyclerView(rvTitle);
    }

    public void onEditClick(View view) {
        List<Integer> list = Arrays.asList(TitleType.ATTRIBUTE, TitleType.LV, TitleType.NAME, TitleType.ATK, TitleType.TYPE1, TitleType.TYPE2, TitleType.DEF, TitleType.RACE);
        rvTitle.smoothScrollToPosition(0);
        mAdapter.setTitleTypeList(list);
        mLayoutManager.setColumnCount(mAdapter.getColumnCount());
        mRightTitleAdapter.refreshData(getTitleList(mAdapter.getTitleTypeList()));
        mAdapter.notifyDataSetChanged();
    }

    private List<String> getTitleList(List<Integer> types) {
        List<String> list = new ArrayList<>();
        for (Integer type : types) {
            list.add(mTitleMap.get(type));
        }

        return list;
    }
}
