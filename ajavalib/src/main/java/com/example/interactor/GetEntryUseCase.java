package com.example.interactor;

import com.example.executor.PostExecutionThread;
import com.example.executor.ThreadExecutor;

import rx.Observable;

/**
 * Created by Administrator on 16-3-18.
 */
public class GetEntryUseCase extends UseCase {

    protected GetEntryUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return null;
    }
}
