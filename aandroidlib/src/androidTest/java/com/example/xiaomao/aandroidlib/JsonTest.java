package com.example.xiaomao.aandroidlib;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.coreDomain.DisplayEntry;
import com.example.xiaomao.utils.mapper.DispEntryJsonMapper;

import java.util.List;

/**
 * Created by Administrator on 16-3-18.
 */
public class JsonTest extends AndroidTestCase {

    final String tag="androidtest";

    public void testJson(){
        DispEntryJsonMapper mapper1=new DispEntryJsonMapper();
        String str1="{\"imageURL\"=\"www.jlu.edu.cn\",\"description\"=\"test\",\"id\"=10}";
        DisplayEntry entry=mapper1.getDisplayEntry(str1);
        Log.i(tag,entry.toString());

    }


    public void testJson2(){
        DispEntryJsonMapper mapper1=new DispEntryJsonMapper();
        String str1="[{\"imageURL\"=\"www.jlu.edu.cn\",\"description\"=\"test\",\"id\"=10}" +
                ",{\"imageURL\"=\"ece.jlu.edu.cn\",\"description\"=\"test2\",\"id\"=11}"+
                ",{\"imageURL\"=\"xx.jlu.edu.cn\",\"description\"=\"test3\",\"id\"=13}]";
        List<DisplayEntry> list=mapper1.getDisplayEntries(str1);
        Log.i(tag,"list length="+list.size()+"--"+list.toString());
    }




}
