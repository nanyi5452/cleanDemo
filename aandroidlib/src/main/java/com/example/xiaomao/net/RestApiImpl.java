package com.example.xiaomao.net;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.coreDomain.DisplayEntry;
import com.example.xiaomao.interactor.ReturnResult;
import com.example.xiaomao.utils.mapper.DispEntryJsonMapper;

import java.util.List;

/**
 * Created by Administrator on 16-3-18.
 */
public class RestApiImpl extends RestApiAdapter {



    private final Context appContext;
    private final DispEntryJsonMapper jsonMapper=new DispEntryJsonMapper();
    private RestApi delegate;

    // constructor --> use fake net-API
    public RestApiImpl(Context appContext) {
        if (appContext == null || jsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.appContext = appContext;
        if (delegate == null) {
            delegate = new FakeAPIconnection(appContext);
        }
    }

    // constructor --> use real net-API, need real API(delegate) input
    public RestApiImpl(Context appContext, RestApi delegate) {
        if (appContext == null || jsonMapper == null || delegate == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.appContext = appContext;
        this.delegate = delegate;
    }




    @Override
    public ReturnResult deleteEntry(int entryId) {
        if (delegate != null )
            return delegate.deleteEntry(entryId);
        else return null;
    }

    @Override
    public List<DisplayEntry> getEntries() {
        if (delegate != null)
            return delegate.getEntries();
        else return null;
    }

    @Override
    public DisplayEntry getEntry(int entryId) {
        if (delegate != null)
            return delegate.getEntry(entryId);
        else return null;
    }

    @Override
    public Drawable getImageFromURL(String imageURL) {
        if (delegate != null)
            return delegate.getImageFromURL(imageURL);
        else return null;
    }
}
