package com.example.xiaomao.sqlitebyrx.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.xiaomao.sqlitebyrx.R;
import com.example.xiaomao.sqlitebyrx.fileIO.IOmanager;
import com.example.xiaomao.sqlitebyrx.fileIO.IntArrayIO;

import java.io.File;
import java.util.concurrent.Callable;

import rx.Observer;

public class FileIOActivity2 extends AppCompatActivity {

    public static void start(Context context){
        Intent i1=new Intent(context,FileIOActivity2.class);
        context.startActivity(i1);
    }


    int[] temp=new int[5];
    StringBuilder sb=new StringBuilder();
    int intsLength;
    int[] temp1={1,2,3,4,5};

    Observer<Callable<int[]>> write_observer = new Observer<Callable<int[]>>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
                Log.i("CCC", "time-out");

        }

        @Override
        public void onNext(Callable<int[]> func) {
            try {
                temp=func.call();
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("CCC", Log.getStackTraceString(e));
            }
//            intsLength=temp.length;
//            for (int aa=0;aa<intsLength;aa++){
//                sb.append("-"+temp[aa]);
//            }
            Log.i("CCC","on next" + Thread.currentThread().getName()+"     ---"+temp.toString());

        }
    };

    File target;
    IOmanager manager;

    private void init() {
        target=new File(getCacheDir().getAbsolutePath()+"/dddd");


        manager=new IOmanager(new IntArrayIO(target));
        manager.setWriteObserver(write_observer);
    }


    Runnable write2File=new Runnable() {
        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                manager.sendArray(temp1);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_io2);
        setTitle("--continuous write--");
//        init();
//        new Thread(write2File).start();
        init2();
        new Thread(write2File2).start();
        Log.i("BBB","ON CREATE:" + Thread.currentThread().getName());

    }






    Observer<int[]> write_observer2 = new Observer<int[]>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.i("CCC", "time-out");
        }

        @Override
        public void onNext(int[] ints) {
            Log.i("CCC","on next" + Thread.currentThread().getName()+"     ---"+ints.toString());
        }
    };

    private void init2() {
        target=new File(getCacheDir().getAbsolutePath()+"/dddd");
        manager=new IOmanager(new IntArrayIO(target));
        manager.setWriteObserver2(write_observer2);
    }


    Runnable write2File2=new Runnable() {
        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("BBB","send array:" + Thread.currentThread().getName());
                manager.sendArray2(temp1);
            }
        }
    };



}
