package com.example.xiaomao.net;

import android.graphics.drawable.Drawable;

import com.example.coreDomain.DisplayEntry;
import com.example.interactor.ReturnResult;

import java.util.List;

/**
 * Created by nanyi545 on 16-3-18.
 *
 * interface defining network operations
 */
public interface RestApi {


    List<DisplayEntry> getEntries();

    DisplayEntry getEntry(int entryId);

    ReturnResult deleteEntry(int entryId);

    Drawable getImageFromURL(String imageURL);

}
