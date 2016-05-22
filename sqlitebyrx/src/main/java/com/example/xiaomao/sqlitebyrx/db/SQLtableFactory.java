package com.example.xiaomao.sqlitebyrx.db;

/**
 * Created by Administrator on 16-4-21.
 */
public interface SQLtableFactory {

    <T extends SQLtable> T createTable(Class<T> cls, String tableName);



}
