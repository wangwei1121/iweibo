package com.snail.iweibo.mvp.view.impl.fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * IMessageFragmentView
 * Created by alexwan on 16/1/30.
 */
public class IOAuthFragmentView implements IBaseView {

    private View mView;

    @Bind(R.id.obtain_token_via_web)
    Button button;

    /** 显示认证后的信息，如 AccessToken */
    @Bind(R.id.token_text_view)
    TextView mTokenText;

    @Override
    public void init(LayoutInflater inflater, ViewGroup viewGroup) {
        mView = inflater.inflate(R.layout.fragment_oauth, viewGroup, false);
        ButterKnife.bind(this, mView);
    }

    @Override
    public View getView() {
        return mView;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public TextView getmTokenText() {
        return mTokenText;
    }

    public void setmTokenText(TextView mTokenText) {
        this.mTokenText = mTokenText;
    }
}
