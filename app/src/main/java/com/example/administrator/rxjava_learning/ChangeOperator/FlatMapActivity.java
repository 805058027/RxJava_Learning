package com.example.administrator.rxjava_learning.ChangeOperator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.rxjava_learning.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 作用：将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送
 * <p>
 * 1.为事件序列中每个事件都创建一个 Observable 对象；
 * 2.将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
 * 3.将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
 * 4.新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）
 * <p>
 * 注：新合并生成的事件序列顺序是无序的，即 与旧序列发送事件的顺序无关
 */

public class FlatMapActivity extends Activity {

    @BindView(R.id.easy_btn)
    Button easyBtn;
    @BindView(R.id.easy_btn1)
    Button easyBtn1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        ButterKnife.bind(this);
        easyBtn.setText("FlatMap操作符");
        easyBtn1.setText("ConcatMap操作符");
    }

    @OnClick({R.id.easy_btn, R.id.easy_btn1})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.easy_btn:
                flatMap();
                break;
            case R.id.easy_btn1:
                ConcatMap();
                break;
        }

    }

    private void flatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                    // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("wsj--" + s);
            }
        });
    }

    /**
     * 与flatMap类似,传递顺序是有序的
     */
    private void ConcatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                    // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("wsj--" + s);
            }
        });
    }
}
