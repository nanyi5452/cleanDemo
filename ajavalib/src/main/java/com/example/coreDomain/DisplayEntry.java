package com.example.coreDomain;

/**
 * Created by Administrator on 16-3-17.
 */
public class DisplayEntry {

    public static final String emptyURL="emptyURL";
    public static final String emptyDiscription="emptyDis";
    public static final int emptyId=0;
    public static final DisplayEntry emptyEntry=new DisplayEntry(emptyURL,emptyDiscription,emptyId);

    @Override
    public boolean equals(Object obj) {
        DisplayEntry entry=(DisplayEntry)obj;
        return entry.getEntryId()==this.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "entryId:"+id+"  url:"+imageURL+"  description:"+description;
    }

    public DisplayEntry(String imageURL, String description, int id){
        this.description=description;
        this.imageURL=imageURL;
        this.id=id;
    }

    private int id;
    public int getEntryId(){
        return id;
    }
    public void setEntryId(int id) {
        this.id = id;
    }


    private String imageURL;
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }




}
