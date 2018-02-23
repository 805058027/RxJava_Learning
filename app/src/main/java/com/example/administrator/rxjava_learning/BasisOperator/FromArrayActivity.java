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
 * 快速创建1个被观察者对象（Observable）
 * 发送事件的特点：直接发送 传入的数组数据
 * 会将数组中的数据转换为Observable对象
 */

public class FromArrayActivity extends Activity {

    @BindView(R.id.public_btn)
    Button publicBtn;
    @BindView(R.id.public_txt)
    TextView publicTxt;
    //数组
    private Integer integer[] = {1, 2, 3, 4, 5, 6, 7};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        ButterKnife.bind(this);
        publicBtn.setText("fromArray操作符");
    }

    @OnClick(R.id.public_btn)
    public void OnClick(View view) {
        Observable.fromArray(integer).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                publicTxt.append("onSubscribe--数组遍历--");
            }

            @Override
            public void onNext(Integer integer) {
                publicTxt.append(integer + "--");
            }

            @Override
            public void onError(Throwable e) {
                publicTxt.append("onError--" + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                publicTxt.append("--onComplete");
            }
        });
    }
}
