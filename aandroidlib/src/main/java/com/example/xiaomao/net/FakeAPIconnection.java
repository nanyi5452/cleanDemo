package com.example.xiaomao.net;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.coreDomain.DisplayEntry;
import com.example.xiaomao.aandroidlib.R;
import com.example.xiaomao.interactor.DeleteEntryUseCase;
import com.example.xiaomao.interactor.ReturnResult;
import com.example.xiaomao.utils.mapper.DispEntryJsonMapper;

import java.util.Iterator;
import java.util.List;

/**
 * Created by nanyi545  on 16-3-18.
 * <p>
 * fake net API
 */
public class FakeAPIconnection implements RestApi {

    Context appContext;  // need context to get Bitmap/Drawable

    public FakeAPIconnection(Context appContext) {
        this.appContext = appContext;
    }

    DispEntryJsonMapper mapper1 = new DispEntryJsonMapper();
    String str1 = "[{\"imageURL\"=\"www.jlu.edu.cn\",\"description\"=\"item1\",\"id\"=10}" +
            ",{\"imageURL\"=\"ece.jlu.edu.cn\",\"description\"=\"item2\",\"id\"=11}" +
            ",{\"imageURL\"=\"xx.jlu.edu.cn\",\"description\"=\"item3\",\"id\"=12}" +
            ",{\"imageURL\"=\"x1\",\"description\"=\"item4\",\"id\"=13}" +
            ",{\"imageURL\"=\"x2\",\"description\"=\"item5\",\"id\"=14}" +
            ",{\"imageURL\"=\"x3\",\"description\"=\"item6\",\"id\"=15}" +
            ",{\"imageURL\"=\"x4\",\"description\"=\"item7\",\"id\"=16}" +
            ",{\"imageURL\"=\"x5\",\"description\"=\"item8\",\"id\"=17}" +
            ",{\"imageURL\"=\"x6\",\"description\"=\"item9\",\"id\"=18}" +
            ",{\"imageURL\"=\"x7\",\"description\"=\"item10\",\"id\"=19}" +
            "]";


    List<DisplayEntry> list = mapper1.getDisplayEntries(str1);


    @Override
    public List<DisplayEntry> getEntries() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("AAA", "getEntries in " + Thread.currentThread().getName());
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
        Iterator<DisplayEntry> iterator = list.iterator();
        while (iterator.hasNext()) {
            ret = iterator.next();
            if (ret.getEntryId() == entryId)
                return ret;
        }
        Log.i("AAA", "getEntry in " + Thread.currentThread().getName());
        return null;
    }

    @Override
    public ReturnResult deleteEntry(int entryId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DisplayEntry temp = null;
        boolean deleteSuccess = false;
        Iterator<DisplayEntry> iterator = list.iterator();
        while (iterator.hasNext()) {
            DisplayEntry dummy = iterator.next();
            if (dummy.getEntryId() == entryId) {
                temp = dummy;
                break;
            }
        }

        if (temp != null) {
            list.remove(temp);
            deleteSuccess = true;
        }

        ReturnResult ret = new DeleteEntryUseCase.DeleteResult(deleteSuccess, list);
        Log.i("AAA", "delete entry in " + Thread.currentThread().getName());
        return ret;
    }

    @Override
    public Drawable getImageFromURL(String imageURL) {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Resources res = appContext.getResources();
        Bitmap bitmap;
        if (imageURL.equals("x1")) bitmap = BitmapFactory.decodeResource(res, R.drawable.x1);
        else if (imageURL.equals("x2")) bitmap = BitmapFactory.decodeResource(res, R.drawable.x2);
        else if (imageURL.equals("x3")) bitmap = BitmapFactory.decodeResource(res, R.drawable.x3);
        else if (imageURL.equals("x4")) bitmap = BitmapFactory.decodeResource(res, R.drawable.x4);
        else if (imageURL.equals("x5")) bitmap = BitmapFactory.decodeResource(res, R.drawable.x5);
        else if (imageURL.equals("x6")) bitmap = BitmapFactory.decodeResource(res, R.drawable.x6);
        else if (imageURL.equals("x7")) bitmap = BitmapFactory.decodeResource(res, R.drawable.x7);
        else bitmap = BitmapFactory.decodeResource(res, R.drawable.icon1);
        Drawable d = new BitmapDrawable(res, bitmap);
        Log.i("AAA", "getImageFromURL in " + Thread.currentThread().getName());
        return d;
    }
}
