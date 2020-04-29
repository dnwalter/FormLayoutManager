package com.dnwalter.formlayoutmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.dnwalter.formlayoutmanager.entity.BaseFormModel;

import java.util.ArrayList;
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
        List<Integer> colorList = new ArrayList<>();
        int rowIndex = position / getColumnCount();
        int columnIndex = position % getColumnCount();
        T model = mList.get(rowIndex);
        String rowData = getRowData(model, columnIndex);
        if (model instanceof BaseFormModel){
            List<Integer> textColors = getTextColors((BaseFormModel) model);
            List<Integer> bgColors = getBgColors((BaseFormModel) model);
            if (textColors.size() > 0){
                colorList.add(textColors.get(columnIndex));
            }
            if (bgColors.size() > 0){
                colorList.add(bgColors.get(columnIndex));
            }

            Integer[] colors = new Integer[colorList.size()];
            colorList.toArray(colors);
            setData(holder, rowIndex, columnIndex, rowData, colors);
        }

        setData(holder, rowIndex, columnIndex, rowData);
    }

    /**
     * 获取一行的数据
     * @param model
     * @param index 列的下标
     * @return
     */
    protected abstract String getRowData(T model, int index);
}
