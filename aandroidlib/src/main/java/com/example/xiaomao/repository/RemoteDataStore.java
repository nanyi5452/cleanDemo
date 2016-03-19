package com.example.xiaomao.repository;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.coreDomain.DisplayEntry;
import com.example.interactor.ReturnResult;
import com.example.xiaomao.exceptions.NetworkConnectionException;

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
    public Observable<List<DisplayEntry>> getEntriesObs() {
        Observable<List<DisplayEntry>> observable=Observable.create(new Observable.OnSubscribe<List<DisplayEntry>>(){
            @Override
            public void call(Subscriber<? super List<DisplayEntry>> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        subscriber.onNext(fetcher.getEntries());
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
    public Observable<DisplayEntry> getEntryObs(final int entryId) {
        Observable<DisplayEntry> observable=Observable.create(new Observable.OnSubscribe<DisplayEntry>() {
            @Override
            public void call(Subscriber<? super DisplayEntry> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        subscriber.onNext(fetcher.getEntry(entryId));
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
