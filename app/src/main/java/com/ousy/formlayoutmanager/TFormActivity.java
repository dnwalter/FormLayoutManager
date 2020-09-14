package com.ousy.formlayoutmanager;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ousy.formlayoutmanager.adapter.MonsterHAdapter;
import com.ousy.formlayoutmanager.adapter.MonsterHAdapter2;
import com.ousy.formlayoutmanager.adapter.TitleAdapter;
import com.ousy.formlayoutmanager.entity.DataJson;
import com.ousy.formlayoutmanager.entity.Monster;
import com.ousy.formlayoutmanager.helper.TFormScrollHelper;
import com.ousy.formlayoutmanager.utils.DensityUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ousiyuan
 * @date 2019/12/2
 */
public class TFormActivity extends AppCompatActivity {
    private RecyclerView rvLeftTitle;
    private RecyclerView rvRightTitle;
    private RecyclerView rvLeftForm;
    private RecyclerView rvRightForm;
    private RecyclerView rvNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tform);

        rvLeftTitle = findViewById(R.id.rv_left_title);
        rvRightTitle = findViewById(R.id.rv_right_title);
        rvLeftForm = findViewById(R.id.rv_left_form);
        rvRightForm = findViewById(R.id.rv_right_form);
        rvNum = findViewById(R.id.rv_num);

        // 设置RecyclerView联动
        TFormScrollHelper scrollHelper = new TFormScrollHelper();
        scrollHelper.connectRecyclerView(rvLeftTitle, TFormScrollHelper.LEFT_AREA);
        scrollHelper.connectRecyclerView(rvLeftForm, TFormScrollHelper.LEFT_AREA);
        scrollHelper.connectRecyclerView(rvNum, TFormScrollHelper.CENTER_AREA);
        scrollHelper.connectRecyclerView(rvRightTitle, TFormScrollHelper.RIGHT_AREA);
        scrollHelper.connectRecyclerView(rvRightForm, TFormScrollHelper.RIGHT_AREA);

        rvNum.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        List<String> numTitles = Arrays.asList("1","2","3","4","5","6","7","8","9","10","11");
        TitleAdapter numAdapter = new TitleAdapter(numTitles);
        rvNum.setAdapter(numAdapter);

        rvRightTitle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        String[] titles = getResources().getStringArray(R.array.form_title);
        List<String> rightTitles = Arrays.asList(titles);
        TitleAdapter rightTitleAdapter = new TitleAdapter(rightTitles);
        rvRightTitle.setAdapter(rightTitleAdapter);

        rvLeftTitle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        List<String> leftTitles = new ArrayList<>();
        for (int i = titles.length - 1; i >= 0; i--){
            leftTitles.add(titles[i]);
        }
        TitleAdapter leftTitleAdapter = new TitleAdapter(leftTitles);
        rvLeftTitle.setAdapter(leftTitleAdapter);
        rvLeftTitle.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                view.removeOnLayoutChangeListener(this);
                // 让title滚到最右边，因为这个title只有8列，所以我写死8个item宽度的距离
                view.scrollBy(8 * DensityUtils.dp2px(TFormActivity.this, 100), 0);
            }
        });

        List<Monster> list = new Gson().fromJson(DataJson.MONSTER_DATA, new TypeToken<List<Monster>>(){}.getType());

        FormLayoutManager rightLayoutManager = new FormLayoutManager(8, rvRightForm);
        rvRightForm.setLayoutManager(rightLayoutManager);
        MonsterHAdapter rightAdapter = new MonsterHAdapter(this, list);
        rvRightForm.setAdapter(rightAdapter);

        FormLayoutManager leftLayoutManager = new FormLayoutManager(8, rvLeftForm);
        leftLayoutManager.startShow(FormLayoutManager.StartShowType.RIGHT);
        rvLeftForm.setLayoutManager(leftLayoutManager);
        MonsterHAdapter2 leftAdapter = new MonsterHAdapter2(this, list);
        rvLeftForm.setAdapter(leftAdapter);
    }
}
