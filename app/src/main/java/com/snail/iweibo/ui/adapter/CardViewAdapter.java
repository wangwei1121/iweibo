package com.snail.iweibo.ui.adapter;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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

import java.util.List;

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
            Log.i("CardViewAdapter" , user.getProfile_image_url());
            Uri uri = UriUtil.parseUriOrNull(user.getAvatar_large());
            if(uri != null){
                holder.userAvatar.setImageURI(uri);
            }
        }
        holder.userName.setText(user.getScreen_name());
        holder.createTime.setText(bean.getCreated_at());
//        holder.textView.setText(statuse.get(position));
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
//        TextView textView;
//        @Bind(R.id.user_avatar)
        SimpleDraweeView userAvatar;
//        @Bind(R.id.user_name)
        TextView userName;
//        @Bind(R.id.created_at)
        TextView createTime;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
            userAvatar = (SimpleDraweeView) itemView.findViewById(R.id.user_avatar);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            createTime = (TextView) itemView.findViewById(R.id.created_at);
        }
    }

}
