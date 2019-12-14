package com.dnwalter.formlayoutmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class BaseVFormAdapter<T> extends BaseFormAdapter<T>{

    public BaseVFormAdapter(Context context) {
        super(context);
    }

    public BaseVFormAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int getColumnCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int rowIndex = position / getColumnCount();
        int columnIndex = position % getColumnCount();
        String[] columnDatas = getColumnDatas(mList.get(columnIndex));
        setData(holder, rowIndex, columnIndex, columnDatas[rowIndex]);
    }

    // 获取一列的数据
    protected abstract String[] getColumnDatas(T model);
}
