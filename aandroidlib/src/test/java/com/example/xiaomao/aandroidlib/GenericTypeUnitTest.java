package com.example.xiaomao.aandroidlib;

import com.example.xiaomao.utils.subscriber.DefaultSubscriber;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class GenericTypeUnitTest {

    interface CanTalk{
        void talk();
    }

    class Dog implements CanTalk{
        @Override
        public void talk() {
            System.out.println("dog bark");
        }
    }
    class Cat implements CanTalk{
        @Override
        public void talk() {
            System.out.println("cat miao miao");
        }
    }



    @Test
    public void testBark() throws Exception {
        new Dog().talk();
    }


    @Test
    public void testGeneric() throws Exception {
        Observable<? extends CanTalk> abser=Observable.create(new Observable.OnSubscribe<CanTalk>() {
            @Override
            public void call(Subscriber<? super CanTalk> subscriber) {
                subscriber.onNext(new Dog());
                subscriber.onNext(new Cat());
            }
        });

        abser.subscribe(new  DefaultSubscriber<CanTalk>(){
            @Override
            public void onNext(CanTalk canTalk) {
                canTalk.talk();
            }
        });


    }



}