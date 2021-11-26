package com.example.final_project_rss_reader;

public class RssItem {

    private String link;
    private String pubDate;
    private String description;
    private String title;
    private long id;

    public RssItem (long id, String title, String description) {
        this.title = title;
        this.id = id;
        this.description = description;
    }
    public RssItem () {
    }
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
