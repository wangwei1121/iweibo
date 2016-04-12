package com.snail.iweibo.mvp.view.impl.activity;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.snail.iweibo.R;
import com.snail.iweibo.mvp.model.UserBean;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.oauth.AccessTokenKeeper;
import com.snail.iweibo.ui.activity.UserDetailActivity;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.snail.iweibo.ui.fragment.HomeFragment;
import com.snail.iweibo.ui.fragment.SettingFragment;
import com.snail.iweibo.util.SharePreferencesUtil;
import com.snail.iweibo.widget.theme.ThemeUIInterface;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * IMainActivityView
 * Created by alexwan on 16/1/28.
 */
public class IMainActivityView implements IBaseView {
    private View mView;
    @Bind(R.id.tool_bar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.root_layout)
    CoordinatorLayout mRootLayout;
    @Bind(R.id.navigation)
    NavigationView navigationView;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.fab_btn)
    FloatingActionButton fabBtn;
    @Bind(R.id.navigate_icon)
    LinearLayout navigateBtn;
    @Bind(R.id.tab_user_avatar)
    SimpleDraweeView userHeader;
    @Bind(R.id.tab_user_name)
    TextView tabUserName;
    private Fragment lastFragment;
    private BasePresenterActivity context;
    private ActionBarDrawerToggle drawerToggle;
    private SimpleDraweeView userAvatar;
    private TextView userName;
    private TextView userState;
    @Override
    public void init(Context context, LayoutInflater inflater, ViewGroup viewGroup) {
        mView = inflater.inflate(R.layout.activity_main, viewGroup, false);
        ButterKnife.bind(this, mView);
        this.context = (BasePresenterActivity) context;
    }

    @Override
    public View getView() {
        return mView;
    }

    /**
     * 初始化View
     */
    public void initViews() {
        // toolbar
        context.setSupportActionBar(mToolbar);
        final android.support.v7.app.ActionBar actionBar = context.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            // add back icon
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        // drawer toggle
        this.drawerToggle = new ActionBarDrawerToggle(context, mDrawerLayout, R.string.tool_name, R.string.tool_name);
        drawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(drawerToggle);
        navigationView.setCheckedItem(R.id.main_frame);
        // 默认首页HomeFragment
        final HomeFragment homeFragment = new HomeFragment();
        homeFragment.setTabLayout(tabLayout);
        lastFragment = homeFragment;

        // 设置SettingFragment
        final SettingFragment settingFragment = new SettingFragment();
        final FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, homeFragment).commit();
        //
        View headerView = navigationView.getHeaderView(0);
        final ImageView themeWitch = (ImageView) headerView.findViewById(R.id.theme_switch);

        themeWitch.setImageResource(SharePreferencesUtil.isDarkTheme(context) ? R.drawable.icon_theme_day :
            R.drawable.icon_theme_night);
        themeWitch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferencesUtil.setDarkTheme(context);
                themeWitch.setImageResource(SharePreferencesUtil.isDarkTheme(context) ? R.drawable.icon_theme_day :
                    R.drawable.icon_theme_night);
                switchTheme();
            }
        });
        // 用户头像
        userAvatar = (SimpleDraweeView) headerView.findViewById(R.id.user_avatar);
        RoundingParams roundingParams = userAvatar.getHierarchy().getRoundingParams();
        roundingParams.setBorder(R.color.main_white , 2);
        userAvatar.getHierarchy().setRoundingParams(roundingParams);
        userAvatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断用户有没有登录
                Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(context);
                if(!TextUtils.isEmpty(token.getToken())){
                    UserDetailActivity.start(context , token);
                }else{

                }
            }
        });
        // 用户名
        userName = (TextView) headerView.findViewById(R.id.user_name);
        // 用户描述
        userState = (TextView) headerView.findViewById(R.id.user_state);
        // navigation
        navigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                mDrawerLayout.closeDrawers();
                navigationView.setCheckedItem(id);
                // Fragment 切换
                switch (id) {
                    case R.id.main_frame:
                        tabLayout.setVisibility(View.GONE);
                        switchFragment(context, lastFragment, homeFragment);
                        break;
                    case R.id.message_frame:
                        tabLayout.setVisibility(View.GONE);
                        switchFragment(context, lastFragment, settingFragment);
                        break;
                    case R.id.search_frame:
                        tabLayout.setVisibility(View.GONE);
                        switchFragment(context, lastFragment, settingFragment);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        //
        navigateBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    /**
     * 切换Fragment
     *
     * @param activity activity
     * @param from     from
     * @param to       to
     */
    public void switchFragment(BasePresenterActivity activity, Fragment from, Fragment to) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            // 隐藏当前的fragment，add下一个到Activity中
            transaction.hide(from).add(R.id.frame_layout, to).commit();
        } else {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commit();
        }
        lastFragment = to;
    }

    /**
     * 切换主题
     */
    public void switchTheme() {
        Window window = context.getWindow();
        // get current window decor view
        final View contentView = window.getDecorView().findViewById(android.R.id.content);
        contentView.setDrawingCacheEnabled(true);
        mView.buildDrawingCache();
        // build decor view bitmap
        final Bitmap bitmap = Bitmap.createBitmap(contentView.getDrawingCache());
        contentView.setDrawingCacheEnabled(false);
        // theme config
        boolean isDark = SharePreferencesUtil.isDarkTheme(context);
        // generate animation view
        final View animView = new View(contentView.getContext().getApplicationContext());
        if (VERSION.SDK_INT > VERSION_CODES.JELLY_BEAN) {
            animView.setBackground(new BitmapDrawable(context.getResources(), bitmap));
        } else {
            animView.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bitmap));
        }
        // add animation view to window's decor
        ((ViewGroup) contentView)
            .addView(animView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        // change theme
        context.setTheme(isDark ? R.style.AppTheme_Dark : R.style.AppTheme);
        changeTheme(mDrawerLayout, context.getTheme());
        // change status bar
        int statusColor =
            context.getResources().getColor(isDark ? R.color.color_primary_dark_inverse : R.color.main_dark_blue);
        if (VERSION.SDK_INT > VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(statusColor);
        }
        // action bar
        ActionBar actionBar = context.getSupportActionBar();
        int actionBarColor =
            context.getResources().getColor(isDark ? R.color.color_primary_dark_inverse : R.color.main_blue);
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
        }
        int colorId = isDark ? R.color.color_primary_dark_inverse : R.color.main_white;
        navigationView.setBackgroundColor(context.getResources().getColor(colorId));
        // navigation view
        ObjectAnimator animator = ObjectAnimator.ofFloat(animView, "alpha", 0f).setDuration(200);
        animator.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((ViewGroup) contentView).removeView(animView);
                bitmap.recycle();
                Runtime.getRuntime().gc();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    /**
     * 修改主题
     * @param view view
     * @param theme theme
     */
    public void changeTheme(View view, Resources.Theme theme) {

        if (view instanceof ThemeUIInterface) {
            Log.i("IMainActivityView" , view.toString() +" - ");
            ((ThemeUIInterface) view).setTheme(theme);
        }
        if (view instanceof ViewGroup) {

            int count = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < count; i++) {
                changeTheme(((ViewGroup) view).getChildAt(i), theme);
            }
        }
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }

    public void updateUserInfo(UserBean userBean) {
        userAvatar.setImageURI(UriUtil.parseUriOrNull(userBean.getAvatar_hd()));
        userName.setText(userBean.getScreen_name());
        userState.setText(userBean.getDescription());
        userHeader.setImageURI(UriUtil.parseUriOrNull(userBean.getAvatar_hd()));
        tabUserName.setText(userBean.getScreen_name());
    }
}
