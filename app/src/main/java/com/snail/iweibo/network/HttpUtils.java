package com.snail.iweibo.network;

import android.util.Log;

import com.snail.iweibo.util.Keys;
import com.snail.iweibo.util.StringUtils;
import com.snail.iweibo.util.ThreadPoolUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by wang.weib on 2016/4/29.
 */
public class HttpUtils {

    public static final int TIMEOUT_IN_MILLIONS = 50000;

    public interface CallBack {
        void onRequestComplete(String result);
    }

    /**
     * 异步的Get请求
     *
     * @param url
     * @param callBack
     */
    public static void doGetAsyn(final String url, final CallBack callBack) {
        Log.d(Keys.PACKAGE,url);
        ThreadPoolUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = doGet(url);
                    if (null != callBack) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 异步的Post请求
     *
     * @param urlStr
     * @param callBack
     * @throws Exception
     */
    public static void doPostAsyn(final String urlStr, final String params,
                                  final CallBack callBack) throws Exception {
        ThreadPoolUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = doPost(urlStr, params);
                    if (null != callBack) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Get请求，获得返回数据
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String doGet(String url) {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        String result = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                inputStream = conn.getInputStream();
                result = reader(inputStream);
            } else {
                result = "{\"error\":\"expired_token\",\"error_code\":21327}";
                new RuntimeException("ResponseCode:" + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "{\"error\":\"connection_time_out\",\"error_code\":1000}";
            new RuntimeException(e.getMessage());
        }  finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if(null != conn){
                conn.disconnect();
            }
        }
        return result;

    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param) {
        if(StringUtils.isNotBlank(param)) {
            try {
                return doPost(url, param.getBytes(Keys.SYS_ENCODING));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            return doPost(url,new byte[0]);
        }
        return null;

    }

    public static String doPost(String url, byte[] bytes) {
        HttpURLConnection conn = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        String result = null;
        try {
            // 打开和URL之间的连接
            conn = (HttpURLConnection) new URL(url).openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            if (null != bytes && bytes.length > 0) {
                outputStream = conn.getOutputStream();
                outputStream.write(bytes);
                outputStream.close();
            }
            if (conn.getResponseCode() == 200) {
                inputStream = conn.getInputStream();
                result = reader(inputStream);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Keys.PACKAGE,e.getMessage());
        }finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != outputStream) {
                    outputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if(null != conn){
                conn.disconnect();
            }
        }
        return result;
    }

    public static String reader(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        byte[] b = new byte[1024];
        int i = 0;
        while((i = inputStream.read(b)) != -1) {
            builder.append(new String(b, 0, i, Keys.SYS_ENCODING));
        }
        return builder.toString();
    }
}
