package com.example.xiaomao.sqlitebyrx.entityTests;


import com.example.xiaomao.sqlitebyrx.entitis.Province;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ProvinceTest {




    @Test
    public void testString() {
        Province p1=new Province("浙江","ZheJiang",18888);
        System.out.println(p1.toString());
    }

    @Test
    public void testString2() {
        Province p1=new Province("浙江","ZheJiang",18888);
        System.out.println(p1.getJsonString());
        System.out.println(Province.getProvinceFromJsonString(p1.getJsonString()).toString()  );
    }


}