package com.example.xiaomao.sqlitebyrx.entitis;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 16-4-18.
 */
public class Province {

    private static Gson gson=new Gson();
    public static Province getProvinceFromJsonString(String str){
        try {
            Type entryType = new TypeToken<Province>() {}.getType();
            Province retEntry = gson.fromJson(str, entryType);
            return retEntry;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    private String name_c;
    private String name_e;
    private int population;


    public Province(String name_c,String name_e,int population){
        this.name_c=name_c;
        this.name_e=name_e;
        this.population=population;
    }

    @Override
    public String toString() {
        return name_c+"-"+name_e+"-"+population;
    }

    public String getJsonString(){
        return "{\"name_c\":\""+name_c+"\",\"name_e\":\""+name_e+"\",\"population\":"+population+"}";
    }



}
