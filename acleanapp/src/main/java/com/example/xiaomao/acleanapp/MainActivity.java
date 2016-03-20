package com.example.xiaomao.acleanapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.xiaomao.acleanapp.view.viewlist1.ViewListActivity;
import com.example.xiaomao.executors.JobExecutor;
import com.example.xiaomao.executors.UIThread;
import com.example.xiaomao.interactor.DeleteEntryUseCase;
import com.example.xiaomao.interactor.GetEntriesUseCase;
import com.example.xiaomao.interactor.GetEntryUseCase;
import com.example.xiaomao.interactor.ReturnResult;
import com.example.xiaomao.interactor.UseCase;
import com.example.xiaomao.repository.DataStore;
import com.example.xiaomao.repository.RemoteDataStore;
import com.example.xiaomao.utils.subscriber.DefaultSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public void jumpRV(View v){
        ViewListActivity.jump(this);
    }

    ImageView iv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv1= (ImageView) findViewById(R.id.iv1);

//        testRemoteRepository();
        testUseCase();


    }

    private void testRemoteRepository() {
        // test
        DataStore remote=new RemoteDataStore(getApplicationContext());
        remote.getEntriesObs().subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(Schedulers.computation()) .subscribe(new DefaultSubscriber<ReturnResult>(){
            @Override
            public void onNext(ReturnResult entriesRET) {
                GetEntriesUseCase.EntriesResult ret=(GetEntriesUseCase.EntriesResult)entriesRET;
                Log.i("AAA","getEntriesObs--on next in:"+Thread.currentThread().getName()+"---"+ret.getEntries().toString());
            }
        });

        remote.getEntryObs(11).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(Schedulers.computation()) .subscribe(new DefaultSubscriber<ReturnResult>(){
            @Override
            public void onNext(ReturnResult entryRet) {
                GetEntryUseCase.EntryReturn ret=(GetEntryUseCase.EntryReturn)entryRet;
                Log.i("AAA","getEntryObs--on next in:"+Thread.currentThread().getName()+"---"+ret.getRetEntry().toString());
            }
        });

        remote.deleteEntryObs(17).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(Schedulers.computation()).subscribe(new  DefaultSubscriber<ReturnResult>(){
            @Override
            public void onNext(ReturnResult deleRet) {
                Log.i("AAA","deleteEntryObs---on next in:"+Thread.currentThread().getName()+"--delete success--"+deleRet.isSuccess());

                if (deleRet.isSuccess()){
                    DeleteEntryUseCase.DeleteResult ret=(DeleteEntryUseCase.DeleteResult)deleRet;
                    Log.i("AAA","deleteEntryObs---on next in:"+Thread.currentThread().getName()+"--list after delete--"+ret.getReturnList().toString());
                }
            }
        });

        remote.getImageFromURL("11").subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new  DefaultSubscriber<Drawable>(){
            @Override
            public void onNext(Drawable drawable) {
                iv1.setImageDrawable(drawable);
            }
        });


    }

    private void testUseCase(){
        DataStore remote=new RemoteDataStore(getApplicationContext());
        UseCase getEntries=new GetEntriesUseCase(new JobExecutor(),new UIThread(),remote);
        getEntries.execute(new DefaultSubscriber<ReturnResult>(){
            @Override
            public void onNext(ReturnResult entriesRET) {
                GetEntriesUseCase.EntriesResult ret=(GetEntriesUseCase.EntriesResult)entriesRET;
                Log.i("AAA","getEntriesObs--on next in:"+Thread.currentThread().getName()+"---"+ret.getEntries().toString());
            }
        });

        UseCase delete13=new DeleteEntryUseCase(new JobExecutor(),new UIThread(),remote,13);
        delete13.execute(new DefaultSubscriber<ReturnResult>(){
            @Override
            public void onNext(ReturnResult deleRet) {
                if (deleRet.isSuccess()) {
                    DeleteEntryUseCase.DeleteResult ret = (DeleteEntryUseCase.DeleteResult) deleRet;
                    Log.i("AAA", "delete--on next in:" + Thread.currentThread().getName() + "--list after delete--" + ret.getReturnList().toString());
                }
            }
        });

        UseCase get11=new GetEntryUseCase(new JobExecutor(),new UIThread(),remote,11);
        get11.execute(new DefaultSubscriber<ReturnResult>(){
            @Override
            public void onNext(ReturnResult getRet) {
                if (getRet.isSuccess()) {
                    GetEntryUseCase.EntryReturn ret = (GetEntryUseCase.EntryReturn) getRet;
                    Log.i("AAA", "getEntry--on next in:" + Thread.currentThread().getName() + "--get entry--" + ret.getRetEntry().toString());
                    iv1.setImageDrawable(ret.getRetImage());
                }
            }
        });

    }
}
