package com.ousy.formlayoutmanager.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dnwalter.formlayoutmanager.adapter.BaseFormAdapter;
import com.ousy.formlayoutmanager.R;

import java.util.List;

/**
 * @author ousiyuan
 * @date 2019/10/11
 */
public class TestAdapter extends BaseFormAdapter<String> {

    public TestAdapter(Context context) {
        super(context);
    }

    public TestAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public int getRowCount() {
        return 10;
    }

    @Override
    public int getColumnCount() {
        return 10;
    }

    @Override
    public View createView(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_monster_form_item, viewGroup, false);

        return view;
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(View view, int viewType) {
        RecyclerView.ViewHolder viewHolder = new ItemHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.tvItem.setText(mList.get(position));
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public TextView tvItem;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("ousyxx","aaa");
                }
            });
        }
    }
}
