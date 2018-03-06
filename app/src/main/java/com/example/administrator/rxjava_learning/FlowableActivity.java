package com.example.administrator.rxjava_learning;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 图文详解 背压策略
 * 缓存区默认大小128（超出后报异常）
 */

public class FlowableActivity extends Activity {
    @BindView(R.id.flowable_btn)
    Button flowableBtn;
    @BindView(R.id.flowable_btn1)
    Button flowableBtn1;
    private String TAG = "wsj--";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowable);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.flowable_btn, R.id.flowable_btn1, R.id.flowable_btn2, R.id.flowable_btn3})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.flowable_btn:
                BaseFlowable();
                break;
            case R.id.flowable_btn1:
                Flowable1();
                break;
            case R.id.flowable_btn2:
                mSubscription.request(2);
                break;
            case R.id.flowable_btn3:
                Flowable2();
                break;
        }
    }

    /**
     * Flowable的基础使用
     */
    private void BaseFlowable() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription
                        // 相同点：Subscription具备Disposable参数的作用，即Disposable.dispose()切断连接, 同样的调用Subscription.cancel()切断连接
                        // 不同点：Subscription增加了void request(long n)
                        Log.d(TAG, "onSubscribe");
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    /**
     * 观察者不接收事件的情况下，被观察者继续发送事件 & 存放到缓存区；再按需取出
     */
    private Subscription mSubscription; // 用于保存Subscription对象

    private void Flowable1() {
        // 1. 创建被观察者Flowable
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                // 一共发送4个事件
                Log.d(TAG, "发送事件 1");
                emitter.onNext(1);
                Log.d(TAG, "发送事件 2");
                emitter.onNext(2);
                Log.d(TAG, "发送事件 3");
                emitter.onNext(3);
                Log.d(TAG, "发送事件 4");
                emitter.onNext(4);
                Log.d(TAG, "发送完成");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()) // 设置被观察者在io线程中进行
                .observeOn(AndroidSchedulers.mainThread()) // 设置观察者在主线程中进行
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription
                        // 相同点：Subscription参数具备Disposable参数的作用，即Disposable.dispose()切断连接, 同样的调用Subscription.cancel()切断连接
                        // 不同点：Subscription增加了void request(long n)
                        mSubscription = s;
                        // 作用：决定观察者能够接收多少个事件
                        // 如设置了s.request(3)，这就说明观察者能够接收3个事件（多出的事件存放在缓存区）
                        // 官方默认推荐使用Long.MAX_VALUE，即s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    /**
     * 同步订阅中，被观察者 & 观察者工作于同1线程
     * 同步订阅关系中没有缓存区
     */
    private void Flowable2() {
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                // 被观察者发送事件数量 = 4个
                Log.d(TAG, "发送了事件1");
                emitter.onNext(1);
                Log.d(TAG, "发送了事件2");
                emitter.onNext(2);
                Log.d(TAG, "发送了事件3");
                emitter.onNext(3);
                Log.d(TAG, "发送了事件4");
                emitter.onNext(4);
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);

        /**
         * 步骤2：创建观察者 =  Subscriber
         */
        Subscriber<Integer> downstream = new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");
                s.request(3);
                // 观察者接收事件 = 3个 ，即不匹配
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "接收到了事件 " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        /**
         * 步骤3：建立订阅关系
         */
        upstream.subscribe(downstream);
    }
}
