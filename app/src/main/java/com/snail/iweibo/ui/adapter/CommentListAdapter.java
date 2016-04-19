package com.snail.iweibo.ui.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.iweibo.R;
import com.snail.iweibo.mvp.model.Comment;
import com.snail.iweibo.util.LogUtils;
import com.snail.iweibo.util.SpanUtil;
import com.snail.iweibo.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * CommonListAdapter
 * Created by alexwan on 16/4/4.
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {
    private List<Comment> comments = new ArrayList<>();
    private Context context;
    public CommentListAdapter(Context context) {
        this.context = context;
    }

    public void setCommentList(List<Comment> comments){
        LogUtils.info("setCommentList : comment size -> " + comments.size());
        this.comments.addAll(comments);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate( R.layout.item_comment_list_layout  , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        LogUtils.info("Comment : content -> " + comment.getText() + " user -> " + comment.getUser().getName());
        holder.userAvatar.setImageURI(UriUtil.parseUriOrNull(comment.getUser().getAvatar_hd()));
        holder.userName.setText(comment.getUser().getName());
        holder.createTime.setText(TimeUtils.formatUTCTime(comment.getCreatedAt() , TimeUtils.MINUTE_SECOND));
        holder.content.setText(SpanUtil.buildSpan(context , comment.getText()));
        holder.content.setMovementMethod(LinkMovementMethod.getInstance());
        holder.likeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //

            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return comments == null ? 0 : comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
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
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
