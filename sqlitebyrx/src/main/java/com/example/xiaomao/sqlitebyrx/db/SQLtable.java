package com.example.xiaomao.sqlitebyrx.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

/**
 * Created by Administrator on 16-4-18.
 */
interface SQLtable<T> {

    void setDB(SQLiteDatabase db);

    T getObject(int id);
    List<T> getObjects();
    long add(T t);
    boolean delete(T t);
    boolean modify(T t,int id);

    String getSQLgenerator();
    String getTableName();

    Callable<T> getObject_callable(int id);
    Callable<List<T>> getObjects_callable();
    Callable<Long> add_callable(T t);
    Callable<Boolean> delete_callable(T t);
    Callable<Boolean> modify_callable(T t,int id);

    Observable<T> getObjectObservable(int id);
    Observable<List<T>> getObjectsObservable();
    Observable<Long> addObservable(T t);
    Observable<Boolean> deleteObservable(T t);
    Observable<Boolean> modifyObservable(T t,int id);


}
