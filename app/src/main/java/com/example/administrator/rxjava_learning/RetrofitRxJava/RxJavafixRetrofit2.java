package com.example.administrator.rxjava_learning.RetrofitRxJava;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.rxjava_learning.R;
import com.example.administrator.rxjava_learning.model.login;
import com.example.administrator.rxjava_learning.model.register;
import com.example.administrator.rxjava_learning.net.HttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 网络的合并操作
 * 先调用注册接口  在调用登录接口。链式操作
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
        //注册
        Observable<register> observable1 = HttpUtils.getInstance().getRegister("12318901", "1238909", "1238909");
        //登录
        final Observable<login> observable2 = HttpUtils.getInstance().getLogin("12318901", "1238909");
        observable1.subscribeOn(Schedulers.newThread())//被观察者的网络请求在子线程中进行
                .observeOn(AndroidSchedulers.mainThread()).//观察者在主线程中进行UI操作
                doOnNext(new Consumer<register>() {
            @Override
            public void accept(@NonNull register register) throws Exception {
                System.out.println("注册信息1" + register.getErrorMsg());
            }
        }).observeOn(Schedulers.io())//切换到io线程
                .flatMap(new Function<register, ObservableSource<login>>() {
                    @Override
                    public ObservableSource<login> apply(@NonNull register register) throws Exception {
                        // 将网络请求1转换成网络请求2，即发送网络请求2
                        return observable2;
                    }
                }).observeOn(AndroidSchedulers.mainThread())//切换回主线程
                .subscribe(new Consumer<login>() {
                    @Override
                    public void accept(@NonNull login login) throws Exception {
                        System.out.println("登录信息2" + login.getErrorMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        System.out.println("登录失败");
                    }
                });
    }
}
