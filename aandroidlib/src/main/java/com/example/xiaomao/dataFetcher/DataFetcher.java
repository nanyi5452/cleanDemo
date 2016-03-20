package com.example.xiaomao.dataFetcher;

import com.example.coreDomain.DisplayEntry;
import com.example.xiaomao.net.RestApi;

import java.util.List;

/**
 * Created by nanyi545 on 16-3-18.
 *
 * DataFetcher interface defines operations to fetch data from  networkApi + Cache
 *
 *  this level is responsible to fetch data form different sources(networkApi or Cache e.g.)
 *  and to reorganize data for different use cases.
 *
 * the Repositories use DataFetchers to get data.
 *
 */
public interface DataFetcher extends RestApi{
    /** force network-API, do not cache, b1=true **/
    List<DisplayEntry> getEntries(boolean b1);
}
