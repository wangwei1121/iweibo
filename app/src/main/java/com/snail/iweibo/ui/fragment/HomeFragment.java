package com.snail.iweibo.ui.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.snail.iweibo.mvp.model.Statuses;
import com.snail.iweibo.mvp.view.impl.fragment.IHomeFragmentView;
import com.snail.iweibo.network.RetrofitClient;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        RetrofitClient
            .getStatuseService()
            .getPublicTimeLine("SlAV32hkKG", 50, 1, 0)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Subscriber<Statuses>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getActivity(), "onCompleted : ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "error : " + e, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(Statuses statuses) {
                        Toast.makeText(getActivity(), "statuses : " + statuses.toString(), Toast.LENGTH_LONG)
                             .show();
                    }
                });
    }


    @Override
    protected Class<IHomeFragmentView> getViewClass() {
        return IHomeFragmentView.class;
    }
    //
}
