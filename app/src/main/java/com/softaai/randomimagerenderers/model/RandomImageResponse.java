package com.softaai.randomimagerenderers.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amol Pawar on 15-05-2019.
 * softAai Apps
 */
public class RandomImageResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("download_url")
    @Expose
    private String downloadUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }


    @Override public boolean equals(Object obj) {
        if (obj instanceof RandomImageResponse) {
            RandomImageResponse other = (RandomImageResponse) obj;
            return id.equals(other.id)
                    && author.equals(other.author)
                    && width == other.width
                    && height == other.height
                    && url == other.url
                    && downloadUrl == other.downloadUrl;
        } else {
            return false;
        }
    }


//    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }

}