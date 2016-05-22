package com.example.xiaomao.sqlitebyrx.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.xiaomao.sqlitebyrx.fileIO.IOmanager;
import com.example.xiaomao.sqlitebyrx.fileIO.IntArrayIO;

import java.io.File;

import rx.Observer;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Observer<int[]> writeObserver;
    File target;
    IOmanager manager;



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        target=new File(getCacheDir().getAbsolutePath()+"/dddd");

        writeObserver=new Observer<int[]>(){
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(int[] ints) {
                StringBuilder sb=new StringBuilder();
                int intsLength=ints.length;
                for (int aa=0;aa<intsLength;aa++){
                    sb.append("-"+ints[aa]);
                }
                Log.i("CCC",""+sb.toString());

            }
        };


        manager=new IOmanager(new IntArrayIO(target));


        new Thread(new Runnable() {
            @Override
            public void run() {
                int[] temp={1,2,3,4,5};
                while(true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    manager.getWriteObservable(temp).subscribe(writeObserver);
                }
            }
        }).start();


        return super.onStartCommand(intent, flags, startId);
    }
}
