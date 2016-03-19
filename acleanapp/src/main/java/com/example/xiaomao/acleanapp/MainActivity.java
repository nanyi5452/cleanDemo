package com.example.xiaomao.acleanapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.example.coreDomain.DisplayEntry;
import com.example.interactor.DeleteEntryUseCase;
import com.example.interactor.ReturnResult;
import com.example.xiaomao.repository.DataStore;
import com.example.xiaomao.repository.RemoteDataStore;
import com.example.xiaomao.utils.subscriber.DefaultSubscriber;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ImageView iv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv1= (ImageView) findViewById(R.id.iv1);


        testFun1();


    }

    private void testFun1() {
        // test
        DataStore remote=new RemoteDataStore(getApplicationContext());
        remote.getEntriesObs().subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(Schedulers.computation()) .subscribe(new DefaultSubscriber<List<DisplayEntry>>(){
            @Override
            public void onNext(List<DisplayEntry> displayEntries) {
                Log.i("AAA","on next in:"+Thread.currentThread().getName()+"---"+displayEntries.toString());
            }
        });

        remote.getEntryObs(11).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(Schedulers.computation()) .subscribe(new DefaultSubscriber<DisplayEntry>(){
            @Override
            public void onNext(DisplayEntry displayEntries) {
                Log.i("AAA","on next in:"+Thread.currentThread().getName()+"---"+displayEntries.toString());
            }
        });

        remote.deleteEntryObs(13).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(Schedulers.computation()).subscribe(new  DefaultSubscriber<ReturnResult>(){
            @Override
            public void onNext(ReturnResult deleRet) {
                Log.i("AAA","on next in:"+Thread.currentThread().getName()+"--delete success--"+deleRet.isSuccess());

                if (deleRet.isSuccess()){
                    DeleteEntryUseCase.DeleteResult ret=(DeleteEntryUseCase.DeleteResult)deleRet;
                    Log.i("AAA","on next in:"+Thread.currentThread().getName()+"--list after delete--"+ret.getReturnList().toString());
                }
            }
        });

        remote.getImageFromURL("11").subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new  DefaultSubscriber<Drawable>(){
            @Override
            public void onNext(Drawable drawable) {
                iv1.setImageDrawable(drawable);
            }
        });


    }
}
