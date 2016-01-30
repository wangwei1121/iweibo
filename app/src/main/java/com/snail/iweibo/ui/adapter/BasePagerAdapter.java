package com.snail.iweibo.ui.adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * BasePagerAdapter
 * Created by alexwan on 16/1/30.
 */
public abstract class BasePagerAdapter extends FragmentStatePagerAdapter {
    public BasePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
