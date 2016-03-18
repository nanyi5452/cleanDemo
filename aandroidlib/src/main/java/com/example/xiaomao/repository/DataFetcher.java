package com.example.xiaomao.repository;

import com.example.coreDomain.DisplayEntry;
import com.example.xiaomao.net.RestApi;

import java.util.List;

/**
 * Created by nanyi545 on 16-3-18.
 *
 * DataFetcher interface defines operations to fetch data from  networkApi + Cache
 *
 * it happens to have the same interface as {@link RestApi}
 *
 */
public interface DataFetcher extends RestApi{
    /** force network-API, do not cache, b1=true **/
    List<DisplayEntry> getEntries(boolean b1);
}
