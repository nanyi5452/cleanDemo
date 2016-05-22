package com.example.xiaomao.sqlitebyrx.entityTests;


import com.example.xiaomao.sqlitebyrx.entitis.City;
import com.example.xiaomao.sqlitebyrx.entitis.Province;
import com.google.gson.Gson;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class CityTest {


    @Test
    public void testGson() {
        Province p1=new Province("浙江","ZheJiang",18888);
        System.out.println(p1.toString());

        City c1=new City(p1,"杭州",343342);
        System.out.println(c1.toString());

        Gson gson = new Gson();
        System.out.println("p1 to Json String:"+gson.toJson(p1));
        System.out.println("c1 to Json String:"+gson.toJson(c1));
        System.out.println("c1 to Json String:"+c1.getJsonString());

        City c2=gson.fromJson("{\"p\":{\"name_c\":\"浙江\",\"name_e\":\"ZheJiang\",\"population\":18888},\"name\":\"温州\",\"population\":4342}",City.class);
        System.out.println("c2:"+c2.toString());

    }



}