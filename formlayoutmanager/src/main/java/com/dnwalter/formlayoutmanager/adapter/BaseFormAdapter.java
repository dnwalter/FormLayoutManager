package com.dnwalter.formlayoutmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFormAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public Context mContext;
    // 数据源
    public List<T> mList;

    public BaseFormAdapter(Context context){
        this(context, new ArrayList<T>());
    }

    public BaseFormAdapter(Context context, List<T> list){
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemCount() {
        return getRowCount() * getColumnCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = createView(parent, viewType);
        RecyclerView.ViewHolder viewHolder = createViewHolder(view, viewType);

        return viewHolder;
    }

    // 刷新数据
    public void refreshData(List<T> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 表格的行数
     * @return
     */
    public abstract int getRowCount();

    /**
     * 表格的列数
     * @return
     */
    public abstract int getColumnCount();

    /**
     * 加载item布局
     *
     * @param viewGroup
     * @param viewType
     * @return
     */

    protected abstract View createView(ViewGroup viewGroup, int viewType);

    /**
     * 创建viewHolder
     *
     * @param view
     * @return
     */
    protected abstract RecyclerView.ViewHolder createViewHolder(View view, int viewType);

    /**
     * 展示数据
     * @param viewHolder
     * @param rowIndex 该item行下标
     * @param columnIndex 该item列下标
     * @param content 该item字符串内容
     */
    protected void setData(RecyclerView.ViewHolder viewHolder, int rowIndex, int columnIndex, String content) {

    }
}
