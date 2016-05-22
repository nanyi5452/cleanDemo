package com.example.xiaomao.sqlitebyrx;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.xiaomao.sqlitebyrx.db.ProvinceTable;
import com.example.xiaomao.sqlitebyrx.db.SQLtableFactory;
import com.example.xiaomao.sqlitebyrx.db.SQLtableFactoryImp;
import com.example.xiaomao.sqlitebyrx.views.MainActivity;

/**
 * Created by Administrator on 16-4-19.
 */
public class DBtest1 extends ActivityInstrumentationTestCase2<MainActivity> {


    public DBtest1() {
        super(MainActivity.class);
    }


    private MainActivity mMainActivity;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
    }


    public void test1(){
        SQLtableFactory tableFactory=new SQLtableFactoryImp();
        ProvinceTable table1=tableFactory.createTable(ProvinceTable.class,"table1");
        Log.i("AAA",""+(table1==null));
        if(table1!=null)Log.i("AAA",""+(table1.getSQLgenerator()));
    }

    public void test2(){
        ProvinceTable table1=new ProvinceTable("table1");
        Log.i("AAA",""+(table1==null));
        if(table1!=null)Log.i("AAA",""+(table1.getSQLgenerator()));
    }

}
