package com.admin.rxjavademo;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    Button bu,bu2;
    TextView tv;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bu = (Button) findViewById(R.id.main_bu);
        tv = (TextView) findViewById(R.id.main_tv);
        bu2 = (Button) findViewById(R.id.main_bu2);
        iv = (ImageView) findViewById(R.id.main_iv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        bu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show2();
            }
        });
    }
    int draw = R.drawable.asd;
    private void show2() {
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getTheme().getDrawable(draw);
                }
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())// 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {//抛出异常
                Log.i("msg",e+"");
            }

            @Override
            public void onNext(Drawable drawable) {//显示视图
                iv.setImageDrawable(drawable);
            }
        });
    }

    private void show() {
        final String[] name = {"asdf","mmm","a","b"};
        Observable.from(name).subscribe(new Action1<String>() {//使用from遍历
            @Override
            public void call(String s) {
                Log.i("msg",s);
            }
        });
    }
}
