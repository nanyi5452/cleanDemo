package com.example.interactor;

import com.example.executor.PostExecutionThread;
import com.example.executor.ThreadExecutor;

import rx.Observable;

/**
 * Created by Administrator on 16-3-19.
 */
public class GetEntriesUseCase extends UseCase {




    protected GetEntriesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return null;
    }
}
