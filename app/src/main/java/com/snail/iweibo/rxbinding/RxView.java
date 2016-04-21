package com.snail.iweibo.rxbinding;

import android.support.annotation.NonNull;
import android.view.View;

import rx.Observable;

/**
 * RxView
 * Created by alexwan on 16/4/21.
 */
public final class RxView {
    public static Observable<Void> clicks(@NonNull View view){
        return Observable.create(new ViewClickOnSubscribe(view));
    }
}
