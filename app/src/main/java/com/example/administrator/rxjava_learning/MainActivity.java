package com.example.administrator.rxjava_learning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.rxjava_learning.BasisOperator.DeferActivity;
import com.example.administrator.rxjava_learning.BasisOperator.FromArrayActivity;
import com.example.administrator.rxjava_learning.BasisOperator.FromIterableActivity;
import com.example.administrator.rxjava_learning.BasisOperator.IntervalActivity;
import com.example.administrator.rxjava_learning.BasisOperator.JustActivity;
import com.example.administrator.rxjava_learning.BasisOperator.RangActivity;
import com.example.administrator.rxjava_learning.BasisOperator.TimerActivity;
import com.example.administrator.rxjava_learning.ChangeOperator.BufferActivity;
import com.example.administrator.rxjava_learning.ChangeOperator.FlatMapActivity;
import com.example.administrator.rxjava_learning.ChangeOperator.MapActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8,
            R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(this, EasyActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, JustActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this, FromArrayActivity.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(this, FromIterableActivity.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(this, DeferActivity.class));
                break;
            case R.id.btn6:
                startActivity(new Intent(this, TimerActivity.class));
                break;
            case R.id.btn7:
                startActivity(new Intent(this, IntervalActivity.class));
                break;
            case R.id.btn8:
                startActivity(new Intent(this, RangActivity.class));
                break;
            case R.id.btn9:
                startActivity(new Intent(this, MapActivity.class));
                break;
            case R.id.btn10:
                startActivity(new Intent(this, FlatMapActivity.class));
                break;
            case R.id.btn11:
                startActivity(new Intent(this, BufferActivity.class));
                break;
            case R.id.btn12:
                startActivity(new Intent(this, RangActivity.class));
                break;
        }
    }
}
