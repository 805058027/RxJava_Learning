package com.example.administrator.rxjava_learning.BasisOperator;

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
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * range（）作用
 * 快速创建1个被观察者对象（Observable）
 * 发送事件的特点：连续发送 1个事件序列，可指定范围
 * a.发送的事件序列 = 从0开始、无限递增1的的整数序列
 * b.作用类似于intervalRange（），但区别在于：无延迟发送事件
 * <p>
 * rangeLong（）
 * 作用：类似于range（），区别在于该方法支持数据类型 = Long
 */

public class RangActivity extends Activity {


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
        easyBtn.setText("Rang()-操作符");
        easyBtn1.setText("RangLong()-操作符");
    }

    @OnClick({R.id.easy_btn, R.id.easy_btn1})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.easy_btn:
                // 参数1 = 事件序列起始点；
                // 参数2 = 事件数量；
                // 注：若设置为负数，则会抛出异常
                Observable.range(3, 10)
                        // 该例子发送的事件序列特点：从3开始发送，每次发送事件递增1，一共发送10个事件
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "开始采用subscribe连接");
                            }
                            // 默认最先调用复写的 onSubscribe（）

                            @Override
                            public void onNext(Integer value) {
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
            case R.id.easy_btn1:
                Observable.rangeLong(2, 5).subscribe(new Observer<Long>() {
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
        }
    }
}
