package com.example.administrator.rxjava_learning.RetrofitRxJava;

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

/**
 * Created by Administrator on 2018/2/28.
 */

public class RxJavafixRetrofit2 extends Activity {
    @BindView(R.id.public_btn)
    Button publicBtn;
    @BindView(R.id.public_txt)
    TextView publicTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        ButterKnife.bind(this);
        publicBtn.setText("网络的合并操作");
    }

    @OnClick(R.id.public_btn)
    public void OnClick(View view) {

    }
}
