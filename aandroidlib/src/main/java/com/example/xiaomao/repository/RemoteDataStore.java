package com.example.xiaomao.repository;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.coreDomain.DisplayEntry;
import com.example.interactor.ReturnResult;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by nanyi545 on 16-3-18.
 *
 * DataStore
 */
public class RemoteDataStore implements DataStore {

    // constructor : pass in fake net-api
    public RemoteDataStore(Context appContext){
        this.appContext=appContext;
        this.fetcher=new DataFetcherImpl(this.appContext);
    }

    // constructor : pass in real net-api
    public RemoteDataStore(Context appContext,DataFetcher fetcher){
        this.appContext=appContext;
        this.fetcher=fetcher;
    }


    private final Context appContext;
    private final DataFetcher fetcher;


    @Override
    public Observable<List<DisplayEntry>> getEntriesObs() {

        Observable<List<DisplayEntry>> observable=Observable.create(new Observable.OnSubscribe<List<DisplayEntry>>(){
            @Override
            public void call(Subscriber<? super List<DisplayEntry>> subscriber) {
                subscriber.onNext(fetcher.getEntries());
            }
        });
        return observable;

    }

    @Override
    public Observable<DisplayEntry> getEntryObs(int entryId) {
        return null;
    }

    @Override
    public Observable<ReturnResult> deleteEntryObs(int entryId) {
        return null;
    }

    @Override
    public Observable<Drawable> getImageFromURL(String URL) {
        return null;
    }
}
