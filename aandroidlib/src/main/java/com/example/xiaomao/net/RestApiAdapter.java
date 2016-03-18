package com.example.xiaomao.net;

import android.graphics.drawable.Drawable;

import com.example.coreDomain.DisplayEntry;
import com.example.interactor.ReturnResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-3-18.
 */
public class RestApiAdapter implements RestApi {


    @Override
    public List<DisplayEntry> getEntries() {
        return new ArrayList<DisplayEntry>();
    }

    @Override
    public DisplayEntry getEntry(int entryId) {
        return DisplayEntry.emptyEntry;
    }

    @Override
    public ReturnResult deleteEntry(int entryId) {
        return null;
    }

    @Override
    public Drawable getImageFromURL(String imageURL) {
        return null;
    }


}
