package com.snail.iweibo.ui.adapter;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.iweibo.R;
import com.snail.iweibo.mvp.model.StatusesBean;
import com.snail.iweibo.mvp.model.UserBean;
import com.snail.iweibo.ui.adapter.CardViewAdapter.ViewHolder;
import com.snail.iweibo.util.TimeUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * CardViewAdapter
 * Created by alexwan on 16/1/30.
 */
public class CardViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<StatusesBean> statuse;
    private Context context;
    public CardViewAdapter(Context context , List<StatusesBean> statuse) {
        this.context = context;
        this.statuse = statuse;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_main_card , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StatusesBean bean = statuse.get(position);
        UserBean user = bean.getUser();
        if(!TextUtils.isEmpty(user.getProfile_image_url())){
            Uri uri = UriUtil.parseUriOrNull(user.getAvatar_large());
            if(uri != null){
                holder.userAvatar.setImageURI(uri);
            }
        }
        Spanned span = Html.fromHtml(String.format(context.getResources().getString( R.string.string_statuses_from) ,
            bean .getSource()));
        holder.from.setText(span);
        holder.userName.setText(user.getScreen_name());
        holder.createTime.setText(TimeUtils.formatUTCTime(bean.getCreatedAt()));
        holder.contentText.setText(bean.getText());

    }

    public void addAll(List<StatusesBean> list){
        statuse.addAll(0 , list);
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return statuse == null ? 0 : statuse.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.user_avatar)
        SimpleDraweeView userAvatar;
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.created_at)
        TextView createTime;
        @Bind(R.id.from)
        TextView from;
        @Bind(R.id.content_pic)
        SimpleDraweeView contentPic;
        @Bind(R.id.content_text)
        TextView contentText;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }

}
