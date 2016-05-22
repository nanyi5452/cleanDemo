package com.example.xiaomao.sqlitebyrx.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-4-18.
 */
public class DbManager extends SQLiteOpenHelper {

    private final String DATABASE_NAME; //"sqliteDemo1.db";

    private final int DATABASE_VER ;

    private SQLiteDatabase db;

    SQLtableFactory tableFactory=new SQLtableFactoryImp();// table creator

    private TableMap maps=new TableMap(); //  holder to place all the tables
    private List<String> creationSQLcommands =new ArrayList<String>(); //   holder to place all the SQL commands needed to create tables


    /**
     *
     * @param context
     * @param DATABASE_NAME
     * @param DATABASE_VER
     * @param tableNames  : a String array of table names
     * @param tableClasses: a Class array of tables
     */
    public DbManager(Context context,String DATABASE_NAME,int DATABASE_VER,String[] tableNames,Class[] tableClasses ) {
        super(context, DATABASE_NAME, null, DATABASE_VER);

        this.DATABASE_NAME=DATABASE_NAME;
        this.DATABASE_VER=DATABASE_VER;

        // create tables and put tables in the container
        int num_of_tables=tableNames.length;
        if (num_of_tables>0) {
            for (int dummy=0;dummy<num_of_tables;dummy++){
                SQLtable table=tableFactory.createTable(tableClasses[dummy],tableNames[dummy]);
                maps.putTable(tableClasses[dummy],tableNames[dummy],table);
//                Log.i("AAA","is table null?"+(table==null));
                String str=table.getSQLgenerator();
                creationSQLcommands.add(str);
            }
        }

        //The first time this is called, the database will be opened and  onCreate,onUpgrade and/or onOpen will be called.
        db = getWritableDatabase();
//        Log.i("AAA","is db null?"+(db==null));

        maps.setDB(db);

    }




    // get table stored in the map !
    public <T extends SQLtable> T getTable(Class<T> cls,String str) {
        return maps.getTable(cls,str);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        // create tables for the first time the DB is accessed
        if(creationSQLcommands.size()>0){
            for (int dummy = 0; dummy< creationSQLcommands.size(); dummy++){
                db.execSQL(creationSQLcommands.get(dummy));
            }
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




}
