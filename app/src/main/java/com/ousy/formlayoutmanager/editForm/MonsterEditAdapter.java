package com.ousy.formlayoutmanager.editForm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dnwalter.formlayoutmanager.adapter.BaseHFormAdapter;
import com.ousy.formlayoutmanager.R;
import com.ousy.formlayoutmanager.entity.Monster;

import java.util.Arrays;
import java.util.List;

public class MonsterEditAdapter extends BaseHFormAdapter<Monster> {
    private List<Integer> mTitleTypeList = Arrays.asList(TitleType.NAME, TitleType.ATTRIBUTE, TitleType.LV, TitleType.ATK, TitleType.DEF, TitleType.RACE);
    public MonsterEditAdapter(Context context, int columnCount) {
        super(context);
    }

    public void setTitleTypeList(List<Integer> titleTypeList) {
        mTitleTypeList = titleTypeList;
    }

    public List<Integer> getTitleTypeList() {
        return mTitleTypeList;
    }

    @Override
    protected String getRowData(Monster model, int index) {
        String result = "";
        switch (mTitleTypeList.get(index)) {
            case TitleType.NAME:
                result = model.getName();
                break;
            case TitleType.ATTRIBUTE:
                result = model.getAttribute();
                break;
            case TitleType.LV:
                result = model.getLv();
                break;
            case TitleType.ATK:
                result = model.getAtk();
                break;
            case TitleType.DEF:
                result = model.getDef();
                break;
            case TitleType.RACE:
                result = model.getRace();
                break;
            case TitleType.TYPE1:
                result = model.getType1();
                break;
            case TitleType.TYPE2:
                result = model.getType2();
                break;
        }

        return result;
    }

    public MonsterEditAdapter(Context context, List<Monster> list) {
        super(context, list);
    }

    @Override
    protected View createView(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_monster_form_item,
                viewGroup, false);

        return view;
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(View view, int viewType) {
        RecyclerView.ViewHolder viewHolder = new ItemHolder(view);

        return viewHolder;
    }

    @Override
    protected void setData(RecyclerView.ViewHolder viewHolder, int rowIndex, int columnIndex, String content) {
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.tvItem.setText(content);
    }

    @Override
    public int getColumnCount() {
        return mTitleTypeList.size();
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        public TextView tvItem;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }
}
