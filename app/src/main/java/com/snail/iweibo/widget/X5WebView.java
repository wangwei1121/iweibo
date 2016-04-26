package com.snail.iweibo.widget;

import android.content.Context;
import android.net.Uri;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * SnailWebView
 * Created by alexwan on 16/4/26.
 */
public class X5WebView extends WebView {

    public X5WebView(Context context) {
        super(context);
        initSettings();
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initSettings();
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initSettings();
    }

    private void initSettings(){
        // // TODO: 16/4/26
        this.setWebViewClient(client);
        this.setWebChromeClient(chromeClient);
    }
    private WebChromeClient chromeClient = new WebChromeClient() {

        @Override
        public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
            return super.onJsConfirm(arg0, arg1, arg2, arg3);
        }

        View myVideoView;
        View myNormalView;
        CustomViewCallback callback;

        ///////////////////////////////////////////////////////////
        //
        /**
         * 全屏播放配置
         */
        @Override
        public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
//            FrameLayout normalView = (FrameLayout) ((Activity) getContext()).findViewById(R.id.web_filechooser);
//            ViewGroup viewGroup = (ViewGroup) normalView.getParent();
//            viewGroup.removeView(normalView);
//            viewGroup.addView(view);
//            myVideoView = view;
//            myNormalView = normalView;
//            callback = customViewCallback;
        }

        @Override
        public void onHideCustomView() {
            if (callback != null) {
                callback.onCustomViewHidden();
                callback = null;
            }
            if (myVideoView != null) {
                ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                viewGroup.removeView(myVideoView);
                viewGroup.addView(myNormalView);
            }
        }

        @Override
        public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String captureType) {
//            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//            i.addCategory(Intent.CATEGORY_OPENABLE);
//            i.setType("*/*");
//            ((Activity) (X5WebView.this.getContext())).startActivityForResult(Intent.createChooser(i, "choose files"),
//                X5WebView.FILE_CHOOSER);
//            super.openFileChooser(uploadFile, acceptType, captureType);
        }
        /**
         * webview 的窗口转移
         */
        @Override
        public boolean onCreateWindow(WebView arg0, boolean arg1, boolean arg2, Message msg) {
            // TODO Auto-generated method stub
//            if (X5WebView.isSmallWebViewDisplayed == true) {
//
//                WebView.WebViewTransport webViewTransport = (WebView.WebViewTransport) msg.obj;
//                WebView webView = new WebView(X5WebView.this.getContext()) {
//
//                    protected void onDraw(Canvas canvas) {
//                        super.onDraw(canvas);
//                        Paint paint = new Paint();
//                        paint.setColor(Color.GREEN);
//                        paint.setTextSize(15);
//                        canvas.drawText("新建窗口", 10, 10, paint);
//                    };
//                };
//                webView.setWebViewClient(new WebViewClient() {
//                    public boolean shouldOverrideUrlLoading(WebView arg0, String arg1) {
//                        arg0.loadUrl(arg1);
//                        return true;
//                    };
//                });
//                FrameLayout.LayoutParams lp = new LayoutParams(400, 600);
//                lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
//                X5WebView.this.addView(webView, lp);
//                webViewTransport.setWebView(webView);
//                msg.sendToTarget();
//            }
            return true;
        }

        @Override
        public boolean onJsAlert(WebView arg0, String arg1, String arg2, JsResult arg3) {
            /**
             * 这里写入你自定义的window alert
             */
            // AlertDialog.Builder builder = new Builder(getContext());
            // builder.setTitle("X5内核");
            // builder.setPositiveButton("确定", new
            // DialogInterface.OnClickListener() {
            //
            // @Override
            // public void onClick(DialogInterface dialog, int which) {
            // // TODO Auto-generated method stub
            // dialog.dismiss();
            // }
            // });
            // builder.show();
            // arg3.confirm();
            // return true;
            Log.i("yuanhaizhou", "setX5webview = null");
            return super.onJsAlert(null, "www.baidu.com", "aa", arg3);
        }

        /**
         * 对应js 的通知弹框 ，可以用来实现js 和 android之间的通信
         */
//        @Override
//        public boolean onJsPrompt(WebView arg0, String arg1, String arg2, String arg3, JsPromptResult arg4) {
            // 在这里可以判定js传过来的数据，用于调起android native 方法
//            if (X5WebView.this.isMsgPrompt(arg1)) {
//                if (X5WebView.this.onJsPrompt(arg2, arg3)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//            return super.onJsPrompt(arg0, arg1, arg2, arg3, arg4);
//        }

        @Override
        public void onReceivedTitle(WebView arg0, final String arg1) {
            super.onReceivedTitle(arg0, arg1);
            Log.i("yuanhaizhou", "webpage title is " + arg1);

        }
    };
    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onReceivedHttpAuthRequest(WebView webview, HttpAuthHandler httpAuthHandlerhost, String host, String realm) {
            boolean flag = httpAuthHandlerhost.useHttpAuthUsernamePassword();
        }
    };
}
