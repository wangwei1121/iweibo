package com.snail.iweibo.ui.base;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.mvp.view.NavigationView;
import com.snail.iweibo.ui.activity.MainActivity;
import com.snail.iweibo.ui.activity.NewsActivity;
import com.snail.iweibo.ui.activity.WBAuthActivity;

public abstract class BaseNavigationActivity extends BasePresenterActivity<NavigationView> {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(view.getDrawerToggle().onOptionsItemSelected(item)){
            return true;
        }
        int id = item.getItemId();
        Intent intent = null;
        switch (id){
            case R.id.main_frame:
                intent = new Intent(this, WBAuthActivity.class);
                this.startActivity(intent);
                break;
            case R.id.oauth_frame:
                intent = new Intent(this, WBAuthActivity.class);
                this.startActivity(intent);
                break;
            case R.id.message_frame:
                intent = new Intent(this, NewsActivity.class);
                this.startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
