package com.snail.iweibo.ui.fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.snail.iweibo.R;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.mvp.model.PublicNews;
import com.snail.iweibo.mvp.view.impl.fragment.IHomeFragmentView;
import com.snail.iweibo.mvp.view.impl.fragment.IOAuthFragmentView;
import com.snail.iweibo.oauth.AccessTokenKeeper;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.base.BasePresenterFragment;
import com.snail.iweibo.util.SharedPreferencesUtil;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * MessageFragment
 * Created by alexwan on 16/1/30.
 */
public class OAuthFragment extends BasePresenterFragment<IOAuthFragmentView>{

    private AuthInfo mAuthInfo;

    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;

    private IOAuthFragmentView oAuthView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.oAuthView = super.mView;
        return view;
    }

    @Override
    protected Class<IOAuthFragmentView> getViewClass() {
        return IOAuthFragmentView.class;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = (Button) getActivity().findViewById(R.id.obtain_token_via_web);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("com.snail.iweibo", "v-->" + v.getId());
                switch (v.getId()){
                    case R.id.obtain_token_via_web:
                        Log.d("com.snail.iweibo","############");
                        mAuthInfo = new AuthInfo(getActivity(), Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
                        mSsoHandler = new SsoHandler(getActivity(), mAuthInfo);
                        mSsoHandler.authorizeWeb(new AuthListener());
                        break;
                    default:
                        Log.d("com.snail.iweibo","-----------------");
                        break;
                }
            }
        });
    }


    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 显示 Token
                updateTokenView(false);
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(getActivity(), mAccessToken);
                Toast.makeText(getActivity(),R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = getString(R.string.weibosdk_demo_toast_auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(getActivity(),
                    R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(getActivity(),
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 显示当前 Token 信息。
     *
     * @param hasExisted 配置文件中是否已存在 token 信息并且合法
     */
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime()));
        String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
        this.oAuthView.getmTokenText().setText(String.format(format, mAccessToken.getToken(), date));
        String message = String.format(format, mAccessToken.getToken(), date);
        if (hasExisted) {
            message = getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
        }
        this.oAuthView.getmTokenText().setText(message);

        Log.d("com.snail.iweibo", mAccessToken.getToken());

        SharedPreferencesUtil.saveData(getActivity(), Constants.SINA_TOKEN, mAccessToken.getToken());
    }
}
