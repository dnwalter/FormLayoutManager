package com.ousy.formlayoutmanager.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ousy.formlayoutmanager.R;

import java.util.List;

/**
 * @author ousiyuan
 * @date 2019/12/2
 */
public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ItemHolder>{
    private List<String> mList;
    public TitleAdapter(List<String> list){
        mList = list;
    }
    @NonNull
    @Override
    public TitleAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_title, viewGroup, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TitleAdapter.ItemHolder viewHolder, int position) {
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.tvItem.setText(mList.get(position));
    }

    // 刷新数据
    public void refreshData(List<String> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public TextView tvItem;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }
}
