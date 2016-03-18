package com.snail.iweibo.ui.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.snail.iweibo.R;

/**
 * Created by wang.weib on 2016/3/4.
 */
public class RefreshListView extends ListView implements OnScrollListener {
    private View header;// 顶部布局文件；
    private int headerHeight;// 顶部布局文件的高度；
    private int firstVisibleItem;// 当前第一个可见的item的位置；
    private int scrollState = 0;// listview 当前滚动状态；
    private boolean isRemark;// 标记，当前是在listview最顶端摁下的；
    private int startY;// 摁下时的Y值；
    private boolean isRefresh = false;

    private int state;// 当前的状态；
    private final int NONE = 0;// 正常状态；
    private final int PULL = 1;// 提示下拉状态；
    private final int RELESE = 2;// 提示释放状态；
    private final int REFLASHING = 3;// 刷新状态；
    private IRefreshListener iRefreshListener;//刷新数据的接口

    private View footer;// 底部布局；
    private int totalItemCount;// 总数量；
    private int lastVisibleItem;// 最后一个可见的item；
    private boolean isLoading = false;// 正在加载；
    private ILoadListener iLoadListener;
    private int footerHeight;// 底部布局文件的高度；

    private int freshHeight = 400;

    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;

    private int mTouchSlop;

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);
        this.initView(context);
    }

    private  void initView(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);

        this.header = inflater.inflate(R.layout.header_layout, null);
        this.header.findViewById(R.id.refresh_layout).setVisibility(View.GONE);
        this.addHeaderView(header);

        this.footer = inflater.inflate(R.layout.footer_layout, null);
        this.footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
        this.addFooterView(footer);
    }

//    /**
//     * 通知父布局，占用的宽，高；
//     *
//     * @param view
//     */
//    private void measureView(View view) {
//        ViewGroup.LayoutParams p = view.getLayoutParams();
//        if (p == null) {
//            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//        }
//        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
//        int height;
//        int tempHeight = p.height;
//        if (tempHeight > 0) {
//            height = MeasureSpec.makeMeasureSpec(tempHeight,
//                    MeasureSpec.EXACTLY);
//        } else {
//            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
//        }
//        view.measure(width, height);
//    }

