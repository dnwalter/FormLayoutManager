package com.ousy.formlayoutmanager.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dnwalter.formlayoutmanager.adapter.BaseHFormAdapter;
import com.ousy.formlayoutmanager.R;
import com.ousy.formlayoutmanager.entity.Monster;

import java.util.List;

/**
 * 不同类型获取不同布局
 */
public class MonsterHAdapterByType extends BaseHFormAdapter<Monster> {
    public final static int TYPE_ATTRIBUTE = 1;
    public final static int TYPE_MONSTER_TYPE = 2;

    public MonsterHAdapterByType(Context context) {
        super(context);
    }

    @Override
    protected String getRowData(Monster model, int index) {
        String result = "";
        switch (index) {
            case 0:
                result = model.getName();
                break;
            case 1:
                result = model.getAttribute();
                break;
            case 2:
                result = model.getLv();
                break;
            case 3:
                result = model.getAtk();
                break;
            case 4:
                result = model.getDef();
                break;
            case 5:
                result = model.getRace();
                break;
            case 6:
                result = model.getType1();
                break;
            case 7:
                result = model.getType2();
                break;
        }

        return result;
    }

    public MonsterHAdapterByType(Context context, List<Monster> list) {
        super(context, list);
    }

    @Override
    protected int getColumnItemViewType(int column) {
        if (column == 1) {
            return TYPE_ATTRIBUTE;
        }
        if (column == 6 || column == 7) {
            return TYPE_MONSTER_TYPE;
        }
        return super.getColumnItemViewType(column);
    }

    @Override
    protected View createView(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_monster_form_item, viewGroup, false);

        if (viewType == TYPE_ATTRIBUTE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_monster_form_item_attribute, viewGroup, false);
        }
        if (viewType == TYPE_MONSTER_TYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_monster_form_item_type, viewGroup, false);
        }

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
        return 8;
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        public TextView tvItem;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }
}
