package com.example.administrator.rxjava_learning.ChangeOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.rxjava_learning.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 数据类型转换
 * 下面以将 使用Map（） 将事件的参数从 整型 变换成 字符串类型 为例子说明
 */

public class MapActivity extends Activity {
    @BindView(R.id.public_btn)
    Button publicBtn;
    @BindView(R.id.public_txt)
    TextView publicTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        ButterKnife.bind(this);
        publicBtn.setText("Map操作符");
    }

    @OnClick(R.id.public_btn)
    public void OnClick(View view) {
        // 采用RxJava基于事件流的链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 1. 被观察者发送事件 = 参数为整型 = 1、2、3、4、5
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onNext(5);
                e.onComplete();
            }
            // 2. 使用Map变换操作符中的Function函数对被观察者发送的事件进行统一变换：整型变换成字符串类型
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "使用 Map变换操作符 将事件" + integer + "的参数从 整型" + integer + " 变换成 字符串类型" + integer;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                publicTxt.append(s);
                System.out.println("wsj--" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * 观察者不同
     */
    private void easy() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onNext(5);
                e.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "使用 Map变换操作符 将事件" + integer + "的参数从 整型" + integer + " 变换成 字符串类型" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                publicTxt.append(s);
                System.out.println("wsj--" + s);
            }
        });
    }
}
