package com.example.newstation.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "sport")
@TypeConverters({DateConverter.class})
public class SportTable {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "author")
    private String mAuthor;
    @ColumnInfo(name = "title")
    private String mTitle;
    @ColumnInfo(name = "description")
    private String mDescription;
    @ColumnInfo(name = "url")
    private String mUrl;
    @ColumnInfo(name = "urlToImage")
    private String mUrlToImage;
    @ColumnInfo(name = "publisherAt")
    private String mPublisherAt;

    public SportTable (String mAuthor, String mTitle, String mDescription, String mUrl, String mUrlToImage,String mPublisherAt){
        this.mAuthor = mAuthor;
        this.mDescription = mDescription;
        this.mPublisherAt  = mPublisherAt;
        this.mUrl = mUrl;
        this.mUrlToImage = mUrlToImage;
        this.mTitle = mTitle;
    }

    public String getPublisherAt() {
        return mPublisherAt;
    }

    public void setPublisherAt(String mPublisherAt) {
        this.mPublisherAt = mPublisherAt;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(String mUrlToImage) {
        this.mUrlToImage = mUrlToImage;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
