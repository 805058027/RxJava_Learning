package com.example.administrator.rxjava_learning.CombinationOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.rxjava_learning.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 组合多个被观察者一起发送数据，合并后 按发送顺序串行执行
 * 二者区别：组合被观察者的数量，即concat（）组合被观察者数量≤4个，而concatArray（）则可＞4个
 */

public class ConcatActivity extends Activity {

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
        easyBtn.setText("concat操作符");
        easyBtn1.setText("concatArray操作符");
    }

    @OnClick({R.id.easy_btn, R.id.easy_btn1})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.easy_btn:
                concat();
                break;
            case R.id.easy_btn1:
                concatArray();
                break;
        }
    }

    private void concat() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6)).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "接收到了事件" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError" + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    private void concatArray() {
        Observable.concatArray(Observable.just(1, 2), Observable.just(3, 4), Observable.just(5, 6),
                Observable.just(7, 8), Observable.just(9, 10)).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "接收到了事件" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError" + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }
}
