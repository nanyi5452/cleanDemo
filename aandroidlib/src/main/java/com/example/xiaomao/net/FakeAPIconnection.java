package com.example.xiaomao.net;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.coreDomain.DisplayEntry;
import com.example.interactor.DeleteEntryUseCase;
import com.example.interactor.ReturnResult;
import com.example.xiaomao.aandroidlib.R;
import com.example.xiaomao.utils.mapper.DispEntryJsonMapper;

import java.util.Iterator;
import java.util.List;

/**
 * Created by nanyi545  on 16-3-18.
 *
 * fake net API
 */
public class FakeAPIconnection implements RestApi{

    Context appContext;  // need context to get Bitmap/Drawable
    public FakeAPIconnection(Context appContext){
        this.appContext=appContext;
    }

    DispEntryJsonMapper mapper1=new DispEntryJsonMapper();
    String str1="[{\"imageURL\"=\"www.jlu.edu.cn\",\"description\"=\"test\",\"id\"=10}" +
            ",{\"imageURL\"=\"ece.jlu.edu.cn\",\"description\"=\"test2\",\"id\"=11}"+
            ",{\"imageURL\"=\"xx.jlu.edu.cn\",\"description\"=\"test3\",\"id\"=13}]";
    List<DisplayEntry> list=mapper1.getDisplayEntries(str1);


    @Override
    public List<DisplayEntry> getEntries() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public DisplayEntry getEntry(int entryId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DisplayEntry ret;
        Iterator<DisplayEntry> iterator=list.iterator();
        while (iterator.hasNext()){
            ret=iterator.next();
            if (ret.getEntryId()==entryId)
            return ret;
        }
        return null;
    }

    @Override
    public ReturnResult deleteEntry(int entryId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DisplayEntry temp=null;
        boolean deleteSuccess=false;
        Iterator<DisplayEntry> iterator=list.iterator();
        while (iterator.hasNext()){
            temp=iterator.next();
            if (temp.getEntryId()==entryId) break;
        }

        if (temp!=null) {
            list.remove(temp);
            deleteSuccess=true;
        }

        ReturnResult ret=new DeleteEntryUseCase.DeleteResult(deleteSuccess,list);
        return ret;
    }

    @Override
    public Drawable getImageFromURL(String imageURL) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Resources res=appContext.getResources();
        Bitmap bitmap= BitmapFactory.decodeResource(res, R.drawable.icon1);
        Drawable d=new BitmapDrawable(res,bitmap);
        return d;
    }
}
