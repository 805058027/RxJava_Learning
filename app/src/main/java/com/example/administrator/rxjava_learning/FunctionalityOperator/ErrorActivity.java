package com.example.administrator.rxjava_learning.FunctionalityOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.rxjava_learning.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 一系列错误处理的操作符
 * <p>
 * 发送事件过程中，遇到错误时的处理机制
 */

public class ErrorActivity extends Activity {

    @BindView(R.id.error_btn1)
    Button errorBtn1;
    @BindView(R.id.error_btn2)
    Button errorBtn2;
    @BindView(R.id.error_btn3)
    Button errorBtn3;
    private String TAG = "wsj--";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.error_btn1, R.id.error_btn2, R.id.error_btn3})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.error_btn1:
                onErrorReturn();
                break;
            case R.id.error_btn2:
                onErrorResumeNext();
                break;
            case R.id.error_btn3:
                onExceptionResumeNext();
                break;
        }
    }

    /**
     * 遇到错误时，发送1个特殊事件 & 正常终止
     * 可捕获在它之前发生的异常
     */
    private void onErrorReturn() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Throwable("发生了异常"));
            }
        }).onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(@NonNull Throwable throwable) throws Exception {
                // 捕捉错误异常
                Log.e(TAG, "在onErrorReturn处理了错误: " + throwable.toString());
                return 666;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "接收到了事件" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    /**
     * 遇到错误时，发送1个新的Observable
     * 1.onErrorResumeNext（）拦截的错误 = Throwable；若需拦截Exception请用onExceptionResumeNext（）
     * 2.若onErrorResumeNext（）拦截的错误 = Exception，则会将错误传递给观察者的onError方法
     */
    private void onErrorResumeNext() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Throwable("发生错误了"));
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> apply(@NonNull Throwable throwable) throws Exception {
                // 1. 捕捉错误异常
                Log.e(TAG, "在onErrorReturn处理了错误: " + throwable.toString());
                // 2. 发生错误事件后，发送一个新的被观察者 & 发送事件序列
                return Observable.just(11, 21);
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "接收到了事件" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    private void onExceptionResumeNext() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
            }
        }).onExceptionResumeNext(new Observable<Integer>() {
            @Override
            protected void subscribeActual(Observer<? super Integer> observer) {
                observer.onNext(11);
                observer.onNext(22);
                observer.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "接收到了事件" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }
}
