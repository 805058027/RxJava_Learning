package com.example.administrator.rxjava_learning.BasisOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.rxjava_learning.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * interval（）
 * 快速创建1个被观察者对象（Observable）
 * 发送事件的特点：每隔指定时间 就发送 事件
 * 发送的事件序列 = 从0开始、无限递增1的的整数序列
 */

/**
 * intervalRange（）
 * 快速创建1个被观察者对象（Observable）
 * 发送事件的特点：每隔指定时间 就发送 事件，可指定发送的数据的数量
 * a. 发送的事件序列 = 从0开始、无限递增1的的整数序列
 * b. 作用类似于interval（），但可指定发送的数据的数量
 */
public class IntervalActivity extends Activity {
    @BindView(R.id.easy_btn)
    Button easyBtn;
    @BindView(R.id.easy_btn1)
    Button easyBtn1;
    private String TAG = "wsj";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        ButterKnife.bind(this);
        easyBtn.setText("延时操作-- Interval()方法");
        easyBtn1.setText("延时操作-- IntervalRang()方法");
    }

    @OnClick({R.id.easy_btn, R.id.easy_btn1})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.easy_btn:
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                Observable.interval(3, 2, TimeUnit.SECONDS)
                        // 该例子发送的事件序列特点：延迟3s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "开始采用subscribe连接");
                            }

                            @Override
                            public void onNext(Long aLong) {
                                Log.d(TAG, "接收到了事件" + aLong);
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
                break;
            case R.id.easy_btn1:
                // 参数1 = 事件序列起始点；
                // 参数2 = 事件数量；
                // 参数3 = 第1次事件延迟发送时间；
                // 参数4 = 间隔时间数字；
                // 参数5 = 时间单位
                Observable.intervalRange(3, 10, 2, 1, TimeUnit.SECONDS)
                        // 该例子发送的事件序列特点：
                        // 1. 从3开始，一共发送10个事件；
                        // 2. 第1次延迟2s发送，之后每隔2秒产生1个数字（从0开始递增1，无限个）
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "开始采用subscribe连接");
                            }
                            // 默认最先调用复写的 onSubscribe（）

                            @Override
                            public void onNext(Long value) {
                                Log.d(TAG, "接收到了事件" + value);
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
                break;

        }
    }
}
