package com.example.administrator.rxjava_learning.CombinationOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.rxjava_learning.R;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

/**
 * 将被观察者Observable发送的数据事件收集到一个数据结构里
 */

public class CollectActivity extends Activity {
    @BindView(R.id.public_btn)
    Button publicBtn;
    private String TAG = "wsj--";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        ButterKnife.bind(this);
        publicBtn.setText("Collect操作符");
    }

    @OnClick(R.id.public_btn)
    public void OnClick(View view) {
        Collect();
    }

    private void Collect() {
        Observable.just(1, 2, 3, 4, 5).collect(new Callable<ArrayList<Integer>>() {
            // 1. 创建数据结构（容器），用于收集被观察者发送的数据
            @Override
            public ArrayList<Integer> call() throws Exception {
                return new ArrayList<>();
            }
            // 2. 对发送的数据进行收集
        }, new BiConsumer<ArrayList<Integer>, Integer>() {
            @Override
            public void accept(@NonNull ArrayList<Integer> list, @NonNull Integer integer) throws Exception {
                // 参数说明：list = 容器，integer = 后者数据
                list.add(integer);
                // 对发送的数据进行收集
            }
        }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(@NonNull ArrayList<Integer> s) throws Exception {
                Log.e(TAG, "本次发送的数据是： " + s);
            }
        });
    }
}
