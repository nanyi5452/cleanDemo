package com.example.xiaomao.sqlitebyrx.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.xiaomao.sqlitebyrx.entitis.Province;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 16-4-19.
 */
public class ProvinceTable implements SQLtable<Province>{

//    String sql = "CREATE TABLE province " +
//            "(ID INT PRIMARY KEY     NOT NULL," +
//            " JSON_STRING   CHAR(80))";  //  or use:  TEXT


    private final String tableName;
    private SQLiteDatabase db;

//    public ProvinceTable(SQLiteDatabase db){
//        this.db=db;
//    }

    public ProvinceTable(String tableName){
        this.tableName=tableName;
    }


    @Override
    public void setDB(SQLiteDatabase db) {
        this.db=db;
    }

    @Override
    public Province getObject(int id){
        Cursor c = db.rawQuery("SELECT * FROM "+tableName+" WHERE ID = ?",  new String[] { ""+id });
        c.moveToNext();
        String str= null;
        try {
            str = c.getString(1);
        } catch (Exception e) {
            e.printStackTrace();   //  this particular ID is empty (possibly deleted)
            return null;
        }
        Province p1=Province.getProvinceFromJsonString(str);
        return p1;
    }

    @Override
    public List<Province> getObjects() {
        Cursor c = db.rawQuery("SELECT * FROM "+tableName+" WHERE ID > ?",  new String[] { "0" });
        List<Province> list=new ArrayList();
        while (c.moveToNext()) {
            Log.i("AAA","id:"+c.getInt(0));
            list.add(Province.getProvinceFromJsonString(c.getString(1)) );
        }
        return list;
    }

    @Override
    public long add(Province province) {
        ContentValues values = new ContentValues();
        values.put("JSON_STRING", province.getJsonString());
        long rowsNum = db.insert(tableName, "JSON_STRING", values);
        return rowsNum;
    }

    @Override
    public boolean delete(Province province) {
        String sql = "delete from "+tableName+" where JSON_STRING='"+province.getJsonString()+"'";//删除操作的SQL语句
        try {
            db.execSQL(sql);//执行删除操作
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean modify(Province province,int id) {
        String sql = "update ["+tableName+"] set JSON_STRING = '"+province.getJsonString()+"' where ID="+id;//修改的SQL语句
        try {
            db.execSQL(sql);//
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String getSQLgenerator() {
        return "create table " + tableName +
                " (ID integer primary key autoincrement, JSON_STRING CHAR(80))";
    }

    @Override
    public String getTableName() {
        return tableName;
    }


    @Override
    public Callable<Province> getObject_callable(final int id) {
        return new Callable<Province>() {
            @Override
            public Province call() throws Exception{
                return getObject(id);
            }
        };
    }

    @Override
    public Callable<List<Province>> getObjects_callable() {
        return new Callable<List<Province>>(){
            @Override
            public List<Province> call() throws Exception {
                return getObjects();
            }
        };
    }

    @Override
    public Callable<Long> add_callable(final Province province) {
        return new Callable<Long>(){
            @Override
            public Long call() throws Exception {
                return add(province);
            }
        };
    }

    @Override
    public Callable<Boolean> delete_callable(final Province province) {
        return new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                return delete(province);
            }
        };
    }

    @Override
    public Callable<Boolean> modify_callable(final Province province,final int id) {
        return new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                return modify(province,id);
            }
        };
    }



    @Override
    public Observable<Province> getObjectObservable(int id) {
        return makeObservable(getObject_callable(id)).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Province>> getObjectsObservable() {
        return makeObservable(getObjects_callable()).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Long> addObservable(Province province) {
        return makeObservable(add_callable(province)).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Boolean> deleteObservable(Province province) {
        return makeObservable(delete_callable(province)).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Boolean> modifyObservable(Province province, int id) {
        return makeObservable(modify_callable(province,id)).subscribeOn(Schedulers.io());
    }


    private <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(func.call());
                        } catch (Exception ex) {
                            Log.e("AAA", "Error database operation", ex);
                        }
                    }
                });
    }




}
