package com.snail.iweibo.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.snail.iweibo.R;
import com.snail.iweibo.ui.BaseActivity;
import com.snail.iweibo.util.BitmapFileCache;
import com.snail.iweibo.util.BitmapUtil;
import com.snail.iweibo.util.Keys;
import com.snail.iweibo.util.MD5Util;
import com.snail.iweibo.util.PicassoHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LargeImageActivity extends BaseActivity {

    private Activity activity;
    private String[] imageUrls;
    private int imageIndex;
    private ImageView imageView;
    private TextView loadingTextView;
    private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image);
        activity = this;
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            imageUrls = bundle.getStringArray("imageUrls");
            imageIndex = bundle.getInt("imageIndex");
        }
        View dialogEntry = LayoutInflater.from(LargeImageActivity.this).inflate(R.layout.dialog_image_entry, null);
        loadingTextView = (TextView) dialogEntry.findViewById(R.id.loading_text);
        dialog = new ProgressDialog(LargeImageActivity.this);
        dialog.setProgress(0);
//        dialog.setView(loadingTextView);
        dialog.show();
        imageView = (ImageView)this.findViewById(R.id.large_image);
        BitmapUtil.initAsynBitmap(LargeImageActivity.this, imageView, imageUrls[imageIndex].replace("thumbnail","bmiddle"));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        AsyncTask asyncTask = new LargeImageTask();
        asyncTask.execute(imageUrls[imageIndex].replace("thumbnail", "large"));
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
        overridePendingTransition(0,0);
    }
    private class LargeImageTask extends AsyncTask<Object, Integer, Bitmap> {
        protected Bitmap doInBackground(Object... urls) {
            String url = (String)urls[0];
            Log.d(Keys.PACKAGE,"doInBackground-->" + url);
            String encodeURL = MD5Util.encode(url);
            HttpURLConnection conn = null;
            InputStream inputStream = null;
            InputStream is = null;
            OutputStream os = null;
            int buffer_size = 1024;
            try {
                File file = BitmapFileCache.getInstance().getFile(encodeURL);
                Bitmap bitmap = BitmapUtil.decodeFile(file);
                if(null != bitmap){
                    return bitmap;
                }
                URL ImageUrl = new URL(url);
                conn = (HttpURLConnection) ImageUrl.openConnection();
                conn.setConnectTimeout(50000);
                conn.setReadTimeout(50000);
                conn.setInstanceFollowRedirects(true);
                int length = conn.getContentLength();
                int sumCount = 0;
                Log.d(Keys.PACKAGE,url + "-->" + length);
                is = conn.getInputStream();
                os = new FileOutputStream(file);
                byte[] bytes=new byte[buffer_size];
                for(;;){
                    int count = is.read(bytes, 0, buffer_size);
                    if(count == -1)
                        break;
                    os.write(bytes, 0, count);
                    sumCount += count;
                    Log.d(Keys.PACKAGE,sumCount + ";" + count);
                    publishProgress((int) ((sumCount / (float)length) * 100));
                    Log.d(Keys.PACKAGE,(int) ((sumCount / (float)length) * 100) + "");
                }
                os.flush();
                bitmap = BitmapUtil.decodeFile(file);
                BitmapFileCache.getInstance().put(encodeURL, bitmap);
                return bitmap;
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
                if(null != conn){
                    conn.disconnect();
                }
            }
            return BitmapUtil.getBitmap(url);
        }

        protected void onProgressUpdate(Integer... progress) {
            Log.d(Keys.PACKAGE, progress[0] + "################");
            dialog.setProgress(progress[0]);
//              Toast.makeText(context,progress[0],Toast.LENGTH_SHORT);
        }

        protected void onPostExecute(Bitmap result) {
            Log.d(Keys.PACKAGE,result.getWidth() + ";" + result.getHeight());
            imageView.setImageBitmap(result);
            dialog.cancel();
        }
    }

}