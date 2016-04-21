package com.snail.iweibo.rxbinding;

import android.view.View;
import android.view.View.OnClickListener;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

import static rx.android.MainThreadSubscription.verifyMainThread;

/**
 * ViewClickOnSubscribe
 * Created by alexwan on 16/4/21.
 */
final class ViewClickOnSubscribe implements Observable.OnSubscribe<Void> {
    final View view;

    ViewClickOnSubscribe(View view) {
        this.view = view;
    }


    @Override
    public void call(final Subscriber<? super Void> subscriber) {
        verifyMainThread();

        View.OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }
        };
        view.setOnClickListener(listener);
        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                view.setOnClickListener(null);
            }
        });
    }
}
