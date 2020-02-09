package com.dnwalter.formlayoutmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dnwalter.formlayoutmanager.entity.BaseFormModel;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFormAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public final static int TYPE_DEFAULT = 0;
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
    public int getItemViewType(int position) {
        int rowIndex = position / getColumnCount();
        int columnIndex = position % getColumnCount();
        int rowType = getRowItemViewType(rowIndex);
        int columnType = getColumnItemViewType(columnIndex);
        if (rowType != TYPE_DEFAULT){
            return rowType;
        }
        if (columnType != TYPE_DEFAULT){
            return columnType;
        }

        return TYPE_DEFAULT;
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

    /**
     * 展示数据
     * @param viewHolder
     * @param rowIndex 该item行下标
     * @param columnIndex 该item列下标
     * @param content 该item字符串内容
     * @param colors 可用于传递字体颜色，背景颜色等
     */
    protected void setData(RecyclerView.ViewHolder viewHolder, int rowIndex, int columnIndex, String content, Integer... colors) {

    }

    /**
     * 根据不同行获取itemViewType（注：与getColumnItemViewType只能重写其中一个）
     * @param row
     * @return
     */
    protected int getRowItemViewType(int row){
        return TYPE_DEFAULT;
    }

    /**
     *  根据不同列获取itemViewType （注：与getRowItemViewType只能重写其中一个）
     * @param column
     * @return
     */
    protected int getColumnItemViewType(int column){
        return TYPE_DEFAULT;
    }

    // 获取一行或一列的字体颜色
    protected List<Integer> getTextColors(BaseFormModel model) {
        return model.getTextColors();
    }

    // 获取一行或一列的背景颜色
    protected List<Integer> getBgColors(BaseFormModel model) {
        return model.getBgColors();
    }
}
