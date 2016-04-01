package com.example.xiaomao.aandroidlib;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.xiaomao.dataFetcher.DataFetcherImpl;
import com.example.xiaomao.interactor.GetEntriesUseCase;
import com.example.xiaomao.interactor.ReturnResult;
import com.example.xiaomao.repository.DataStore;
import com.example.xiaomao.repository.RemoteDataStore;
import com.example.xiaomao.utils.subscriber.DefaultSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 16-3-18.
 */
public class DataFetcherTest extends AndroidTestCase {

    final String tag="androidtest";

    public void testGetEntries(){
        Context context=getContext().getApplicationContext();
        Log.i(tag,"is context null?"+(context==null));
        DataFetcherImpl fetcher=new DataFetcherImpl(context);
        Log.i(tag, fetcher.getEntries().toString());
        Log.i(tag, fetcher.getEntry(10).toString());
        Log.i(tag, fetcher.getEntry(11).toString());
        Log.i(tag, fetcher.getEntry(13).toString());
    }


    /** check the caching behaviour  **/
    public void testGetEntriesCache(){
        Context context=getContext().getApplicationContext();
        DataFetcherImpl fetcher=new DataFetcherImpl(context);
        long t1=System.nanoTime();
        Log.i(tag, fetcher.getEntries().toString());
        long t2=System.nanoTime();
        Log.i(tag,"elapsed time in milli-sec:"+(t2-t1)/1000000f);

        long t3=System.nanoTime();
        Log.i(tag, fetcher.getEntries().toString());
        long t4=System.nanoTime();
        Log.i(tag,"elapsed time in milli-sec:"+(t4-t3)/1000000f);
    }


    // test--> subscribe----> doesn't work ---> why,   works in real activity
    public void testDataStore(){
        Context context=getContext().getApplicationContext();
        DataStore remote=new RemoteDataStore(context);
        remote.getEntriesObs().subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultSubscriber<ReturnResult>(){
            @Override
            public void onNext(ReturnResult entriesRET) {
                GetEntriesUseCase.EntriesResult ret=(GetEntriesUseCase.EntriesResult)entriesRET;
                Log.i("AAA","getEntriesObs--on next in:"+Thread.currentThread().getName()+"---"+ret.getEntries().toString());
            }
        });


    }


}
