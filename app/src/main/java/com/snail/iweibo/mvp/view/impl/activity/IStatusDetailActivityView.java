package com.snail.iweibo.mvp.view.impl.activity;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.iweibo.R;
import com.snail.iweibo.mvp.model.Comment;
import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.model.Status.ThumbnailPic;
import com.snail.iweibo.mvp.model.UserBean;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.activity.UserDetailActivity;
import com.snail.iweibo.ui.adapter.CommentListAdapter;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.snail.iweibo.util.ScreenInfo;
import com.snail.iweibo.util.SpanUtil;
import com.snail.iweibo.util.TimeUtils;
import com.snail.iweibo.widget.CompatClickTextView.CompatLinkMovementMethod;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 微博正文View
 * Created by alexwan on 16/4/4.
 */
public class IStatusDetailActivityView implements IBaseView {

    private View mView;
    private Status status;
    private Context context;
    // 微博正文
    @Bind(R.id.user_avatar)
    SimpleDraweeView userAvatar;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.created_at)
    TextView createTime;
    @Bind(R.id.from)
    TextView from;
    @Bind(R.id.content_text)
    TextView content;
    @Bind(R.id.status_pic_grid)
    GridLayout gridLayout;
    @Bind(R.id.tool_bar)
    Toolbar toolbar;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    //    @Bind(R.id.frame_layout)
//    FrameLayout frameLayout;
    @Bind(R.id.tool_bar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.list_view)
    RecyclerView listView;
    @Bind(R.id.retweeted_layout)
    LinearLayout relayLayout;
    @Bind(R.id.relay_content)
    TextView relayContent;
    @Bind(R.id.relay_pic_grid)
    GridLayout relayGridLayout;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    @Bind(R.id.relay_data_relay)
    TextView relayDataRelay;
    @Bind(R.id.relay_data_comment)
    TextView relayDataComment;
    @Bind(R.id.relay_data_like)
    TextView relayDataLike;
    private CommentListAdapter commentAdapter;

    @Override
    public void init(Context context, LayoutInflater inflater, ViewGroup viewGroup) {
        mView = inflater.inflate(R.layout.activity_status_detail, viewGroup);
        this.context = context;
        ButterKnife.bind(this, mView);
    }

    @Override
    public View getView() {
        return mView;
    }

    public void initView(final BasePresenterActivity context) {
        if (toolbar == null) {
            return;
        }
        context.setSupportActionBar(toolbar);
        ActionBar actionBar = context.getSupportActionBar();
        if (actionBar != null) {
            //
            actionBar.setDisplayShowHomeEnabled(true);
            // add titile
            actionBar.setDisplayShowTitleEnabled(true);
            // add back icon
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
        toolbarLayout.setTitleEnabled(false);
    }

    public void updateView(final Status status) {

        this.status = status;
        final UserBean user = status.getUser();
        userAvatar.setImageURI(UriUtil.parseUriOrNull(user.getAvatar_hd()));
        userAvatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetailActivity.start(context, user);
            }
        });
        userName.setText(user.getName());
        createTime.setText(TimeUtils.formatUTCTimes(status.getCreatedAt()));
        Spanned span = Html.fromHtml(String.format(context.getResources().getString(R.string.string_statuses_from),
                status.getSource()));
        from.setText(span);
        content.setText(SpanUtil.buildSpan(context, status.getText()));
        content.setMovementMethod(CompatLinkMovementMethod.getInstance());
        gridLayout.removeAllViews();
        if (status.getPicUrls() != null && !status.getPicUrls().isEmpty()) {
            int size = status.getPicUrls().size();
            updateGridLayout(size, gridLayout, status.getPicUrls());
        }
        // 转发微博内容
        Status relayStatus = status.getRetweetedStatus();
        if (relayStatus != null) {
            Log.i("StatusListAdapter", relayStatus.toString());
            relayLayout.setVisibility(View.VISIBLE);
            String reContent = "@" + relayStatus.getUser().getName() + ":" + relayStatus.getText();
            relayContent.setText(SpanUtil.buildSpan(context, reContent));
            relayContent.setMovementMethod(CompatLinkMovementMethod.getInstance());
            relayDataRelay.setText(String.valueOf(relayStatus.getRepostsCount()));
            relayDataComment.setText(String.valueOf(relayStatus.getCommentsCount()));
            relayDataLike.setText(String.valueOf(relayStatus.getAttitudesCount()));
            relayGridLayout.removeAllViews();
            if (relayStatus.pic_urls != null && !relayStatus.getPicUrls().isEmpty()) {
                int size = relayStatus.getPicUrls().size();
                updateGridLayout(size, relayGridLayout, relayStatus.getPicUrls());
            }
        } else {
            relayLayout.setVisibility(View.GONE);
        }
        // ListView 必须设置LayoutManager和Adapter才能配合CoordinatorLayout使用
        commentAdapter = new CommentListAdapter(context);
        listView.setLayoutManager(new LinearLayoutManager(context));
        listView.setAdapter(commentAdapter);
        // TAB
        Tab tab1 = tabLayout.newTab().setText("转发 " + status.getRepostsCount());
        tabLayout.addTab(tab1);
        Tab tab2 = tabLayout.newTab().setText("评论 " + status.getCommentsCount());
        tabLayout.addTab(tab2);
        tabLayout.addTab(tabLayout.newTab().setText("赞 " + status.getAttitudesCount()));
        tab2.select();
        tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                //
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        listView.setVisibility(View.GONE);
                        break;
                    case 1:
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(commentAdapter);
                        commentAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        listView.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(Tab tab) {
                //
            }

            @Override
            public void onTabReselected(Tab tab) {
                //
            }
        });
    }

    /**
     * 多图
     *
     * @param size       size
     * @param gridLayout gridLayout
     * @param pics       pics
     */
    private void updateGridLayout(int size, GridLayout gridLayout, final List<ThumbnailPic> pics) {
        ScreenInfo info = new ScreenInfo(context);
        MarginLayoutParams params = new MarginLayoutParams(info.getWidth() / 3 - 20, info.getWidth() / 3 - 20);
        int column = (size >= 1 && size <= 3) ? size : ((size == 4) ? 2 : 3);
        int row = (int) Math.ceil(size / 3);
        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < pics.size(); i++) {
            // 图片地址
            SimpleDraweeView imageView = (SimpleDraweeView) inflater.inflate(R.layout.item_status_grid, null);
            // 将缩略图替换为中等大小图
            final String url = pics.get(i).getThumbnailPic().replace("thumbnail", "bmiddle");
            imageView.setImageURI(UriUtil.parseUriOrNull(url));
            imageView.setLayoutParams(params);
            imageView.setPadding(0, 0, 5, 5);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            imageView.setTag(url);
            gridLayout.addView(imageView);
        }
    }

    public void updateComments(List<Comment> comments) {
        commentAdapter.setCommentList(comments);
    }

    public void setProgressBarVisible(boolean isVisible) {
        if (progressBar != null) {
            progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }
}
