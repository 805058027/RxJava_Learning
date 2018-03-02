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
import com.example.administrator.rxjava_learning.model.register;
import com.example.administrator.rxjava_learning.net.HttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 数据的合并操作
 */

public class RxJavafixRetrofit3 extends Activity {
    @BindView(R.id.easy_btn)
    Button easyBtn;
    @BindView(R.id.easy_txt)
    TextView easyTxt;
    @BindView(R.id.easy_btn1)
    Button easyBtn1;
    @BindView(R.id.easy_txt1)
    TextView easyTxt1;
    private String result = "数据源来自 = ", TAG = "wsj";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        ButterKnife.bind(this);
        easyBtn.setText("网络+本地 数据合并展示");
        easyBtn1.setText("网络+网络 两个不同接口的数据合并展示");
    }

    @OnClick({R.id.easy_btn, R.id.easy_btn1})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.easy_btn:
                Merge();
                break;
            case R.id.easy_btn1:
                Zip();
                break;
        }
    }

    private void Merge() {
        //设置第1个Observable：通过网络获取数据此处仅作网络请求的模拟
        Observable<String> network = Observable.just("网络");
        // 设置第2个Observable：通过本地文件获取数据此处仅作本地文件请求的模拟
        Observable<String> file = Observable.just("本地文件");
        //通过merge（）合并事件 & 同时发送事件
        Observable.merge(network, file)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        Log.d(TAG, "数据源有： " + value);
                        result += value + "+";
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    // 接收合并事件后，统一展示
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "获取数据完成");
                        Log.d(TAG, result);
                        easyTxt.setText(result);
                    }
                });
    }

    private void Zip() {
        //玩Android注册接口--在新的子线程里操作
        Observable<register> observable1 = HttpUtils.getInstance().getRegister("12318901", "1238909", "1238909")
                .subscribeOn(Schedulers.newThread());
        //获取检索字段--在新的子线程里操作
        Observable<hotkey> observable2 = HttpUtils.getInstance().getCall().subscribeOn(Schedulers.newThread());
        Observable.zip(observable1, observable2, new BiFunction<register, hotkey, String>() {
            @Override
            public String apply(@NonNull register register, @NonNull hotkey hotkey) throws Exception {
                return register.getErrorMsg() + "---" + hotkey.getData().get(0).getName();
            }
        }).observeOn(AndroidSchedulers.mainThread())//切换回主线程修改UI
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        easyTxt1.setText(s);
                    }
                });
    }
}
