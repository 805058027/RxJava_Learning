package com.example.administrator.rxjava_learning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.rxjava_learning.RetrofitRxJava.CheckActivity;
import com.example.administrator.rxjava_learning.RetrofitRxJava.JointJudgmentActivity;
import com.example.administrator.rxjava_learning.RetrofitRxJava.RxJavafixRetrofit;
import com.example.administrator.rxjava_learning.RetrofitRxJava.RxJavafixRetrofit1;
import com.example.administrator.rxjava_learning.RetrofitRxJava.RxJavafixRetrofit2;
import com.example.administrator.rxjava_learning.RetrofitRxJava.RxJavafixRetrofit3;
import com.example.administrator.rxjava_learning.RetrofitRxJava.RxJavafixRetrofit4;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 操作符的实战操作
 */

public class PracticeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7})
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
            case R.id.btn4:
                startActivity(new Intent(this, RxJavafixRetrofit3.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(this, CheckActivity.class));
                break;
            case R.id.btn6:
                startActivity(new Intent(this, JointJudgmentActivity.class));
                break;
            case R.id.btn7:
                startActivity(new Intent(this, RxJavafixRetrofit4.class));
                break;
        }
    }
}
