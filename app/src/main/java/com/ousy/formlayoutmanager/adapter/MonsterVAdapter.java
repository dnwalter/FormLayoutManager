package com.ousy.formlayoutmanager.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dnwalter.formlayoutmanager.adapter.BaseVFormAdapter;
import com.ousy.formlayoutmanager.R;
import com.ousy.formlayoutmanager.entity.Monster;

import java.util.List;

public class MonsterVAdapter extends BaseVFormAdapter<Monster> {

    public MonsterVAdapter(Context context) {
        super(context);
    }

    public MonsterVAdapter(Context context, List<Monster> list) {
        super(context, list);
    }

    @Override
    public int getRowCount() {
        return 8;
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
    protected void setData(RecyclerView.ViewHolder viewHolder, int rowIndex, int columnIndex, String content, Integer... colors) {
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.tvItem.setText(content);
        holder.tvItem.setTextColor(colors[0]);
    }

    @Override
    protected String[] getColumnDatas(Monster model) {
        return new String[]{model.getName(), model.getAttribute(), model.getLv(), model.getAtk(),
                model.getDef(), model.getRace(), model.getType1(), model.getType2()};
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        public TextView tvItem;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }
}
