package com.example.administrator.rxjava_learning.FunctionalityOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.rxjava_learning.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by Administrator on 2018/2/27.
 */

public class RetryActivity extends Activity {


    @BindView(R.id.retry_btn1)
    Button retryBtn1;
    @BindView(R.id.retry_btn2)
    Button retryBtn2;
    @BindView(R.id.retry_btn3)
    Button retryBtn3;
    private String TAG = "wsj---";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retry);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.retry_btn1, R.id.retry_btn2, R.id.retry_btn3})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.error_btn1:
                retry1();
                break;
            case R.id.error_btn2:
                retryUntil();
                break;
            case R.id.error_btn3:
                break;
        }
    }

    /**
     * 作用:重试，即当出现错误时，让被观察者（Observable）重新发射数据
     * 接收到 onError（）时，重新订阅 & 发送事件
     * Throwable 和 Exception都可拦截
     */
    private void retry1() {
        // 作用：出现错误时，让被观察者重新发送数据
        // 注：若一直错误，则一直重新发送
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }// 遇到错误时，让被观察者重新发射数据（若一直错误，则一直重新发送
        }).retry().subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "接收到了事件" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    /**
     * retry（long time）
     * 作用：出现错误时，让被观察者重新发送数据（具备重试次数限制)
     * 参数 = 重试次数
     */
    private void retry2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
                .retry(3) // 设置重试次数 = 3次
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     * retry（Predicate predicate）
     * 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
     * 参数 = 判断逻辑
     */
    private void retry3() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
                // 拦截错误后，判断是否需要重新发送请求
                .retry(new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        // 捕获异常
                        Log.e(TAG, "retry错误: " + throwable.toString());
                        //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                        //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                        return true;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     * retry（new BiPredicate<Integer, Throwable>）
     * 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送 & 持续遇到错误，则持续重试
     * 参数 =  判断逻辑（传入当前重试次数 & 异常错误信息）
     */
    private void retry4() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })

                // 拦截错误后，判断是否需要重新发送请求
                .retry(new BiPredicate<Integer, Throwable>() {
                    @Override
                    public boolean test(@NonNull Integer integer, @NonNull Throwable throwable) throws Exception {
                        // 捕获异常
                        Log.e(TAG, "异常错误 =  " + throwable.toString());
                        // 获取当前重试次数
                        Log.e(TAG, "当前重试次数 =  " + integer);
                        //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                        //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                        return true;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     * retry（long time,Predicate predicate）
     * 作用：出现错误后，判断是否需要重新发送数据（具备重试次数限制)
     * 参数 = 设置重试次数 & 判断逻辑
     */
    private void retry5() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
                // 拦截错误后，判断是否需要重新发送请求
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        // 捕获异常
                        Log.e(TAG, "retry错误: " + throwable.toString());
                        //返回false = 不重新重新发送数据 & 调用观察者的onError（）结束
                        //返回true = 重新发送请求（最多重新发送3次）
                        return true;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     * 作用
     * 出现错误后，判断是否需要重新发送数据
     * 1.若需要重新发送 & 持续遇到错误，则持续重试
     * 2.作用类似于retry（Predicate predicate）
     * 唯一区别：返回 true 则不重新发送数据事件。
     */
    private void retryUntil() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
                // 拦截错误后，判断是否需要重新发送请求
                .retryUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() throws Exception {
                        //返回true= 不重新重新发送数据 & 调用观察者的onError（）结束
                        //返回false = 重新发送请求（最多重新发送3次）
                        return false;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     * 遇到错误时，将发生的错误传递给一个新的被观察者（Observable），
     * 并决定是否需要重新订阅原始被观察者（Observable）& 发送事件
     */
    private void retryWhen() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
                // 遇到error事件才会回调
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {

                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                        // 返回Observable<?> = 新的被观察者 Observable（任意类型）
                        // 此处有两种情况：
                        // 1. 若 新的被观察者 Observable发送的事件 = Error事件，那么 原始Observable则不重新发送事件：
                        // 2. 若 新的被观察者 Observable发送的事件 = Next事件 ，那么原始的Observable则重新发送事件：
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {

                                // 1. 若返回的Observable发送的事件 = Error事件，则原始的Observable不重新发送事件
                                // 该异常错误信息可在观察者中的onError（）中获得
                                return Observable.error(new Throwable("retryWhen终止啦"));

                                // 2. 若返回的Observable发送的事件 = Next事件，则原始的Observable重新发送事件（若持续遇到错误，则持续重试）
                                // return Observable.just(1);
                            }
                        });

                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应" + e.toString());
                        // 获取异常错误信息
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });

    }
}
