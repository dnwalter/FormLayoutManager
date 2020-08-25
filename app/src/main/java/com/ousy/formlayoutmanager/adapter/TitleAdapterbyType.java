package com.ousy.formlayoutmanager.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ousy.formlayoutmanager.R;

import java.util.List;

import static com.ousy.formlayoutmanager.adapter.MonsterHAdapterByType.TYPE_ATTRIBUTE;
import static com.ousy.formlayoutmanager.adapter.MonsterHAdapterByType.TYPE_MONSTER_TYPE;

/**
 * @author ousiyuan
 * @date 2019/12/2
 */
public class TitleAdapterbyType extends RecyclerView.Adapter<TitleAdapterbyType.ItemHolder>{
    private List<String> mList;
    public TitleAdapterbyType(List<String> list){
        mList = list;
    }
    @NonNull
    @Override
    public TitleAdapterbyType.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_title, viewGroup, false);
        if (viewType == TYPE_ATTRIBUTE) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_title_attribute, viewGroup, false);
        }
        if (viewType == TYPE_MONSTER_TYPE) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_title_type, viewGroup, false);
        }
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TitleAdapterbyType.ItemHolder viewHolder, int position) {
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.tvItem.setText(mList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 1){
            return TYPE_ATTRIBUTE;
        }
        if (position == 6 || position == 7) {
            return TYPE_MONSTER_TYPE;
        }
        return super.getItemViewType(position);
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
