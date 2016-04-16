package com.snail.iweibo.ui.adapter;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.iweibo.R;
import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.model.Status.ThumbnailPic;
import com.snail.iweibo.mvp.model.UserBean;
import com.snail.iweibo.ui.adapter.StatusListAdapter.ViewHolder;
import com.snail.iweibo.util.ScreenInfo;
import com.snail.iweibo.util.SharePreferencesUtil;
import com.snail.iweibo.util.SpanUtil;
import com.snail.iweibo.util.TimeUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * StatusListAdapter
 * Created by alexwan on 16/1/30.
 */
public class StatusListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Status> statuses;
    private Context context;
    private OnClickListener onClickListener;
    private OnItemClickListener itemClick;
    public StatusListAdapter(Context context, List<Status> statuses , OnClickListener onClickListener ,
                             OnItemClickListener itemClick) {
        this.context = context;
        this.statuses = statuses;
        this.onClickListener = onClickListener;
        this.itemClick = itemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_main_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Status bean = statuses.get(position);
        UserBean user = bean.getUser();
        boolean isDarkTheme = SharePreferencesUtil.isDarkTheme(context);
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(isDarkTheme ? R.color.color_primary_dark_inverse  : R.color.main_white));
        // 用户头像
        if (!TextUtils.isEmpty(user.getProfile_image_url())) {
            Uri uri = UriUtil.parseUriOrNull(user.getAvatar_large());
            if (uri != null) {
                holder.userAvatar.setImageURI(uri);
            }
        }
        // 名称
        holder.userName.setText(user.getScreen_name());
        holder.userName.setTextColor(context.getResources().getColor(isDarkTheme ? R.color.main_gray  : R.color
            .main_black));
        // 时间
        holder.createTime.setText(TimeUtils.formatUTCTimes(bean.getCreatedAt()));
        // 来自
        Spanned span = Html.fromHtml(String.format(context.getResources().getString(R.string.string_statuses_from),
            bean.getSource()));
        holder.from.setText(span);
        // 微博内容
        holder.contentText.setText(SpanUtil.buildSpan(context , bean.getText()));
        holder.contentText.setTextColor(context.getResources().getColor(isDarkTheme ? R.color.main_gray  : R.color
            .main_black));
        holder.contentText.setMovementMethod(LinkMovementMethod.getInstance());
        // 被转发的微博字段
        Status status = bean.getRetweetedStatus();
        if (status != null) {
            Log.i("StatusListAdapter", status.toString());
            holder.relayLayout.setVisibility(View.VISIBLE);
            holder.relayLayout.setBackgroundColor(context.getResources().getColor(isDarkTheme ? R.color.color_primary_inverse : R
                    .color.main_light_gray));
            String reContent = "@" + status.getUser().getName() + ":" + status.getText();
            holder.relayContent.setText(SpanUtil.buildSpan(context , reContent));
            holder.relayContent.setTextColor(context.getResources().getColor(isDarkTheme ? R.color.main_gray  : R.color
                .main_black));
            holder.relayContent.setMovementMethod(LinkMovementMethod.getInstance());
        }else{
            holder.relayLayout.setVisibility(View.GONE);
        }
        // 组合图片
        holder.statusPicGrid.removeAllViews();
        if (bean.getPicUrls() != null && !bean.getPicUrls().isEmpty()) {
            int size = bean.getPicUrls().size();
            updateGridLayout(size, holder.statusPicGrid, bean.getPicUrls());
        }
        holder.favorBtn.setOnClickListener(onClickListener);
        holder.relayBtn.setOnClickListener(onClickListener);
        holder.commentBtn.setOnClickListener(onClickListener);
        holder.likeBtn.setOnClickListener(onClickListener);
        // 转发数
        if( bean.getRepostsCount() != 0){
            holder.relayTxt.setText(String.valueOf(bean.getRepostsCount()));
        }
        // 评论数
        if(bean.getCommentsCount() != 0){
            holder.commentTxt.setText(String.valueOf(bean.getCommentsCount()));
        }
        // 赞
        if(bean.getAttitudesCount() != 0){
            holder.likeTxt.setText(String.valueOf(bean.getAttitudesCount()));
        }
        holder.divider.setBackgroundResource(isDarkTheme ? R.color.main_dark_gray : R.color.main_light_gray);
        holder.setOnItemClickListener(itemClick);
    }

    /**
     * updateGridLayout
     *
     * @param size       size
     * @param gridLayout gridLayout
     * @param pics       pics
     */
    private void updateGridLayout(int size, GridLayout gridLayout, final List<ThumbnailPic> pics) {
        ScreenInfo screenInfo = new ScreenInfo(context);
        MarginLayoutParams params = new MarginLayoutParams(screenInfo.getWidth() / 3 - 20 , screenInfo.getWidth() / 3
            - 20);
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
            imageView.setOnClickListener(onClickListener);
            imageView.setTag(url);
            gridLayout.addView(imageView);
        }
    }

    /**
     * addAll
     *
     * @param list list
     */
    public void addAll(List<Status> list) {
        statuses.addAll(0, list);
        this.notifyDataSetChanged();
    }

    public Status getStatus(int position){
        return statuses.get(position);
    }
    @Override
    public int getItemCount() {
        return statuses == null ? 0 : statuses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
        @Bind(R.id.user_avatar)
        SimpleDraweeView userAvatar;
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.created_at)
        TextView createTime;
        @Bind(R.id.from)
        TextView from;
        @Bind(R.id.content_text)
        TextView contentText;
        @Bind(R.id.retweeted_layout)
        FrameLayout relayLayout;
        @Bind(R.id.relay_content)
        TextView relayContent;
        @Bind(R.id.status_pic_grid)
        GridLayout statusPicGrid;
        // 收藏
        @Bind(R.id.action_favorite_layout)
        LinearLayout favorBtn;
        @Bind(R.id.action_favorite)
        TextView favoriteTxt;
        @Bind(R.id.action_favorite_icon)
        ImageView favorIcon;
        // 转发
        @Bind(R.id.action_relay)
        TextView relayTxt;
        @Bind(R.id.action_relay_layout)
        LinearLayout relayBtn;
        // 评论
        @Bind(R.id.action_comment)
        TextView commentTxt;
        @Bind(R.id.action_comment_layout)
        LinearLayout commentBtn;
        // 点赞、取消点赞
        @Bind(R.id.action_like)
        TextView likeTxt;
        @Bind(R.id.action_like_layout)
        LinearLayout likeBtn;
        @Bind(R.id.action_like_icon)
        ImageView likeIcon;
        @Bind(R.id.card_view)
        CardView cardView;
        @Bind(R.id.action_divider)
        View divider;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(listener != null){
                listener.onItemClick(v);
            }
        }
        private OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View v);
    }
}
