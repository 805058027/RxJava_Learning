package com.example.administrator.rxjava_learning.CombinationOperator;

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
 * 组合多个被观察者一起发送数据，合并后 按时间线并行执行
 * 二者区别：组合被观察者的数量，即merge（）组合被观察者数量≤4个，而mergeArray（）则可＞4个
 * 区别上述concat（）操作符：同样是组合多个被观察者一起发送数据，但concat（）操作符合并后是按发送顺序串行执行
 */

public class MergeActivity extends Activity {
    @BindView(R.id.public_btn)
    Button publicBtn;
    private String TAG = "wsj";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        ButterKnife.bind(this);
        publicBtn.setText("Merge操作符");
    }

    @OnClick(R.id.public_btn)
    public void OnClick(View view) {
        Observable.merge(Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS), Observable.intervalRange(6, 3, 1, 1, TimeUnit.SECONDS)).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "接收到了事件" + aLong);
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
