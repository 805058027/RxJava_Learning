package com.example.administrator.rxjava_learning.FunctionalityOperator;

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
 * 需求场景
 * 即在被观察者发送事件前进行一些延迟的操作
 * 作用
 * 使得被观察者延迟一段时间再发送事件
 */

public class DelayActivity extends Activity {

    @BindView(R.id.public_btn)
    Button publicBtn;
    private String TAG = "wsj--";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        ButterKnife.bind(this);
        publicBtn.setText("Delay操作符");
    }

    @OnClick(R.id.public_btn)
    public void OnClick(View view) {
        Observable.just(1, 2, 3).delay(3, TimeUnit.SECONDS).subscribe(new Observer<Integer>() {
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
