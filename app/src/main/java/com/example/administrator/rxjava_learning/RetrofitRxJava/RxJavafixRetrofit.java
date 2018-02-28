package com.example.administrator.rxjava_learning.RetrofitRxJava;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.rxjava_learning.R;
import com.example.administrator.rxjava_learning.model.hotkey;
import com.example.administrator.rxjava_learning.net.HttpUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * （无条件）网络请求轮询
 */

public class RxJavafixRetrofit extends Activity {
    @BindView(R.id.public_btn)
    Button publicBtn;
    @BindView(R.id.public_txt)
    TextView publicTxt;
    private int i = 0;
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);
        ButterKnife.bind(this);
        publicBtn.setText("无条件网络请求");
    }

    @OnClick(R.id.public_btn)
    public void OnClick(View view) {
        /*
         * 步骤1：采用interval（）延迟发送
         * 注：此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（）即可
         **/
        Observable.interval(2, 3, TimeUnit.SECONDS).
                 /*
                  * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                  * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                  **/
                         doOnNext(new Consumer<Long>() {
                     @Override
                     public void accept(@NonNull Long aLong) throws Exception {
                         Log.d("wsj--", "第 " + aLong + " 次轮询");
                         Observable<hotkey> observable = HttpUtils.getInstance().getCall();
                         observable.subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                                 .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<hotkey>() {
                             @Override
                             public void onSubscribe(Disposable d) {
                                 disposable = d;
                             }

                             @Override
                             public void onNext(hotkey hotkey) {
                                 publicTxt.setText(hotkey.getData().get(i).getName());
                                 i++;
                                 if (i > 8) {
                                     i = 0;
                                 }
                             }

                             @Override
                             public void onError(Throwable e) {

                             }

                             @Override
                             public void onComplete() {

                             }
                         });
                     }
                 }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        //切断观察者与被观察者关系
        disposable.dispose();
        super.onDestroy();
    }
}
