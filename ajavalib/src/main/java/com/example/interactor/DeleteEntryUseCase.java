package com.example.interactor;

import com.example.coreDomain.DisplayEntry;
import com.example.executor.PostExecutionThread;
import com.example.executor.ThreadExecutor;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 16-3-18.
 */
public class DeleteEntryUseCase extends UseCase {


    public static class DeleteResult implements ReturnResult{
        private final boolean deleteSuccess;
        private final List<DisplayEntry> newListAfterDelete;
        public DeleteResult(boolean deleteSuccess,List<DisplayEntry> newListAfterDelete){
            this.deleteSuccess=deleteSuccess;
            this.newListAfterDelete=newListAfterDelete;
        }
        @Override
        public boolean isSuccess() {
            return deleteSuccess;
        }
        public List<DisplayEntry> getReturnList(){return newListAfterDelete;}
    }

    public DeleteEntryUseCase(ThreadExecutor threadExecutor,
                              PostExecutionThread postExecutionThread){
        super(threadExecutor,postExecutionThread);
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return null;
    }
}
