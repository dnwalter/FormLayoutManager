package com.ousy.formlayoutmanager.adapter;

import android.content.Context;


import com.ousy.formlayoutmanager.entity.Monster;

import java.util.List;

public class MonsterHAdapter2 extends MonsterHAdapter {

    public MonsterHAdapter2(Context context) {
        super(context);
    }

    public MonsterHAdapter2(Context context, List<Monster> list) {
        super(context, list);
    }

    @Override
    protected String[] getRowDatas(Monster model) {
        return new String[]{model.getType2(), model.getType1(), model.getRace(), model.getDef(), model.getAtk(), model.getLv(), model.getAttribute(), model.getName()};
    }
}
