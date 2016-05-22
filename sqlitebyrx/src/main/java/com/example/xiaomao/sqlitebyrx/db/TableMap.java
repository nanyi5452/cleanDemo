package com.example.xiaomao.sqlitebyrx.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 16-4-21.
 *
 * http://stackoverflow.com/questions/8918550/cast-via-reflection-and-use-of-class-cast
 *
 * This class is to be used as a temporary container of Tables which are implementations of the SQLtable interface
 *
 */
class TableMap {


    <T extends SQLtable> T getTable(Class<T> cls,String str) {
        Map.Entry<String, Class<?>> entry = new AbstractMap.SimpleEntry(str, cls);
        SQLtable table=tableMap.get(entry);
        return cls.cast(table);
    }

    void putTable(Class<?> cls,String str,SQLtable table) {
        Map.Entry<String, Class<?>> entry = new AbstractMap.SimpleEntry(str, cls);
        tableMap.put(entry,table);
    }

    private HashMap<Map.Entry<String,Class<?>>,SQLtable> tableMap=new HashMap();


    // set the db for all the tables ...
    // it smells a bit, to have this method in this class..
    // this method has to  be called to inject dependency to SQLtables
    void setDB(SQLiteDatabase db){
        Iterator<SQLtable> iterator=tableMap.values().iterator();
        while(iterator.hasNext()){
            iterator.next().setDB(db);
        }
    }



}
