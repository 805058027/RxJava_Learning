package com.example.administrator.rxjava_learning.BasisOperator;

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
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Just操作符
 * 快速创建1个被观察者对象（Observable）
 * 发送事件的特点：直接发送 传入的事件
 * 注：最多只能发送10个参数
 */

public class JustActivity extends Activity {
    @BindView(R.id.public_txt)
    TextView justTxt;
    @BindView(R.id.public_btn)
    Button justBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        ButterKnife.bind(this);
        justBtn.setText("Just操作符");
    }

    @OnClick(R.id.public_btn)
    public void OnClick(View view) {
        Observable.just(1, 2, 3, 4, 5, 6).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                justTxt.append("onSubscribe--单个T类型--");
            }

            @Override
            public void onNext(Integer integer) {
                justTxt.append(integer + "--");
            }

            @Override
            public void onError(Throwable e) {
                justTxt.append("onError" + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                justTxt.append("--onComplete");
            }
        });
    }
}
