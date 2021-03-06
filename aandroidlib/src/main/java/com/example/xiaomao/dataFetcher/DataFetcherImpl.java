package com.example.xiaomao.dataFetcher;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.coreDomain.DisplayEntry;
import com.example.xiaomao.cache.Cache;
import com.example.xiaomao.cache.DispEntriesMemoryCache;
import com.example.xiaomao.cache.ImageDiskCache;
import com.example.xiaomao.interactor.ReturnResult;
import com.example.xiaomao.net.RestApi;
import com.example.xiaomao.net.RestApiImpl;

import java.util.List;

/**
 * Created by Administrator on 16-3-18.
 */
public class DataFetcherImpl implements DataFetcher {

    Cache<String,List<DisplayEntry>> memoryListCache;
    RestApi restApi;
    Context appContext;


    Cache<String,Drawable> imageDiskCache;

    public DataFetcherImpl(Context appContext){
        this.appContext=appContext;
        this.memoryListCache=new DispEntriesMemoryCache();
        this.restApi=new RestApiImpl(this.appContext);
        imageDiskCache=new ImageDiskCache(appContext);
    }

    public DataFetcherImpl(Context appContext,RestApi restApi){
        this.appContext=appContext;
        this.memoryListCache=new DispEntriesMemoryCache();
        this.restApi=restApi;
    }




    @Override
    public List<DisplayEntry> getEntries(boolean b1) {  // if b1=true, force network API, do not use cache
        if (b1) {
            List<DisplayEntry> list = restApi.getEntries();  // not use cache but use network api
            memoryListCache.put("", list);
            return list;
        } else return getEntries();
    }


    @Override
    public List<DisplayEntry> getEntries() {
        if (memoryListCache.isFresh()&&memoryListCache.isCached(""))  // check if use cache
        return memoryListCache.getLast();
        else {
            List<DisplayEntry> list=restApi.getEntries();  // not use cache but use network api
            memoryListCache.put("",list);
            return list;
        }
    }

    @Override
    public DisplayEntry getEntry(int entryId) {
        return restApi.getEntry(entryId);
    }

    @Override
    public ReturnResult deleteEntry(int entryId) {
        return restApi.deleteEntry(entryId);
    }

    @Override
    public Drawable getImageFromURL(String imageURL) {
        // implement diskLruCache....
        if (imageDiskCache.isCached(imageURL)){
            Log.i("DDD",imageURL+" is from cache");
            return  imageDiskCache.get(imageURL);
        } else {
            Drawable drawable=restApi.getImageFromURL(imageURL);
            imageDiskCache.put(imageURL,drawable);
            Log.i("DDD",imageURL+" is from net");
            return drawable;
        }
    }

}
