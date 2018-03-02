package com.example.administrator.rxjava_learning.RetrofitRxJava;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.rxjava_learning.R;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

/**
 * 联合判断多个事件
 */

public class JointJudgmentActivity extends Activity {
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.age)
    EditText age;
    @BindView(R.id.job)
    EditText job;
    @BindView(R.id.list)
    Button list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joint_judgment);
        ButterKnife.bind(this);
        CheckDate();
    }

    @OnClick(R.id.list)
    public void OnClick(View view) {
        Toast.makeText(getApplicationContext(), "1111", Toast.LENGTH_SHORT).show();
    }

    /**
     * 联合判断--检测数据
     */
    private void CheckDate() {
        //1. 此处采用了RxBinding：RxTextView.textChanges(name) = 对对控件数据变更进行监听（功能类似TextWatcher）
        //2. 传入EditText控件，点击任1个EditText撰写时，都会发送数据事件 = Function3（）的返回值（下面会详细说明）
        //3. 采用skip(1)原因：跳过 一开始EditText无任何输入时的空值
        Observable<CharSequence> nameObservable = RxTextView.textChanges(name).skip(1);
        Observable<CharSequence> ageObservable = RxTextView.textChanges(age).skip(1);
        Observable<CharSequence> jobObservable = RxTextView.textChanges(job).skip(1);

        Observable.combineLatest(nameObservable, ageObservable, jobObservable, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(@NonNull CharSequence charSequence, @NonNull CharSequence charSequence2, @NonNull CharSequence charSequence3) throws Exception {
                // 1. 姓名信息
                boolean isUserNameValid = !TextUtils.isEmpty(name.getText());
                // 除了设置为空，也可设置长度限制
                // boolean isUserNameValid = !TextUtils.isEmpty(name.getText()) && (name.getText().toString().length() > 2 && name.getText().toString().length() < 9);
                // 2. 年龄信息
                boolean isUserAgeValid = !TextUtils.isEmpty(age.getText());
                // 3. 职业信息
                boolean isUserJobValid = !TextUtils.isEmpty(job.getText());
                /*
                 * 步骤5：返回信息 = 联合判断，即3个信息同时已填写，"提交按钮"才可点击
                 **/
                return isUserNameValid && isUserAgeValid && isUserJobValid;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                Log.e("wsj-", "提交按钮是否可点击： " + aBoolean);
                list.setEnabled(aBoolean);
            }
        });
    }
}
