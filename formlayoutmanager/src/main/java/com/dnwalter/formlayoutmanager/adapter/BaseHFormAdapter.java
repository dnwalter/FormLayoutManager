package com.dnwalter.formlayoutmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class BaseHFormAdapter<T> extends BaseFormAdapter<T>{

    public BaseHFormAdapter(Context context) {
        super(context);
    }

    public BaseHFormAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int getRowCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int rowIndex = position / getColumnCount();
        int columnIndex = position % getColumnCount();
        String[] rowDatas = getRowDatas(mList.get(rowIndex));
        setData(holder, rowIndex, columnIndex, rowDatas[columnIndex]);
    }

    // 获取一行的数据
    protected abstract String[] getRowDatas(T model);
}
