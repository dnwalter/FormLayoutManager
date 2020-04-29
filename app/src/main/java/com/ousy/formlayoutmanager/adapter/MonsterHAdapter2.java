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
    protected String getRowData(Monster model, int index) {
        String result = "";
        switch (index) {
            case 0:
                result = model.getType2();
                break;
            case 1:
                result = model.getType1();
                break;
            case 2:
                result = model.getRace();
                break;
            case 3:
                result = model.getDef();
                break;
            case 4:
                result = model.getAtk();
                break;
            case 5:
                result = model.getLv();
                break;
            case 6:
                result = model.getAttribute();
                break;
            case 7:
                result = model.getName();
                break;
        }

        return result;
    }
}
