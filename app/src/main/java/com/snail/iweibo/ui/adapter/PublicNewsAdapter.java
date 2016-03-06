package com.snail.iweibo.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.model.PublicNews;
import com.snail.iweibo.mvp.model.Statuse;
import com.snail.iweibo.util.DateUtil;
import com.snail.iweibo.util.PicassoHelper;

import java.util.List;

/**
 * Created by wang.weib on 2016/3/2.
 */
public class PublicNewsAdapter extends BaseAdapter {


    private Context context;
    private List<Statuse> statuseList;

    public PublicNewsAdapter(Context context, List<Statuse> statuseList) {
        this.context = context;
        this.statuseList = statuseList;
    }

    public int getCount() {
        return null == statuseList ? 0 : statuseList.size();
    }

    public Object getItem(int position) {
        return statuseList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (null == convertView) {
            convertView = View.inflate(context, R.layout.public_news_item, null);
        }

        Statuse statuse = statuseList.get(position);

        //获取要显示的组件,注意findViewById的调用对象是上面填充了Item的布局的对象View
        TextView tv_name = (TextView) convertView.findViewById(R.id.txt_wb_item_uname);
        TextView tv_content = (TextView) convertView.findViewById(R.id.txt_wb_item_content);
        TextView tv_time = (TextView) convertView.findViewById(R.id.txt_wb_item_time);
        TextView tv_from = (TextView) convertView.findViewById(R.id.txt_wb_item_from);
        TextView tv_comment = (TextView) convertView.findViewById(R.id.txt_wb_item_comment);
        TextView tv_repost = (TextView) convertView.findViewById(R.id.txt_wb_item_redirect);

        LinearLayout zlayout = (LinearLayout) convertView.findViewById(R.id.lyt_wb_item_sublayout);
        TextView tv_zcontent = (TextView) convertView.findViewById(R.id.txt_wb_item_subcontent);

        ImageView iv_userhead = (ImageView) convertView.findViewById(R.id.img_wb_item_head);
        ImageView iv_isv = (ImageView) convertView.findViewById(R.id.img_wb_item_V);
        ImageView iv_content_pic = (ImageView) convertView.findViewById(R.id.img_wb_item_content_pic);
        ImageView iv_zcontent_pic = (ImageView) convertView.findViewById(R.id.img_wb_item_content_subpic);

        //组件添加内容
        tv_content.setText(statuse.getText());
        tv_name.setText(statuse.getUser().getName());
        tv_from.setText("来自:" + Html.fromHtml(statuse.getSource()));
        tv_repost.setText(statuse.getReposts_count() + "");
        tv_comment.setText(statuse.getComments_count() + "");

        tv_time.setText(DateUtil.parseUS(statuse.getCreated_at()));

        PicassoHelper.loadImage(context,statuse.getUser().getProfile_image_url(),iv_userhead);

//        if(!statuse.getBmiddle_pic().equals(""))
//        {
//            loadBitmap(statuse.getBmiddle_pic(), iv_content_pic,0,0);
//            iv_content_pic.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            iv_content_pic.setVisibility(View.GONE);
//        }
//
//        if(statuse.getUser().isIsv())
//            iv_isv.setVisibility(View.VISIBLE);
//        else
//            iv_isv.setVisibility(View.GONE);
//
//        if(statuse.getWeibo()!=null)
//        {
//            zlayout.setVisibility(View.VISIBLE);
//            tv_zcontent.setText("@"+statuse.getWeibo().getUser().getName()+":"+statuse.getWeibo().getContent());
//            if(!statuse.getWeibo().getBmiddle_pic().equals(""))
//            {
//                loadBitmap(statuse.getWeibo().getBmiddle_pic(), iv_zcontent_pic,0,0);
//                iv_zcontent_pic.setVisibility(View.VISIBLE);
//            }
//        }
//        else
//            zlayout.setVisibility(View.GONE);

        return convertView;
    }

    public void addItem(Statuse statuse) {
        statuseList.add(statuse);
    }
}
