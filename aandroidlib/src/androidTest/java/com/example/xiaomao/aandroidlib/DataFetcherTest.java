package com.example.xiaomao.aandroidlib;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.xiaomao.repository.DataFetcherImpl;

/**
 * Created by Administrator on 16-3-18.
 */
public class DataFetcherTest extends AndroidTestCase {

    final String tag="androidtest";

    public void testGetEntries(){
        Context context=getContext().getApplicationContext();
        Log.i(tag,"is context null?"+(context==null));
        DataFetcherImpl fetcher=new DataFetcherImpl(context);
        Log.i(tag, fetcher.getEntries().toString());
        Log.i(tag, fetcher.getEntry(10).toString());
        Log.i(tag, fetcher.getEntry(11).toString());
        Log.i(tag, fetcher.getEntry(13).toString());
    }


    /** check the caching behaviour  **/
    public void testGetEntriesCache(){
        Context context=getContext().getApplicationContext();
        DataFetcherImpl fetcher=new DataFetcherImpl(context);
        long t1=System.nanoTime();
        Log.i(tag, fetcher.getEntries().toString());
        long t2=System.nanoTime();
        Log.i(tag,"elapsed time in milli-sec:"+(t2-t1)/1000000f);

        long t3=System.nanoTime();
        Log.i(tag, fetcher.getEntries().toString());
        long t4=System.nanoTime();
        Log.i(tag,"elapsed time in milli-sec:"+(t4-t3)/1000000f);

    }


}
