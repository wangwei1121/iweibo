package com.snail.iweibo.ui.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.iweibo.R;
import com.snail.iweibo.mvp.model.Comment;
import com.snail.iweibo.util.SpanUtil;
import com.snail.iweibo.util.TimeUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * CommonListAdapter
 * Created by alexwan on 16/4/4.
 */
public class CommentListAdapter extends ArrayAdapter<Comment> {
    private ArrayList<Comment> comments = new ArrayList<>();
    private int resource;
    public CommentListAdapter(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return 0;
    }

    public void setCommentList(ArrayList<Comment> comments){
        this.comments = comments;
        notifyDataSetChanged();
    }
    @Override
    public Comment getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource , parent);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        if(comments == null || comments.isEmpty()){
            return convertView;
        }
        Comment comment = comments.get(position);

        holder.userAvatar.setImageURI(UriUtil.parseUriOrNull(comment.getUser().getAvatar_hd()));
        holder.userName.setText(comment.getUser().getName());
        holder.createTime.setText(TimeUtils.formatUTCTime(comment.getCreatedAt() , TimeUtils.MINUTE_SECOND));
        holder.content.setText(SpanUtil.buildSpan(getContext() , comment.getText()));
        holder.likeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return convertView;
    }
    public static class ViewHolder{
        @Bind(R.id.user_avatar)
        SimpleDraweeView userAvatar;
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.created_at)
        TextView createTime;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.action_like)
        ImageView likeBtn;
        public ViewHolder(View itemView){
            ButterKnife.bind(this , itemView);
        }
    }
}
