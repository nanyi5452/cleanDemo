package com.example.xiaomao.cache;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


/**
 * Created by Administrator on 16-3-23.
 */
public class ImageDiskCache implements Cache<String,Drawable> {

    private Bitmap drawable2bitmap(Drawable drawable){
        if (drawable instanceof BitmapDrawable){
            BitmapDrawable bitmap=((BitmapDrawable)drawable);
            return bitmap.getBitmap();
        }
        return null;
    }

    private Drawable bitmap2drawable(Bitmap bitmap){
        res = context.getResources();
        return new BitmapDrawable(res, bitmap);
    }


    private Context context;
    private long maxSize=8*1024*1024L;  //in bytes
    Resources res;

    private DiskLruImageCache cache;

    public ImageDiskCache(Context context){
        this.context=context;
        cache=new DiskLruImageCache(context,maxSize);
    }


    @Override
    public Drawable get(String key) {
        String temp_key=key.replace(".","_");  // "." can bot be contained in the key
        return bitmap2drawable(cache.getBitmap(temp_key));
    }

    @Override
    public Drawable getLast() {
        return null;
    }

    @Override
    public boolean isFresh() {
        return false;
    }

    @Override
    public void put(String key, Drawable obj) {
        String temp_key=key.replace(".","_");
        cache.put(temp_key,drawable2bitmap(obj));
    }

    @Override
    public boolean isCached(String key) {
        String temp_key=key.replace(".","_");
        return cache.containsKey(temp_key);
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public void evictAll() {

    }
}
