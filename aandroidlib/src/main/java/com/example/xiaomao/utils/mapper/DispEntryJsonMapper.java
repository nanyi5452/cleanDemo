package com.example.xiaomao.utils.mapper;

import com.example.coreDomain.DisplayEntry;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by nanyi545 on 16-3-18.
 *
 * Class used to transform from Strings representing json to valid objects.
 */
public class DispEntryJsonMapper {

    private final Gson gson;

    public DispEntryJsonMapper(){
        this.gson=new Gson();
    }


    /**
     *  Transform from valid json string to {@link com.example.coreDomain.DisplayEntry}.
     *
     * @param displayEntryString
     * @return
     * @throws JsonSyntaxException
     */
    public DisplayEntry getDisplayEntry(String displayEntryString) throws JsonSyntaxException {
        try {
            Type entryType = new TypeToken<DisplayEntry>() {}.getType();
            DisplayEntry retEntry = this.gson.fromJson(displayEntryString, entryType);
            return retEntry;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }


    /**
     * Transform from valid json string to a list of {@link com.example.coreDomain.DisplayEntry}.
     *
     * @param displayEntriesString
     * @return
     * @throws JsonSyntaxException
     */
    public List<DisplayEntry> getDisplayEntries(String displayEntriesString)
            throws JsonSyntaxException {
        List<DisplayEntry> entries;
        try {
            Type listOfEntryes = new TypeToken<List<DisplayEntry>>() {}.getType();
            entries = this.gson.fromJson(displayEntriesString, listOfEntryes);
            return entries;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }




}
