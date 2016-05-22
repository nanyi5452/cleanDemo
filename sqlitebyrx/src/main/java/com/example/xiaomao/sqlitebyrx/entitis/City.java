package com.example.xiaomao.sqlitebyrx.entitis;

import com.google.gson.Gson;

/**
 * Created by Administrator on 16-4-18.
 */
public class City {

    private static Gson gson=new Gson();
    public static City getCityFromJsonString(String str){
        City c=gson.fromJson(str,City.class);
        return c;
    }


    private Province p;
    private String name;
    private int population;

    public City(Province p,String name,int population){
        this.p=p;
        this.name=name;
        this.population=population;
    }


    @Override
    public String toString() {
        return p.toString()+"-"+name+"-"+population;
    }

    public String getJsonString(){
        return "{\"p\":"+p.getJsonString()+",\"name\":\""+name+"\",\"population\":"+population+"}";
    }






}
