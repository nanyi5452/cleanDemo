package com.example.xiaomao.interactor;

import android.graphics.drawable.Drawable;

import com.example.coreDomain.DisplayEntry;
import com.example.executor.PostExecutionThread;
import com.example.executor.ThreadExecutor;
import com.example.xiaomao.repository.DataStore;

import rx.Observable;

/**
 * Created by Administrator on 16-3-18.
 */
public class GetEntryUseCase extends UseCase {

    private final DataStore mDataStore;
    private final int targetEntryId;

    public static class EntryReturn implements ReturnResult{
        private final boolean success;
        private final DisplayEntry entry;
        private Drawable logo;


        // successful return
        public EntryReturn(DisplayEntry entry,Drawable logo){
            this.success=true;
            this.entry=entry;
            this.logo=logo;
        }

        public DisplayEntry getRetEntry() {
            return entry;
        }
        public Drawable getRetImage() {
            return logo;
        }
        @Override
        public boolean isSuccess() {
            return success;
        }
    }

    public GetEntryUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,DataStore mDataStore,int targetEntryId) {
        super(threadExecutor, postExecutionThread);
        this.mDataStore=mDataStore;
        this.targetEntryId=targetEntryId;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return mDataStore.getEntryObs(targetEntryId);
    }
}
