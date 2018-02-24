package com.example.administrator.rxjava_learning.ChangeOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.rxjava_learning.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 定期从 被观察者（Obervable）需要发送的事件中 获取一定数量的事件 & 放到缓存区中，最终发送
 * <p>
 * 应用场景
 * 缓存被观察者发送的事件
 * <p>
 * 具体使用
 * 那么，Buffer（）每次是获取多少个事件放到缓存区中的呢？下面我将通过一个例子来说明
 */

public class BufferActivity extends Activity {
    @BindView(R.id.public_btn)
    Button publicBtn;
    private String TAG = "wsj";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        ButterKnife.bind(this);
        publicBtn.setText("Buffer操作符");
    }

    @OnClick(R.id.public_btn)
    public void OnClick(View view) {
        Observable.just(1, 2, 3, 4, 5).buffer(3, 1)
                // 设置缓存区大小 & 步长
                // 缓存区大小 = 每次从被观察者中获取的事件数量
                // 步长 = 每次获取新事件的数量
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.d(TAG, " 缓存区里的事件数量 = " + integers.size());
                        for (Integer value : integers) {
                            Log.d(TAG, " 事件 = " + value);
                        }
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
