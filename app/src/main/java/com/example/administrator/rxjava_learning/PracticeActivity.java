package com.example.administrator.rxjava_learning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.administrator.rxjava_learning.RetrofitRxJava.RxJavafixRetrofit;
import com.example.administrator.rxjava_learning.RetrofitRxJava.RxJavafixRetrofit1;
import com.example.administrator.rxjava_learning.RetrofitRxJava.RxJavafixRetrofit2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 操作符的实战操作
 */

public class PracticeActivity extends Activity {
    @BindView(R.id.btn1)
    Button btn1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1,R.id.btn2,R.id.btn3})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(this, RxJavafixRetrofit.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, RxJavafixRetrofit1.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this, RxJavafixRetrofit2.class));
                break;
        }
    }
}
