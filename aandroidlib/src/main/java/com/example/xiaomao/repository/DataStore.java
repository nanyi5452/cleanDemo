package com.example.xiaomao.repository;

import android.graphics.drawable.Drawable;

import com.example.xiaomao.interactor.ReturnResult;

import rx.Observable;

/**
 * Created by nanyi545 on 16-3-18.
 *
 * interface defining repository behaviours(to get data needed for a particular use case),
 * the actual implementation(details of retrieving data) is in DataFetchers
 *
 * Repositories are provided to UseCases
 *
 */
public interface DataStore {

//    Observable<List<DisplayEntry>> getEntriesObs();
    Observable<? extends ReturnResult> getEntriesObs();

    Observable<? extends ReturnResult> getEntryObs(int entryId);

    Observable<? extends ReturnResult> deleteEntryObs(int entryId);

    Observable<Drawable> getImageFromURL(String URL);

}
