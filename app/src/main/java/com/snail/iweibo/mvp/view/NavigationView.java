package com.snail.iweibo.mvp.view;

import android.support.v7.app.ActionBarDrawerToggle;

/**
 * Created by wang.weib on 2016/3/3.
 */
public abstract class NavigationView implements IBaseView{
   private ActionBarDrawerToggle drawerToggle;

    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }

    public void setDrawerToggle(ActionBarDrawerToggle drawerToggle) {
        this.drawerToggle = drawerToggle;
    }
}
