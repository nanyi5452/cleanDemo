package com.example.xiaomao.repository;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.coreDomain.DisplayEntry;
import com.example.interactor.ReturnResult;
import com.example.xiaomao.net.FakeAPIconnection;
import com.example.xiaomao.net.RestApi;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 16-3-18.
 */
public class RemoteDataStore implements DataStore {

    public RemoteDataStore(Context appContext){
        this.appContext=appContext;
        this.restApi=new FakeAPIconnection(this.appContext);
    }

    private final Context appContext;
    private final RestApi restApi;


    @Override
    public Observable<List<DisplayEntry>> getEntriesObs() {
        return null;
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
