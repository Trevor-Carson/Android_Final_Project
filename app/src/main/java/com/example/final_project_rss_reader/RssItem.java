package com.example.final_project_rss_reader;

/**
 * Class to hold information for individual RSS feed items
 */
public class RssItem {

    private String link;        // String to display the URL information for a specified RSS feed
    private String pubDate;     // String to display the date for a specified RSS feed
    private String description; // String to display the description for a specified RSS feed
    private String title;       // String to display the title for a specified RSS feed
    private long id;            // Number to display the id number for a specified RSS feed

    // Parameterized constructor for a RSS feed item
    public RssItem (long id, String title, String description) {
        this.title = title;
        this.id = id;
        this.description = description;
    }

    // Default non parameterized constructor for an RSS feed item
    public RssItem () {
    }

    // Getter method for the RSS URL's link string
    public String getLink() {
        return link;
    }

    // Setter method for the RSS URL's link string
    public void setLink(String link) {
        this.link = link;
    }

    // Getter method for the RSS date string
    public String getPubDate() {
        return pubDate;
    }

    // Setter method for the RSS date string
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    // Getter method for the RSS descriptions string
    public String getDescription() {
        return description;
    }

    // Setter method for the RSS descriptions string
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter method for the RSS titles string
    public String getTitle() {
        return title;
    }

    // Setter method for the RSS titles string
    public void setTitle(String title) {
        this.title = title;
    }

    // Override method for the toString method
    @Override
    public String toString() {
        return this.title;
    }

    // Getter method for the RSS id number
    public long getId() {
        return id;
    }

    // Setter method for the RSS id number
    public void setId(long id) {
        this.id = id;
    }
}
