package com.n00ner.newsbuddy.models;

import java.util.Date;

public class NewsItem {
    private String title;
    private String link;
    private String description;
    private String imageUrl;
    private String pubDate;
    private String pubSource;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubSource() {
        return pubSource;
    }

    public void setPubSource(String pubSource) {
        this.pubSource = pubSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public long getPubDateInMillis(){
        return new Date(this.pubDate).getTime();
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
