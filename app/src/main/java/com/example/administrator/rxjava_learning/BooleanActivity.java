package com.example.administrator.rxjava_learning;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by Administrator on 2018/3/5.
 */

public class BooleanActivity extends Activity {
    @BindView(R.id.boolean_btn1)
    Button booleanBtn1;
    @BindView(R.id.boolean_btn2)
    Button booleanBtn2;
    @BindView(R.id.boolean_btn3)
    Button booleanBtn3;
    @BindView(R.id.boolean_btn4)
    Button booleanBtn4;
    private String TAG = "wsj--";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boolean);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.boolean_btn1, R.id.boolean_btn2, R.id.boolean_btn3, R.id.boolean_btn4, R.id.boolean_btn5,
            R.id.boolean_btn6, R.id.boolean_btn7, R.id.boolean_btn8, R.id.boolean_btn9, R.id.boolean_btn10})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.boolean_btn1:
                all();
                break;
            case R.id.boolean_btn2:
                takeWhile();
                break;
            case R.id.boolean_btn3:
                skipWhile();
                break;
            case R.id.boolean_btn4:
                takeUntil();
                break;
            case R.id.boolean_btn5:
                skipUntil();
                break;
            case R.id.boolean_btn6:
                SequenceEqual();
                break;
            case R.id.boolean_btn7:
                contains();
                break;
            case R.id.boolean_btn8:
                isEmpty();
                break;
            case R.id.boolean_btn9:
                amb();
                break;
            case R.id.boolean_btn10:
                contains();
                break;
        }
    }

    /**
     * all操作符
     * 作用:判断发送的每项数据是否都满足 设置的函数条件(所有数据都满足函数内条件)
     */
    private void all() {
        Observable.just(1, 2, 3, 4, 5).all(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer < 10;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                Log.e(TAG, aBoolean + "");
            }
        });
    }

    /**
     * 判断发送的每项数据是否满足 设置函数条件
     * 若发送的数据满足该条件，则发送该项数据；否则不发送
     */
    private void takeWhile() {
        Observable.just(1, 2, 3, 4, 5).takeWhile(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer < 4;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, integer + "");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 判断发送的每项数据是否满足 设置函数条件
     * 直到该判断条件 = false时，才开始发送Observable的数据
     */
    private void skipWhile() {
        // 1. 每隔1s发送1个数据 = 从0开始，每次递增1
        Observable.interval(1, TimeUnit.SECONDS)
                // 2. 通过skipWhile（）设置判断条件
                .just(1, 2, 3, 4, 5, 6, 7, 8, 9).skipWhile(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return (integer < 5);
                // 直到判断条件不成立 = false = 发射的数据≥5，才开始发送数据
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "发送了事件 " + value);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * 执行到某个条件时，停止发送事件
     */
    private void takeUntil() {
        // 1. 每1s发送1个数据 = 从0开始，递增1，即0、1、2、3
        Observable.interval(1, TimeUnit.SECONDS)
                // 2. 通过takeUntil的Predicate传入判断条件
                .takeUntil(new Predicate<Long>() {
                    @Override
                    public boolean test(Long integer) throws Exception {
                        return (integer > 3);
                        // 返回true时，就停止发送事件
                        // 当发送的数据满足>3时，就停止发送Observable的数据
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Long value) {
                Log.d(TAG, "发送了事件 " + value);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * 等到 skipUntil（） 传入的Observable开始发送数据，（原始）第1个Observable的数据才开始发送数据
     */
    private void skipUntil() {
        // （原始）第1个Observable：每隔1s发送1个数据 = 从0开始，每次递增1
        Observable.interval(1, TimeUnit.SECONDS)
                // 第2个Observable：延迟5s后开始发送1个Long型数据
                .skipUntil(Observable.timer(5, TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
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
     * 判定两个Observables需要发送的数据是否相同
     * 若相同，返回 true；否则，返回 false
     */
    private void SequenceEqual() {
        Observable.sequenceEqual(Observable.just(1, 2), Observable.just(1, 2))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        Log.d(TAG, "2个Observable是否相同：" + aBoolean);
                    }
                });
    }

    /**
     * 判断发送的数据中是否包含指定数据
     * 若包含，返回 true；否则，返回 false
     * 内部实现 = exists（）
     */
    private void contains() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .contains(4)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG, "result is " + aBoolean);
                        // 输出返回结果
                    }
                });
    }

    /**
     * 判断发送的数据是否为空
     * 若为空，返回 true；否则，返回 false
     */
    private void isEmpty() {
        Observable.just(1, 2, 3).isEmpty().subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                Log.d(TAG, "result is " + aBoolean);
                // 输出返回结果
            }
        });
    }

    /**
     * 当需要发送多个 Observable时，只发送 先发送数据的Observable的数据，而其余 Observable则被丢弃。
     */
    private void amb() {
        // 设置2个需要发送的Observable & 放入到集合中
        List<ObservableSource<Integer>> list = new ArrayList<>();
        // 第1个Observable延迟1秒发射数据
        list.add(Observable.just(1, 2, 3).delay(1, TimeUnit.SECONDS));
        // 第2个Observable正常发送数据
        list.add(Observable.just(4, 5, 6));
        // 一共需要发送2个Observable的数据
        // 但由于使用了amba（）,所以仅发送先发送数据的Observable
        // 即第二个（因为第1个延时了）
        Observable.amb(list).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "接收到了事件 " + integer);
            }
        });
    }

    /**
     * 在不发送任何有效事件（ Next事件）、仅发送了 Complete 事件的前提下，发送一个默认值
     */
    private void defaultIfEmpty() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 不发送任何有效事件
                //  e.onNext(1);
                //  e.onNext(2);

                // 仅发送Complete事件
                e.onComplete();
            }
        }).defaultIfEmpty(10) // 若仅发送了Complete事件，默认发送 值 = 10
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
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
}
