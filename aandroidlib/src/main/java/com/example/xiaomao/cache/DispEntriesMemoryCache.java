package com.example.xiaomao.cache;

import com.example.coreDomain.DisplayEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-3-18.
 */
public class DispEntriesMemoryCache implements Cache<String,List<DisplayEntry>> {

    public DispEntriesMemoryCache(){

    }


    private static final long ExpireThresh=1000*60*10L;  // 10minute
    private static final long FreshThresh=1000*30L;  // 30sec

    private long lastPutTime=System.currentTimeMillis();
    private List<DisplayEntry> lastList=new ArrayList();


    @Override
    public List<DisplayEntry> get(String key) {
        return lastList;
    }

    @Override
    public List<DisplayEntry> getLast() {
        return lastList;
    }

    @Override
    public boolean isFresh() {
        long now=System.currentTimeMillis();
        return ((now-lastPutTime)<FreshThresh);
    }

    @Override
    public void put(String key, List<DisplayEntry> obj) {
        lastList=obj;
        lastPutTime=System.currentTimeMillis();
    }

    @Override
    public boolean isCached(String key) {
        return lastList.size()!=0;  //  ==0 : not cached   !=0 : cached
    }

    @Override
    public boolean isExpired() {
        long now=System.currentTimeMillis();
        boolean ret=((now-lastPutTime)>ExpireThresh);
        if (ret) evictAll();
        return ret;
    }

    @Override
    public void evictAll() {
        lastList=new ArrayList();
    }





}
