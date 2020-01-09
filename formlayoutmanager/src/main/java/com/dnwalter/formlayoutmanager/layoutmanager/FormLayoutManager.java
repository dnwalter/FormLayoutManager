package com.dnwalter.formlayoutmanager.layoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dnwalter.formlayoutmanager.entity.ItemViewSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager.StartShowType.BOTTOM;
import static com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager.StartShowType.RIGHT;

/**
 * @author ousiyuan
 * @date 2019/10/14
 */
public class FormLayoutManager extends RecyclerView.LayoutManager {

    protected int mSumDy, mSumDx;
    // 默认起始显示滚动的dx,dy
    protected int mDefaultStartDx, mDefaultStartDy;
    private int mTotalHeight, mTotalWidth;
    private int mRowCount, mColumnCount;
    private RecyclerView mRecyclerView;
    protected int mItemCount;
    // 根据类型获取宽高的map
    private Map<Integer, ItemViewSize> mItemViewSizeMap = new HashMap<>();
    // 是水平排列还是垂直，默认水平
    private boolean mIsHorV = true;
    // 所有item的rect
    private List<Rect> mItemRects = new ArrayList<>();
    /**
     * 记录Item是否出现过屏幕且还没有回收。true表示出现过屏幕上，并且还没被回收
     */
    private List<Boolean> mHasAttachedItems = new ArrayList<>();
    // 起始显示的地方
    private int mStartShowType;

    public FormLayoutManager(int columnCount){
        mColumnCount = columnCount;
    }

    public FormLayoutManager(boolean isHorV, int count){
        this(isHorV, count, null);
    }

    /**
     * 什么场景需要传入RecyclerView
     * 在滚动过程会刷新的数据的时候，最好设置RecyclerView
     */
    public FormLayoutManager(int columnCount, RecyclerView recyclerView){
        this(true, columnCount, recyclerView);
    }

    public FormLayoutManager(boolean isHorV, int count, RecyclerView recyclerView){
        mIsHorV = isHorV;
        if (isHorV){
            mColumnCount = count;
        }else{
            mRowCount = count;
        }
        mRecyclerView = recyclerView;
    }

    /**
     * recyclerview默认是显示左顶位置
     * 若想默认起始的时候显示在右边或底部，或右底部可以设置这个值
     * @param type
     */
    public void startShow(@StartShowType int type){
        mStartShowType = type;
    }

    // 刷新数据后想到回到起始位置，刷新数据前调用reset
    public void reset(){
        mSumDx = mDefaultStartDx;
        mSumDy = mDefaultStartDy;
        mItemCount = 0;
    }

    private int getColumnCount() {
        if (mIsHorV){
            return mColumnCount;
        }else{
            return (getItemCount() - 1) / mRowCount + 1;
        }
    }

