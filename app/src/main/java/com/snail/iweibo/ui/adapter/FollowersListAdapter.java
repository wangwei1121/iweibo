package com.snail.iweibo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.snail.iweibo.R;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.api.WeiBoApiService;
import com.snail.iweibo.mvp.model.Follower;
import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.oauth.AccessTokenKeeper;
import com.snail.iweibo.oauth.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by luckyliang on 16/4/16.
 */
public class FollowersListAdapter extends RecyclerView.Adapter<FollowersListAdapter.ViewHolder> {

    private List<Follower> followers;
    private Context context;
    private View.OnClickListener onClickListener;
    private OnItemClickListener itemClick;
    private int resource;
    Oauth2AccessToken token;

    public FollowersListAdapter(Context context, List<Follower> followers, View.OnClickListener onClickListener,
                                OnItemClickListener itemClick) {
        this.context = context;
        this.followers = followers;
        this.onClickListener = onClickListener;
        this.itemClick = itemClick;
        this.token = AccessTokenKeeper.readAccessToken(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_followers_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Follower follower = followers.get(position);
        holder.userAvatar.setImageURI(UriUtil.parseUriOrNull(follower.getProfile_image_url()));
        holder.userName.setText(follower.getName());
        if (follower.isFollow_me())
            holder.imageViewConcern.setImageResource(R.drawable.eyes_on);
        if (follower.getOnline_status() != 1) {
            holder.is_online.setTextColor(context.getResources().getColor(R.color.main_gray));
            holder.is_online.setText("离线");
        }
        //根据用户最新微博id获取微博
        ApiServiceHelper.getApiService(Constants.WEIBO_BASE_URL, WeiBoApiService.class)
                .getStatus(token.getToken(), follower.getStatus_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Status>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Status status) {
                        holder.lastTweeter.setText(status.getText());
                    }
                });

    }

    public void addAll(List<Follower> followers) {
        followers.addAll(0, followers);
        this.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return followers == null ? 0 : followers.size();
    }

    public Follower getFollow(int position) {
        return followers.get(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.user_avatar)
        SimpleDraweeView userAvatar;
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.is_online)
        TextView is_online;
        @Bind(R.id.description)
        TextView description;
        @Bind(R.id.lastTweeter)
        TextView lastTweeter;
        @Bind(R.id.eyes_on)
        ImageView imageViewConcern;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(getAdapterPosition());
            }
        }

        private OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
