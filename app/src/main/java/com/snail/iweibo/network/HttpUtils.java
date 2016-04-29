package com.snail.iweibo.network;

import android.util.Log;

import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.util.Keys;
import com.snail.iweibo.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wang.weib on 2016/4/29.
 */
public class HttpUtils {

    private static final int TIMEOUT_IN_MILLIONS = 5000;

    public interface CallBack {
        void onRequestComplete(String result);
    }

    /**
     * 异步的Get请求
     *
     * @param urlStr
     * @param callBack
     */
    public static void doGetAsyn(final String urlStr, final CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr);
                    if (null != callBack) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();
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
        new Thread() {
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

            ;
        }.start();

    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public static String doGet(String urlStr) {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        String result = null;
        try {
            conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                inputStream = conn.getInputStream();
                result = reader(inputStream);
            } else {
                throw new RuntimeException("ResponseCode:" + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            conn.disconnect();
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
        OutputStream outputStream = null;
        InputStream inputStream = null;
        String result = null;
        try {
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
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
            Log.e(Constants.PACKAGE,e.getMessage());
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
        }
        return result;
    }

    public static String reader(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        byte[] b = new byte[1024];
        int i = inputStream.read(b);
        while ((i = inputStream.read(b))!=-1) {
            builder.append(new String(b, 0, i, Keys.SYS_ENCODING));
        }
        return builder.toString();
    }
}
