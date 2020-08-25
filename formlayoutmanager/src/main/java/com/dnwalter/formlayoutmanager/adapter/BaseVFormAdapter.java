package com.dnwalter.formlayoutmanager.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import com.dnwalter.formlayoutmanager.entity.BaseFormModel;

import java.util.ArrayList;
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
        List<Integer> colorList = new ArrayList<>();
        int rowIndex = position / getColumnCount();
        int columnIndex = position % getColumnCount();
        T model = mList.get(columnIndex);
        String columnData = getColumnData(model, rowIndex);
        if (model instanceof BaseFormModel){
            List<Integer> textColors = getTextColors((BaseFormModel) model);
            List<Integer> bgColors = getBgColors((BaseFormModel) model);
            if (textColors.size() > 0){
                colorList.add(textColors.get(rowIndex));
            }
            if (bgColors.size() > 0){
                colorList.add(bgColors.get(rowIndex));
            }

            Integer[] colors = new Integer[colorList.size()];
            colorList.toArray(colors);
            setData(holder, rowIndex, columnIndex, columnData, colors);
        }

        setData(holder, rowIndex, columnIndex, columnData);
    }

    /**
     * 获取一列的数据
     * @param model
     * @param index 行的下标
     * @return
     */
    protected abstract String getColumnData(T model, int index);
}
