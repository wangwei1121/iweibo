package com.snail.iweibo.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by wang.weib on 2016/5/5.
 */
public class BitmapTask extends AsyncTask<String, Integer, Bitmap> {
    private Context context;
    private ImageView imageView;
    public BitmapTask(Context context,ImageView imageView){
        this.context = context;
        this.imageView = imageView;
    }
    @Override
    protected void onPreExecute() {
        Log.i(Keys.PACKAGE, "onPreExecute() called");
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Log.i(Keys.PACKAGE, "doInBackground(Params... params) called");
        try {
            return BitmapUtil.getBitmap(params[0]);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Keys.PACKAGE, e.getMessage());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progresses) {
        Log.i(Keys.PACKAGE, "onProgressUpdate(Progress... progresses) called");
    }

    @Override
    protected void onPostExecute(final Bitmap bitmap) {
        Activity activity = (Activity)context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    protected void onCancelled() {
        Log.i(Keys.PACKAGE, "onCancelled() called");
    }
}