//    /**
//     * 设置header 布局 上边距；
//     *
//     * @param topPadding
//     */
//    private void topPadding(int topPadding) {
//        header.setPadding(header.getPaddingLeft(), topPadding,
//                header.getPaddingRight(), header.getPaddingBottom());
//        header.invalidate();
//    }
//
//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                mLastY = (int) event.getRawY();
                int upSpace = mLastY - startY;
                Log.e("com.snail.iweibo","event.getRawY()-UP->" + mLastY);
                Log.e("com.snail.iweibo","upSpace-->" + upSpace);
                if(upSpace > freshHeight && this.getFirstVisiblePosition() == 0 && !this.isRefresh){
                    Log.e("com.snail.iweibo","refresh");
                    this.isRefresh = true;
                    this.header.findViewById(R.id.refresh_layout).setVisibility(View.VISIBLE);
                    this.iRefreshListener.onReflash();
                }else if(upSpace < freshHeight*-1 && this.getLastVisiblePosition() == (this.getCount() - 1) && !this.isLoading){
                    Log.e("com.snail.iweibo","load");
                    this.isLoading = true;
                    this.footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                    this.iLoadListener.onLoad();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        /**
         * firstVisibleItem 表示在当前屏幕显示的第一个listItem在整个listView里面的位置（下标从0开始）
         * visibleItemCount表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数
         * totalItemCount表示ListView的ListItem总数
         * listView.getLastVisiblePosition()表示在现时屏幕最后一个ListItem
         * (最后ListItem要完全显示出来才算)在整个ListView的位置（下标从0开始）
         */

    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        /**
         *scrollState有三种状态，分别是SCROLL_STATE_IDLE、SCROLL_STATE_TOUCH_SCROLL、SCROLL_STATE_FLING
         *SCROLL_STATE_IDLE是当屏幕停止滚动时
         *SCROLL_STATE_TOUCH_SCROLL是当用户在以触屏方式滚动屏幕并且手指仍然还在屏幕上时（The user is scrolling using touch, and their finger is still on the screen）
         *SCROLL_STATE_FLING是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时（The user had previously been scrolling using touch and had performed a fling）
         */
        switch (scrollState) {
            // 当不滚动时
            case OnScrollListener.SCROLL_STATE_IDLE:
                // 判断滚动到底部
                if (this.getLastVisiblePosition() == (this.getCount() - 1)) {
                    this.scrollState = 1;
                }
                // 判断滚动到顶部
                if(this.getFirstVisiblePosition() == 0){
                    this.scrollState = 0;
                }
                break;
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (firstVisibleItem == 0) {
//                    isRemark = true;
//                    startY = (int) event.getRawY();
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (!isRemark) {
//                    break;
//                }
//                mLastY = (int) event.getRawY();
//                int space = mLastY - startY;
//                int topPadding = space - headerHeight;
//                switch (state) {
//                    case NONE:
//                        if (space > 0) {
//                            state = PULL;
//                            reflashViewByState();
//                        }
//                        break;
//                    case PULL:
//                        topPadding(topPadding);
//                        if (space > headerHeight + 30 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
//                            state = RELESE;
//                            reflashViewByState();
//                        }
//                        break;
//                    case RELESE:
//                        topPadding(topPadding);
//                        if (space < headerHeight + 30) {
//                            state = PULL;
//                            reflashViewByState();
//                        } else if (space <= 0) {
//                            state = NONE;
//                            isRemark = false;
//                            reflashViewByState();
//                        }
//                        break;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                if (state == RELESE) {
//                    state = REFLASHING;
//                    // 加载最新数据；
//                    reflashViewByState();
//                    iRefreshListener.onReflash();
//                } else if (state == PULL) {
//                    state = NONE;
//                    isRemark = false;
//                    reflashViewByState();
//                }
//                Log.e("com.snail.iweibo", "startY-->" + startY);
//                Log.e("com.snail.iweibo", "mLastY-->" + mLastY);
//                Log.e("com.snail.iweibo", "space-->" + (mLastY - startY));
//                break;
//        }
//        return super.onTouchEvent(event);
//    }


//    /**
//     * 根据当前状态，改变界面显示；
//     */
//    private void reflashViewByState() {
//        TextView tip = (TextView) header.findViewById(R.id.tip);
//        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
//        ProgressBar progress = (ProgressBar) header.findViewById(R.id.list_view_header_progress);
//        RotateAnimation anim = new RotateAnimation(0, 180,
//                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
//                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//        anim.setDuration(500);
//        anim.setFillAfter(true);
//        RotateAnimation anim1 = new RotateAnimation(180, 0,
//                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
//                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//        anim1.setDuration(500);
//        anim1.setFillAfter(true);
//        switch (state) {
//            case NONE:
//                arrow.clearAnimation();
//                topPadding(-headerHeight);
//                break;
//            case PULL:
//                arrow.setVisibility(View.VISIBLE);
//                progress.setVisibility(View.GONE);
//                tip.setText("下拉可以刷新！");
//                arrow.clearAnimation();
//                arrow.setAnimation(anim1);
//                break;
//            case RELESE:
//                arrow.setVisibility(View.VISIBLE);
//                progress.setVisibility(View.GONE);
//                tip.setText("松开可以刷新！");
//                arrow.clearAnimation();
//                arrow.setAnimation(anim);
//                break;
//            case REFLASHING:
//                topPadding(50);
//                arrow.setVisibility(View.GONE);
//                progress.setVisibility(View.VISIBLE);
//                tip.setText("正在刷新...");
//                arrow.clearAnimation();
//                break;
//        }
//    }

    /**
     * 获取完数据；
     */
    public void reflashComplete() {
        this.isRefresh = false;
        header.findViewById(R.id.refresh_layout).setVisibility(View.GONE);
    }

    public void setIRefreshListener(IRefreshListener iRefreshListener){
        this.iRefreshListener = iRefreshListener;
    }
    /**
     * 刷新数据接口
     * @author Administrator
     */
    public interface IRefreshListener{
        void onReflash();
    }


    /**
     * 加载完毕
     */
    public void loadComplete(){
        isLoading = false;
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
    }

    public void setILoadListener(ILoadListener iLoadListener){
        this.iLoadListener = iLoadListener;
    }
    //加载更多数据的回调接口
    public interface ILoadListener{
        void onLoad();
    }
}
