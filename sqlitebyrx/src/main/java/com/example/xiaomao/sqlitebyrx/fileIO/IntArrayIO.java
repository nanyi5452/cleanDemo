package com.example.xiaomao.sqlitebyrx.fileIO;

import android.os.Environment;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 16-2-1.
 */
public class IntArrayIO {

    private File saveFile;


    public IntArrayIO(File file) {
        this.saveFile = file;
        tempBuffer = new ArrayList();
        try {
            dos = new DataOutputStream(new FileOutputStream(saveFile, true));  // append mode-->
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private List<Integer> tempBuffer;// memory buffer: only size larger than bufferSize(below), will an IO operation gets triggered
    public int bufferSize = 100;   //

    int[] writeTempArray;
    DataOutputStream dos;
    int write_index;


    // ----------actual operations: 1  write to files  -->
    protected int[] write2File(int[] aaa) {
//        Log.i("AAA","start write"+Thread.currentThread().getName());
//        if (Environment.getExternalStorageState().equals(   //检查是否有ExternalStorage
//                Environment.MEDIA_MOUNTED))
        {

            tempBuffer.addAll(ArrayAnalyser.convert2intList(aaa));
//            Log.i("AAA","current size"+tempBuffer.size());

            if (tempBuffer.size() > bufferSize) {
                try {
                    writeTempArray = ArrayAnalyser.convert2intArray(tempBuffer);

                    write_index = 0;
                    while (write_index < writeTempArray.length) {
                        dos.writeInt(writeTempArray[write_index]);
                        write_index++;
//                        Log.i("AAA", "writing..." + index + "   in thread:" + Thread.currentThread().getName());
                    }

                    tempBuffer.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i("AAA","end write:"+Thread.currentThread().getName());
        return aaa;
    }


    // ----------actual operations 2 read files -->
    protected int[] readFile(File file) {
        Log.i("CCC","start read"+Thread.currentThread().getName());
        List<Integer> tempList = new ArrayList();
        if (Environment.getExternalStorageState().equals(   //检查是否有ExternalStorage
                Environment.MEDIA_MOUNTED)) {
            DataInputStream dis = null;
            try {
                dis = new DataInputStream(new FileInputStream(file));
                int totalBytes = dis.available();
                while (totalBytes > 0) {
                    tempList.add(dis.readInt());
                    totalBytes = dis.available();
                }
                dis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.i("CCC","FileNotFoundException");
                // /data/data/rm.module_net/files/nh_breath_rec1/2016_02_19/13_28.test
                // /data/data/rm.module_net/files/2016_2_19/13_28.test
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("CCC","IOException");
            }
        }
        int[] ret = ArrayAnalyser.convert2intArray(tempList);
        ArrayAnalyser.dumpArray(ret,"CCC");
        Log.i("CCC","finish read"+Thread.currentThread().getName());
        return ret;
    }




    class WriteCallable implements Callable<int[]>{

        int[] aaa;
        public void setaaa(int[] aaa){
            this.aaa=aaa;
        }

        @Override
        public int[] call() throws Exception {
            write2File(aaa);
            return aaa;

        }
    }

    //------
    WriteCallable writeCallable=new WriteCallable();



    //-----wrap in callables  :-----
    // write callable
    protected Callable<int[]> getWriteCallable(final int[] aaa) {
        writeCallable.setaaa(aaa);
        return writeCallable;
    }


    // write callable
    protected Callable<int[]> getWriteCallable2(final int[] aaa) {
        return new Callable<int[]>() {
            @Override
            public int[] call() throws Exception {
                return write2File(aaa);
            }
        };
    }


    // read callable
    protected Callable<int[]> getReadCallable(final File file) {
        return new Callable<int[]>() {
            @Override
            public int[] call() throws Exception {
                return readFile(file);
            }
        };
    }




}
