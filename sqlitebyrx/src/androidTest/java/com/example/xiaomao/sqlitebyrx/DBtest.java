package com.example.xiaomao.sqlitebyrx;

import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.xiaomao.sqlitebyrx.db.DbManager;
import com.example.xiaomao.sqlitebyrx.db.ProvinceTable;
import com.example.xiaomao.sqlitebyrx.entitis.Province;
import com.example.xiaomao.sqlitebyrx.views.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by Administrator on 16-4-19.
 */
public class DBtest extends ActivityInstrumentationTestCase2<MainActivity> {


    public DBtest() {
        super(MainActivity.class);
    }


    private MainActivity mMainActivity;
    private DbManager dbManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
        dbManager=new DbManager(mMainActivity,"sqliteDemo1.db",1,new String[]{"table1"},new Class[]{ProvinceTable.class});
    }

    public void testDBadd(){
        ProvinceTable table1= dbManager.getTable(ProvinceTable.class,"table1");
        long l1=table1.add(new Province("浙江","zhejiang",19854));
        long l2=table1.add(new Province("云南","yunnan",169854));
        Log.i("AAA","-"+l1+"--"+l2);
    }



    public void testDBread4(){
        ProvinceTable table1= dbManager.getTable(ProvinceTable.class,"table1");
        List<Province> list=table1.getObjects();
        Log.i("AAA",list.toString());
    }



    public void testDBread1(){
        ProvinceTable table1= dbManager.getTable(ProvinceTable.class,"table1");
        Province p1=table1.getObject(2);
        if (p1==null) Log.i("AAA","could not find object at the index");
        if (p1!=null) Log.i("AAA",p1.toString());
    }



    public void testDBdelete1(){
        ProvinceTable table1= dbManager.getTable(ProvinceTable.class,"table1");
        boolean deleteSuccess=table1.delete(new Province("云南","yunnan",169854));
        Log.i("AAA","delete successful?"+deleteSuccess);
        List<Province> list=table1.getObjects();
        Log.i("AAA",list.toString());
    }


    public void testDBmodify1(){
        ProvinceTable table1= dbManager.getTable(ProvinceTable.class,"table1");
        boolean modifySuccess=table1.modify(new Province("贵州","guizhou",451123),1);
        Log.i("AAA","modify successful?"+modifySuccess);
        List<Province> list=table1.getObjects();
        Log.i("AAA",list.toString());
    }

    public void testDBCopy(){
        //  /data/data/... can not be accessed.
        //  if you want to see the db file, you need to copy the file to an external location
        File db=mMainActivity.getDatabasePath("sqliteDemo1.db");
        Log.i("AAA","DB path:"+db.getAbsolutePath());

        File f1=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.i("AAA","f1 path:"+f1.getAbsolutePath());

        File f2=new File(f1.getAbsolutePath()+"/aaa.db");
        if(f2.exists())f2.delete();

        try {
            byte[] b=new byte[30];
            int bytes=0;
            InputStream is=new FileInputStream(db);
            OutputStream os=new FileOutputStream(f2);
            while((bytes=is.read(b,0,b.length))!=-1){
                os.write(b);
                os.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void testDB_RXread1(){
        ProvinceTable table1= dbManager.getTable(ProvinceTable.class,"table1");

        Observable<Province> observable=table1.getObjectObservable(2);

        Observer<Province> observer = new Observer<Province>() {
            @Override
            public void onCompleted() {
                Log.i("AAA", "obs1 completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("AAA", "obs1 onerror");
            }

            @Override
            public void onNext(Province province) {
                Log.i("AAA", ""+province.toString());
            }
        };

        observable.subscribe(observer);

    }

    public void testDB_RXread2(){
        ProvinceTable table1= dbManager.getTable(ProvinceTable.class,"table1");

        Observable<List<Province>> observable=table1.getObjectsObservable();

        Observer<List<Province>> observer = new Observer<List<Province>>() {
            @Override
            public void onCompleted() {
                Log.i("AAA", "obs1 completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("AAA", "obs1 onerror");
            }

            @Override
            public void onNext(List<Province> list) {
                Log.i("AAA", ""+list.toString());
            }
        };
        observable.subscribe(observer);
    }


    public void testDB_RXadd(){
        ProvinceTable table1= dbManager.getTable(ProvinceTable.class,"table1");

        Observable<Long> observable=table1.addObservable(new Province("辽宁","liaoning",23423));

        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.i("AAA", "obs1 completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("AAA", "obs1 onerror");
            }

            @Override
            public void onNext(Long retID) {
                Log.i("AAA", "added to  ID:"+retID);
            }
        };
        observable.subscribe(observer);
    }

    public void testDB_RXdelete(){
        ProvinceTable table1= dbManager.getTable(ProvinceTable.class,"table1");

        Observable<Boolean> observable=table1.deleteObservable(new Province("云南","yunnan",169854));

        Observer<Boolean> observer = new Observer<Boolean>() {
            @Override
            public void onCompleted() {
                Log.i("AAA", "obs1 completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("AAA", "obs1 onerror");
            }

            @Override
            public void onNext(Boolean deleteSuccess) {
                Log.i("AAA", "delete successful:"+deleteSuccess);
            }
        };
        observable.subscribe(observer);
    }




    public void testDB_RXmodify(){
        ProvinceTable table1= dbManager.getTable(ProvinceTable.class,"table1");

        Observable<Boolean> observable=table1.modifyObservable(new Province("四川","sichuan",162854),3);

        Observer<Boolean> observer = new Observer<Boolean>() {
            @Override
            public void onCompleted() {
                Log.i("AAA", "obs1 completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("AAA", "obs1 onerror");
            }

            @Override
            public void onNext(Boolean deleteSuccess) {
                Log.i("AAA", "modify successful:"+deleteSuccess);
            }
        };
        observable.subscribe(observer);
    }








}
