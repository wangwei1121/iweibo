package com.snail.iweibo.mvp.view.impl.activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.iweibo.R;
import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.snail.iweibo.util.SpanUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.dreamtobe.kpswitch.util.KPSwitchConflictUtil;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchPanelLinearLayout;

/**
 * 微博转发View
 * Created by alexwan on 16/4/27.
 */
public class IRetweetActivityView implements IBaseView{

    @Bind(R.id.tool_bar)
    Toolbar toolBar;
    @Bind(R.id.edit_text)
    EditText editText;
    @Bind(R.id.image_view)
    SimpleDraweeView imageView;
    @Bind(R.id.status_user_name)
    TextView userName;
    @Bind(R.id.status_content)
    TextView statusContent;
    @Bind(R.id.panel_root)
    KPSwitchPanelLinearLayout panelLayout;
    @Bind(R.id.action_retweet)
    Button retweetBtn;
    @Bind(R.id.action_choose_image)
    Button imgChoseBtn;
    @Bind(R.id.action_at_friend)
    Button choseFriendBtn;
    @Bind(R.id.action_add_topic)
    Button addTopicBtn;
    @Bind(R.id.action_add_emotion)
    Button addEmotionBtn;
    private View view;
    private BasePresenterActivity context;
    @Override
    public void init(final Context context, LayoutInflater inflater, ViewGroup viewGroup) {
        this.context = (BasePresenterActivity) context;
        view = inflater.inflate(R.layout.activity_retweet , viewGroup ,false);
        ButterKnife.bind(this , view);
        ((BasePresenterActivity) context).setSupportActionBar(toolBar);
        ActionBar actionBar = ((BasePresenterActivity) context).getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        toolBar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BasePresenterActivity) context).finish();
            }
        });

    }

    @Override
    public View getView() {
        return view;
    }

    public void updateView(Status status , OnClickListener listener) {
        if(status == null){
            return;
        }
        // click event
        retweetBtn.setOnClickListener(listener);
        imgChoseBtn.setOnClickListener(listener);
        choseFriendBtn.setOnClickListener(listener);
        addTopicBtn.setOnClickListener(listener);
        addEmotionBtn.setOnClickListener(listener);

        // keyboard
        KeyboardUtil.attach(context, panelLayout);
        KPSwitchConflictUtil.attach(panelLayout, null , editText);

        if(status.getRetweetedStatus() != null){
            editText.setText(SpanUtil.buildSpan(context ,
                String.format("//@%s:%s", status.getUser().getName() , status.getText())));
            userName.setText(String.format("@%s", status.getRetweetedStatus().getUser().getName()));
            imageView.setImageURI(UriUtil.parseUriOrNull(status.getRetweetedStatus().getUser().getAvatar_hd()));
            statusContent.setText(status.getRetweetedStatus().getText());
        }else {
            userName.setText(String.format("@%s", status.getUser().getName()));
            imageView.setImageURI(UriUtil.parseUriOrNull(status.getUser().getAvatar_hd()));
            statusContent.setText(status.getText());
        }

    }


    public boolean hidePanelAndKeyboard() {
        if(panelLayout == null){
            return false;
        }
        if (panelLayout.isVisible()) {
            KPSwitchConflictUtil.hidePanelAndKeyboard(panelLayout);
            return true;
        }else{
            return false;
        }
    }

    public String getRepostContent() {
        return String.valueOf(editText.getText());
    }
}
