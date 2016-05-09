package com.snail.iweibo.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.snail.iweibo.R;
import com.snail.iweibo.ui.BaseActivity;
import com.snail.iweibo.util.PicassoHelper;

public class LargeImageActivity extends BaseActivity {

    private Activity activity;
    private String[] imageUrls;
    private int imageIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image);
        activity = this;
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            imageUrls = bundle.getStringArray("imageUrls");
            imageIndex = bundle.getInt("imageIndex");
        }
        ImageView img = (ImageView)this.findViewById(R.id.large_image);
        PicassoHelper.loadImage(LargeImageActivity.this, imageUrls[imageIndex].replace("thumbnail", "large"), img);
        Toast toast = Toast.makeText(this, "点击图片即可返回", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
}