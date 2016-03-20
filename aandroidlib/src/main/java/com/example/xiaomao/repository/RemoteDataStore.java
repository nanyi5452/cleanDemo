package com.example.xiaomao.repository;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.coreDomain.DisplayEntry;
import com.example.xiaomao.dataFetcher.DataFetcher;
import com.example.xiaomao.dataFetcher.DataFetcherImpl;
import com.example.xiaomao.exceptions.NetworkConnectionException;
import com.example.xiaomao.interactor.GetEntriesUseCase;
import com.example.xiaomao.interactor.GetEntryUseCase;
import com.example.xiaomao.interactor.ReturnResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by nanyi545 on 16-3-18.
 *
 * DataStore implementation
 */
public class RemoteDataStore implements DataStore {

    // constructor : pass in DataFetcher which uses mock net-api
    public RemoteDataStore(Context appContext){
        this.appContext=appContext;
        this.fetcher=new DataFetcherImpl(this.appContext);
    }

    // constructor : pass in DataFetcher which uses real net-api
    public RemoteDataStore(Context appContext,DataFetcher fetcher){
        this.appContext=appContext;
        this.fetcher=fetcher;
    }


    private final Context appContext;
    private final DataFetcher fetcher;


    /**
     *  Checks if the device has any active internet connection.------> not here
     *
     *  @return true device with internet connection, otherwise false.
     */
    public boolean isThereInternetConnection() {
        boolean isConnected=true;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());
        return isConnected;
    }



    @Override
    public Observable<? extends ReturnResult> getEntriesObs() {
        Observable<? extends ReturnResult> observable=Observable.create(new Observable.OnSubscribe<ReturnResult>(){
            @Override
            public void call(Subscriber<? super ReturnResult> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        List<DisplayEntry> entries=fetcher.getEntries();
                        List<Drawable> imageList=new ArrayList();
                        if (entries!=null){
                            Iterator<DisplayEntry> iterator=entries.iterator();
                            while(iterator.hasNext()){
                                imageList.add(fetcher.getImageFromURL(iterator.next().getImageURL()));
                            }
                        }
                        subscriber.onNext(new GetEntriesUseCase.EntriesResult(entries,imageList));
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        e.printStackTrace();
                        subscriber.onError(new NetworkConnectionException());
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException());
                }
                Log.i("AAA","in observable Call in thread:"+Thread.currentThread().getName());
            }
        });
        Log.i("AAA","getEntriesObs in thread:"+Thread.currentThread().getName());
        return observable;
    }

    @Override
    public Observable<? extends ReturnResult> getEntryObs(final int entryId) {
        Observable<? extends ReturnResult> observable=Observable.create(new Observable.OnSubscribe<ReturnResult>() {
            @Override
            public void call(Subscriber<? super ReturnResult> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        DisplayEntry entry=fetcher.getEntry(entryId);
                        Drawable d1=fetcher.getImageFromURL(entry.getImageURL());
                        ReturnResult ret=new GetEntryUseCase.EntryReturn(entry,d1);
                        subscriber.onNext(ret);
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        e.printStackTrace();
                        subscriber.onError(new NetworkConnectionException());
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException());
                }
            }
        });
        return observable;
    }


    @Override
    public Observable<? extends ReturnResult> deleteEntryObs(final int entryId) {
        Observable<? extends ReturnResult> observable=Observable.create(new Observable.OnSubscribe<ReturnResult>(){

            @Override
            public void call(Subscriber<? super ReturnResult> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        subscriber.onNext(fetcher.deleteEntry(entryId));
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        e.printStackTrace();
                        subscriber.onError(new NetworkConnectionException());
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException());
                }

            }

        });
        return observable;
    }

    @Override
    public Observable<Drawable> getImageFromURL(final String URL) {
        Observable<Drawable> obser=Observable.create(new Observable.OnSubscribe<Drawable>(){
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        subscriber.onNext(fetcher.getImageFromURL(URL));
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        e.printStackTrace();
                        subscriber.onError(new NetworkConnectionException());
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException());
                }
            }
        });
        return obser;
    }
}
