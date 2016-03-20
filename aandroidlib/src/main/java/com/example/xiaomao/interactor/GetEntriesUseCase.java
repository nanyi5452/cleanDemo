package com.example.xiaomao.interactor;

import android.graphics.drawable.Drawable;

import com.example.coreDomain.DisplayEntry;
import com.example.executor.PostExecutionThread;
import com.example.executor.ThreadExecutor;
import com.example.xiaomao.repository.DataStore;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 16-3-19.
 */
public class GetEntriesUseCase extends UseCase {

    private DataStore mDataStore;

    // inner class... return result from GetEntriesUseCase
    public static class EntriesResult implements ReturnResult{
        private final List<DisplayEntry> entries;
        private final List<Drawable> imageList;
        private final boolean entriesSuccess;

        // get entries operation successful
        public EntriesResult(List<DisplayEntry> entries,List<Drawable> imageList){
            this.entries=entries;
            this.imageList=imageList;
            entriesSuccess=true;
        }
        // get entries operation failed
        public EntriesResult(){
            this.entries=null;
            this.imageList=null;
            entriesSuccess=false;
        }

        public List<DisplayEntry> getEntries() {
            return entries;
        }
        public List<Drawable> getImageList() {
            return imageList;
        }

        @Override
        public boolean isSuccess() {
            return entriesSuccess;
        }
    }

    public GetEntriesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,DataStore mDataStore) {
        super(threadExecutor, postExecutionThread);
        this.mDataStore=mDataStore;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return mDataStore.getEntriesObs();
    }


}
