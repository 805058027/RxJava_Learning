package com.example.administrator.rxjava_learning.BasisOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.rxjava_learning.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 快速创建1个被观察者对象（Observable）
 * 发送事件的特点：直接发送 传入的集合List数据
 * 会将数组中的数据转换为Observable对象
 */

public class FromIterableActivity extends Activity {
    @BindView(R.id.public_btn)
    Button publicBtn;
    @BindView(R.id.public_txt)
    TextView publicTxt;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        ButterKnife.bind(this);
        publicBtn.setText("FromIterable操作符");
        mList.add("1");
        mList.add("2");
        mList.add("3");
        mList.add("4");
    }

    @OnClick(R.id.public_btn)
    public void OnClick(View view) {
        Observable.fromIterable(mList).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                publicTxt.append("onSubscribe--集合遍历--");
            }

            @Override
            public void onNext(String s) {
                publicTxt.append(s + "--");
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