    private int getRowCount() {
        if (mIsHorV){
            return (getItemCount() - 1) / mColumnCount + 1;
        }else{
            return mRowCount;
        }
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (mItemCount != getItemCount()){
            mItemCount = 0;
        }
        if (mItemCount == 0 || mRecyclerView == null){
            mItemCount = getItemCount();
            handleLayoutChildren(recycler);
        }else{
            // 防止数据在更新的时候，用户又在滑动表格，这时会看到卡顿现象
            // 第二种刷新当前界面可视的view，不过要设置RecyclerView，但刷新时间短
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                int position = getPosition(child);
                mRecyclerView.getAdapter().onBindViewHolder(mRecyclerView.getChildViewHolder(child), position);
            }
        }
    }

    private void handleLayoutChildren(RecyclerView.Recycler recycler){
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }

        if (mIsHorV){
            mRowCount = getRowCount();
        }else{
            mColumnCount = getColumnCount();
        }

        mHasAttachedItems.clear();
        mItemRects.clear();

        detachAndScrapAttachedViews(recycler);

        mCurRow = 0;
        mCurColumn = 0;
        for (int i = 0; i < getItemCount(); i++) {
            // item所在的行和列的index
            int row = i / mColumnCount;
            int column = i % mColumnCount;

            View itemView = recycler.getViewForPosition(i);
            Integer itemViewType = getItemViewType(itemView);
            int itemWidth;
            int itemHeight;
            if (mItemViewSizeMap.containsKey(itemViewType)){
                itemWidth = mItemViewSizeMap.get(itemViewType).width;
                itemHeight = mItemViewSizeMap.get(itemViewType).height;
            }else{
                measureChildWithMargins(itemView, 0, 0);
                itemWidth = getDecoratedMeasuredWidth(itemView);
                itemHeight = getDecoratedMeasuredHeight(itemView);
                mItemViewSizeMap.put(itemViewType, new ItemViewSize(itemWidth, itemHeight));
            }

            Rect rect = getViewRect(row, column, itemWidth, itemHeight);
            mItemRects.add(rect);
            mHasAttachedItems.add(false);
        }

        Rect lastRect = mItemRects.get(mItemRects.size() - 1);

        //如果所有子View的高度和没有填满RecyclerView的高度，
        // 则将高度设置为RecyclerView的高度
        mTotalWidth = Math.max(lastRect.right, getHorizontalSpace());
        mTotalHeight = Math.max(lastRect.bottom, getVerticalSpace());

        if ((mStartShowType & RIGHT) > 0 && mDefaultStartDx == 0){
            mDefaultStartDx = mTotalWidth - getHorizontalSpace();
            mSumDx = mDefaultStartDx;
        }

        if ((mStartShowType & BOTTOM) > 0 && mDefaultStartDy == 0){
            mDefaultStartDy = mTotalHeight - getVerticalSpace();
            mSumDy = mDefaultStartDy;
        }

        // 第一个显示在界面的item的下标
        int firstShowRow = 0;
        int firstShowCol = 0;
        for (int i = 0; i < getItemCount(); i++) {
            // item所在的行和列的index
            int row = i / mColumnCount;
            int column = i % mColumnCount;
            if (Rect.intersects(getVisibleArea(), mItemRects.get(i))){
                firstShowRow = row;
                firstShowCol = column;
                break;
            }
        }

        /**
         * 计算可视个数和下标
         * 由于itemView可以有不同的type，那个itemView的宽高可能会不一样
         * 故以最小的宽和最小的高来计算最大可视数
         */
        int minWidth = 0;
        int minHeight = 0;
        for (ItemViewSize viewSize : mItemViewSizeMap.values()){
            if (minWidth == 0){
                minWidth = viewSize.width;
                minHeight = viewSize.height;
            }else{
                if (viewSize.width < minWidth){
                    minWidth = viewSize.width;
                }
                if (viewSize.height < minHeight){
                    minHeight = viewSize.height;
                }
            }
        }

        // 可视的最大行数，列数
        int visibleRowCount = (int) Math.ceil(getVerticalSpace() * 1f / minHeight) + 1;
        int visibleColumnCount = (int) Math.ceil(getHorizontalSpace() * 1f / minWidth) + 1;

        /**
         * 第一次加载数据的时候，下面的代码只加载起始默认滚动到的位置的view
         * 第一种只刷新当前界面可视的view
         */
        here:
        for (int row = firstShowRow; row < visibleRowCount + firstShowRow; row++) {
            for (int column = firstShowCol; column < visibleColumnCount + firstShowCol; column++) {
                int itemPosition = row * mColumnCount + column;

                if (itemPosition >= mItemRects.size()){
                    break here;
                }
                Rect rect = mItemRects.get(itemPosition);
                if (!Rect.intersects(getVisibleArea(), rect)){
                    continue;
                }
                View view = recycler.getViewForPosition(itemPosition);
                addView(view);
                //addView后一定要measure，先measure再layout
                measureChildWithMargins(view, 0, 0);
                layoutDecorated(view, rect.left - mSumDx, rect.top - mSumDy, rect.right - mSumDx, rect.bottom - mSumDy);
            }
        }
    }

    // 记录getViewRect当前行
    private int mCurRow;
    private int mCurColumn;

    // 获取view对应的Rect
    private Rect getViewRect(int row, int column, int itemWidth, int itemHeight) {
        int left = 0;
        int right = itemWidth;
        int top = 0;
        int bottom = itemHeight;

        if (mItemRects.size() > 0){
            Rect lastRect = mItemRects.get(mItemRects.size() - 1);
            if (mCurRow != row){
                mCurRow = row;
                left = 0;
                right = itemWidth;
                top = lastRect.bottom;
                bottom = top + itemHeight;
            }else if (mCurColumn != column){
                mCurColumn = column;
                left = lastRect.right;
                right = left + itemWidth;
                top = lastRect.top;
                bottom = lastRect.bottom;
            }
        }

        Rect rect = new Rect(left, top, right, bottom);

        return rect;
    }

    // RecyclerView可视内容的高度
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    // RecyclerView可视内容的宽度
    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private boolean mIsCanScrollV = true;
    private boolean mIsCanScrollH = true;

    // 设置能否垂直滚动
    public void setCanScrollV(boolean canScrollV) {
        mIsCanScrollV = canScrollV;
    }

    //  设置能否水平滚动
    public void setCanScrollH(boolean canScrollH) {
        mIsCanScrollH = canScrollH;
    }

    @Override
    public boolean canScrollVertically() {
        return mIsCanScrollV;
    }

    @Override
    public boolean canScrollHorizontally() {
        return mIsCanScrollH;
    }

    // 处理垂直滚动
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler,
                                  RecyclerView.State state) {
        // 上滑dy > 0
        if (getChildCount() <= 0) {
            return dy;
        }

        int travel = dy;
        //如果滑动到最顶部
        if (mSumDy + dy < 0) {
            travel = -mSumDy;
        }
        else if (mSumDy + dy > mTotalHeight - getVerticalSpace()) {
            //如果滑动到最底部
            travel = mTotalHeight - getVerticalSpace() - mSumDy;
        }

        mSumDy += travel;

        Rect visibleRect = getVisibleArea();

        //回收越界子View
        List<Integer> recylerPostions = new ArrayList<>();
        List<View> recylerChilds = new ArrayList<>();
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            int position = getPosition(child);
            Rect rect = mItemRects.get(position);

            if (!Rect.intersects(rect, visibleRect)) {
                // 先要记录要回收的view和position，不能在这里回收，放在后面回收
                // 当我们不是手势操作滑动，是调用RecyclerView.scrollBy的方法来滚动大距离是，下面的firstView会变空
                recylerPostions.add(position);
                recylerChilds.add(child);
            }
            else {
                layoutDecoratedWithMargins(child, rect.left - mSumDx, rect.top - mSumDy, rect.right - mSumDx,
                        rect.bottom - mSumDy);
                mHasAttachedItems.set(position, true);
            }
        }

        View lastView = getChildAt(getChildCount() - 1);
        View firstView = getChildAt(0);
        int minPos = getPosition(firstView);
        int maxPos = getPosition(lastView);
        if (travel >= 0) {
            // 上滑（滚到下边）
            for (int i = minPos; i < getItemCount(); i++) {
                insertRowView(i, recycler, false);
            }
        }
        else {
            // 下滑（滚到上边）
            for (int i = maxPos; i >= 0; i--) {
                insertRowView(i, recycler, true);
            }
        }

        //回收越界子View
        for (int i = 0; i < recylerChilds.size(); i++){
            removeAndRecycleView(recylerChilds.get(i), recycler);
            mHasAttachedItems.set(recylerPostions.get(i), false);
        }

        return travel;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 左滑 dx > 0
        if (getChildCount() <= 0) {
            return dx;
        }

        int travel = dx;
        //如果滑动到最左边
        if (mSumDx + dx < 0) {
            travel = -mSumDx;
        }
        else if (mSumDx + dx > mTotalWidth - getHorizontalSpace()) {
            //如果滑动到最右边
            travel = mTotalWidth - getHorizontalSpace() - mSumDx;
        }

        mSumDx += travel;

        Rect visibleRect = getVisibleArea();

        //记录要回收越界子View
        List<Integer> recylerPostions = new ArrayList<>();
        List<View> recylerChilds = new ArrayList<>();
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            int position = getPosition(child);
            Rect rect = mItemRects.get(position);

            if (!Rect.intersects(rect, visibleRect)) {
                recylerPostions.add(position);
                recylerChilds.add(child);
            }
            else {
                layoutDecoratedWithMargins(child, rect.left - mSumDx, rect.top - mSumDy, rect.right - mSumDx,
                        rect.bottom - mSumDy);
                mHasAttachedItems.set(position, true);
            }
        }

        View lastView = getChildAt(getChildCount() - 1);
        View firstView = getChildAt(0);
        int minPos = getPosition(firstView);
        int maxPos = getPosition(lastView);
        if (travel >= 0) {
            // 左滑（滚到右边）
            for (int i = minPos; i < getItemCount(); i++) {
                insertColumnView(i, recycler, false);
            }
        }
        else {
            // 右滑（滚到左边）
            for (int i = maxPos; i >= 0; i--) {
                insertColumnView(i, recycler, true);
            }
        }

        //回收越界子View
        for (int i = 0; i < recylerChilds.size(); i++){
            removeAndRecycleView(recylerChilds.get(i), recycler);
            mHasAttachedItems.set(recylerPostions.get(i), false);
        }

        return travel;
    }

    /**
     * 插入行的view
     * @param pos
     * @param recycler
     * @param isSmoothDown 是否为下滑，true：下滑；false：上滑
     */
    private void insertRowView(int pos, RecyclerView.Recycler recycler, boolean isSmoothDown) {
        Rect visibleRect = getVisibleArea();
        Rect rect = mItemRects.get(pos);
        if (Rect.intersects(visibleRect, rect) && !mHasAttachedItems.get(pos)) {
            View child = recycler.getViewForPosition(pos);
            if (isSmoothDown) {
                addView(child, 0);
            }
            else {
                addView(child);
            }
            measureChildWithMargins(child, 0, 0);
            layoutDecoratedWithMargins(child, rect.left - mSumDx, rect.top - mSumDy, rect.right - mSumDx,
                    rect.bottom - mSumDy);

            mHasAttachedItems.set(pos, true);
        }
    }

    /**
     * 插入列的view
     * @param pos
     * @param recycler
     * @param isSmoothRight 是否为右滑，true：右滑；false：左滑
     */
    private void insertColumnView(int pos, RecyclerView.Recycler recycler, boolean isSmoothRight) {
        Rect visibleRect = getVisibleArea();
        Rect rect = mItemRects.get(pos);
        if (Rect.intersects(visibleRect, rect) && !mHasAttachedItems.get(pos)) {
            View child = recycler.getViewForPosition(pos);
            if (isSmoothRight){
                addView(child, 0);
            }else{
                addView(child);
            }

            measureChildWithMargins(child, 0, 0);
            layoutDecoratedWithMargins(child, rect.left - mSumDx, rect.top - mSumDy, rect.right - mSumDx,
                    rect.bottom - mSumDy);

            mHasAttachedItems.set(pos, true);
        }
    }

    /**
     * 获取可见的区域Rect
     *
     * @return
     */
    private Rect getVisibleArea() {
        Rect result = new Rect(getPaddingLeft() + mSumDx, getPaddingTop() + mSumDy, getWidth() + getPaddingRight() + mSumDx, getVerticalSpace() + mSumDy);
        return result;
    }

    public @interface StartShowType {
        //  右边
        int RIGHT = 1 << 1;
        // 底部
        int BOTTOM = 1 << 2;
    }
}
