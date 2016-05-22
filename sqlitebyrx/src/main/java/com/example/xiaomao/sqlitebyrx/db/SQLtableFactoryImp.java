package com.example.xiaomao.sqlitebyrx.db;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 16-4-21.
 */
public class SQLtableFactoryImp implements SQLtableFactory {


    @Override
    public <T extends SQLtable> T createTable(Class<T> cls,String tableName) {
        SQLtable table=null;
        
        try {
            Constructor cons1=Class.forName(cls.getName()).getConstructor(new Class[]{String.class});
            table=(SQLtable)cons1.newInstance(tableName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return (T)table;
    }
}
