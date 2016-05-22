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

import rx.Observer;

public class FileIOActivity extends AppCompatActivity {

    public static void start(Context context){
        Intent i1=new Intent(context,FileIOActivity.class);
        context.startActivity(i1);
    }


    Observer<int[]> writeObserver;
    File target;
    IOmanager manager;
    int[] temp={1,2,3,4,5};

    Runnable write2File=new Runnable() {
        @Override
        public void run() {

            while(true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                manager.getWriteObservable(temp).subscribe(writeObserver);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_io);
        setTitle("-- non-continuous write --");

        init();


        new Thread(write2File).start();


    }

    private void init() {
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
//                StringBuilder sb=new StringBuilder();
//                int intsLength=ints.length;
//                for (int aa=0;aa<intsLength;aa++){
//                    sb.append("-"+ints[aa]);
//                }
                Log.i("CCC",""+ints.toString());

            }
        };


        manager=new IOmanager(new IntArrayIO(target));
    }
}
