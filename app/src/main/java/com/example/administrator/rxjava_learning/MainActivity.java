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
import com.example.administrator.rxjava_learning.CombinationOperator.CollectActivity;
import com.example.administrator.rxjava_learning.CombinationOperator.CombineLatestActivity;
import com.example.administrator.rxjava_learning.CombinationOperator.ConcatActivity;
import com.example.administrator.rxjava_learning.CombinationOperator.CountActivity;
import com.example.administrator.rxjava_learning.CombinationOperator.MergeActivity;
import com.example.administrator.rxjava_learning.CombinationOperator.ReduceActivity;
import com.example.administrator.rxjava_learning.CombinationOperator.StartWithActivity;
import com.example.administrator.rxjava_learning.CombinationOperator.ZipActivity;
import com.example.administrator.rxjava_learning.CombinationOperator.concatDelayErrorActivity;
import com.example.administrator.rxjava_learning.FunctionalityOperator.DelayActivity;
import com.example.administrator.rxjava_learning.FunctionalityOperator.DoActivity;
import com.example.administrator.rxjava_learning.FunctionalityOperator.ErrorActivity;
import com.example.administrator.rxjava_learning.FunctionalityOperator.RetryActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
//https://github.com/805058027/RxJava_Learning/tree/master/app/pic/do.png
    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8,
            R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16,
            R.id.btn17, R.id.btn18, R.id.btn19, R.id.btn20, R.id.btn21, R.id.btn22, R.id.btn23, R.id.btn24})
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
                startActivity(new Intent(this, ConcatActivity.class));
                break;
            case R.id.btn13:
                startActivity(new Intent(this, MergeActivity.class));
                break;
            case R.id.btn14:
                startActivity(new Intent(this, concatDelayErrorActivity.class));
                break;
            case R.id.btn15:
                startActivity(new Intent(this, ZipActivity.class));
                break;
            case R.id.btn16:
                startActivity(new Intent(this, CombineLatestActivity.class));
                break;
            case R.id.btn17:
                startActivity(new Intent(this, ReduceActivity.class));
                break;
            case R.id.btn18:
                startActivity(new Intent(this, CollectActivity.class));
                break;
            case R.id.btn19:
                startActivity(new Intent(this, StartWithActivity.class));
                break;
            case R.id.btn20:
                startActivity(new Intent(this, CountActivity.class));
                break;
            case R.id.btn21:
                startActivity(new Intent(this, DelayActivity.class));
                break;
            case R.id.btn22:
                startActivity(new Intent(this, DoActivity.class));
                break;
            case R.id.btn23:
                startActivity(new Intent(this, ErrorActivity.class));
                break;
            case R.id.btn24:
                startActivity(new Intent(this, RetryActivity.class));
                break;
            case R.id.btn25:
                startActivity(new Intent(this, RetryActivity.class));
                break;
        }
    }
}
