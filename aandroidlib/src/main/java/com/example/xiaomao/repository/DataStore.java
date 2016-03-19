package com.example.xiaomao.repository;

import android.graphics.drawable.Drawable;

import com.example.coreDomain.DisplayEntry;
import com.example.interactor.ReturnResult;

import java.util.List;

import rx.Observable;

/**
 * Created by nanyi545 on 16-3-18.
 *
 * interface defining data store behaviours
 *
 */
public interface DataStore {

    Observable<List<DisplayEntry>> getEntriesObs();

    Observable<DisplayEntry> getEntryObs(int entryId);

    Observable<? extends ReturnResult> deleteEntryObs(int entryId);

    Observable<Drawable> getImageFromURL(String URL);

}
