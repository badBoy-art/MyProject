package com.rxjava.study;

import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

import java.util.concurrent.TimeUnit;

/**
 * @author xuedui.zhao
 * @create 2018-05-30
 */
public class RxjavaStudy {
    
    public static final Func2<Integer, Integer,Integer> INTEGER_SUM =
            (integer, integer2) -> integer + integer2;
    
    public static final Func1<Observable<Integer>, Observable<Integer>> WONDOW_SUM =
            window -> window.scan(0, INTEGER_SUM).skip(3);
    
    public static final Func1<Observable<Integer>,Observable<Integer>> INNER_BUCKEF_SUM =
            integerObservable -> integerObservable.reduce(0, INTEGER_SUM);

    @Test
    public void test01() {
        Observable<String> myObervable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("Hello World jx");
                        subscriber.onCompleted();
                    }
                }
        );

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

        myObervable.subscribe(mySubscriber);
    }

    @Test
    public void test02() {
        Observable.just("Hello World")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    @Test
    public void test03() {
        Observable.just("Hello World")
                .map(s -> s+"-Dan")
                .subscribe(s -> System.out.println(s));
    }

    @Test
    public void test04() {
        Observable.just("Hello World")
                .map(s->s.hashCode())
                .map(i->Integer.toString(i))
                .subscribe(s->System.out.println(s));
    }

    @Test
    public void RollingWindowTest() {
        try {
            PublishSubject<Integer> publishSubject = PublishSubject.create();
            SerializedSubject<Integer,Integer> serializedSubject = publishSubject.toSerialized();

            serializedSubject
                    .window(5, TimeUnit.SECONDS)  // 5秒作为一个基本块
                    .flatMap(INNER_BUCKEF_SUM)             // 基本块内数据求和
                    .window(3,1)               // 3个块作为一个窗口，滚动布数为1
                    .flatMap(WONDOW_SUM)                  // 窗口数据求和

                    .subscribe((Integer integer) ->
                            System.out.println(
                                    Thread.currentThread().getName() + "---" + integer));
            // 缓慢发送数据，观察效果
            for (int i=0; i<100; ++i) {
                if (i < 30) {
                    serializedSubject.onNext(1);
                } else {
                    serializedSubject.onNext(2);
                }
                Thread.sleep(300);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
