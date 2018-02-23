package com.example.administrator.rxjava_learning;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/2/23.
 */

public class EasyActivity extends Activity {
    @BindView(R.id.easy_txt)
    TextView easyTxt;
    @BindView(R.id.easy_txt1)
    TextView easyTxt1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.easy_btn, R.id.easy_btn1})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.easy_btn:
                EasyRxJavaOne();
                break;
            case R.id.easy_btn1:
                EasyRxJavaTwo();
                break;
        }
    }

    /**
     * 观察者与被观察者的非链式绑定
     **/
    private void EasyRxJavaOne() {
        //创建被观察者
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            //在复写的subscribe（）里定义需要发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 通过 ObservableEmitter类对象产生事件并通知观察者
                // ObservableEmitter类介绍
                // a. 定义：事件发射器
                // b. 作用：定义需要发送的事件 & 向观察者发送事件
                emitter.onNext(0);
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });

        //观察者
        Observer<Integer> observer = new Observer<Integer>() {
            //切断作用
            Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                easyTxt.append("onSubscribe" + "---");
            }

            @Override
            public void onNext(Integer integer) {
                if (integer == 2) {
                    mDisposable.dispose();
                }
                easyTxt.append(integer + "---mDisposable中断");
            }

            @Override
            public void onError(Throwable e) {
                easyTxt.append("onError");
            }

            @Override
            public void onComplete() {
                easyTxt.append("onComplete");
            }
        };
        //绑定
        observable.subscribe(observer);
    }

    /**
     * 观察者与被观察者的链式绑定
     **/
    private void EasyRxJavaTwo() {
        // 1. 创建被观察者 & 生产事件
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            // 2. 通过通过订阅（subscribe）连接观察者和被观察者
            // 3. 创建观察者 & 定义响应事件的行为
            @Override
            public void onSubscribe(Disposable d) {
                //默认最先调用复写的 onSubscribe（）
                easyTxt1.append("onSubscribe--");
            }

            @Override
            public void onNext(Integer integer) {
                easyTxt1.append(integer + "--");
            }

            @Override
            public void onError(Throwable e) {
                easyTxt1.append("--onError" + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                easyTxt1.append("--onComplete");
            }
        });
    }
}
