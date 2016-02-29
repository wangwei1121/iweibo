package com.snail.iweibo.ui.adapter;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.snail.iweibo.ui.fragment.RecyclerViewFragment;

/**
 * HomePagerAdapter
 * Created by alexwan on 16/1/30.
 */
public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private FragmentManager manager;
    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        this.manager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return RecyclerViewFragment.newInstance();
    }

    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return "标题";
    }
}
