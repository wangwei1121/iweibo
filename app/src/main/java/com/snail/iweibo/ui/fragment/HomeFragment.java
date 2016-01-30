package com.snail.iweibo.ui.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.mvp.model.Statuses;
import com.snail.iweibo.mvp.view.impl.fragment.IHomeFragmentView;

import rx.Subscriber;

/**
 * MessageFragment
 * Created by alexwan on 16/1/30.
 */
public class HomeFragment extends BasePresenterFragment<IHomeFragmentView> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void onBindView() {
        initData();
    }

    /**
     * initData
     */
    private void initData() {
        ApiServiceHelper.getPublicTimeLine("", 50, 1, 0).subscribe(new Subscriber<Statuses>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Statuses statuses) {

            }
        });

    }


    @Override
    protected Class<IHomeFragmentView> getViewClass() {
        return IHomeFragmentView.class;
    }
}
