package com.example.final_project_rss_reader;

import androidx.annotation.NonNull;

/**
 * Class to hold information for individual RSS feed items
 */
public class RssItem {

    /** String to display the URL information for a specified RSS feed */
    private String link;
    /** String to display the date for a specified RSS feed */
    private String pubDate;
    /** String to display the description for a specified RSS feed */
    private String description;
    /** String to display the title for a specified RSS feed */
    private String title;
    /** Number to display the id number for a specified RSS feed */
    private long id;

    /**
     * Parameterized constructor for a RSS feed item
     * @param id - Variable to store the id number for the RSS feed
     * @param title - String to hold the title of the RSS feed
     * @param description - String to hold the description of the RSS feed
     */
    public RssItem (long id, String title, String description) {
        this.title = title;
        this.id = id;
        this.description = description;
    }

    /**
     * Default non parameterized constructor for an RSS feed item
     */
    public RssItem () {
    }

    public RssItem(long id, String title, String description, String link, String date) {
        this.title = title;
        this.id = id;
        this.description = description;
        this.link = link;
        this.pubDate = date;
    }

    /**
     * Getter method for the RSS URL's link String
     * @return - RSS URL's link String
     */
    public String getLink() {
        return link;
    }

    /**
     * Setter method for the RSS URL's link String
     * @param link - Sets the value of the URL link String
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Getter method for the RSS date String
     * @return - RSS date string
     */
    public String getPubDate() {
        return pubDate;
    }

    /**
     * Setter method for the RSS date String
     * @param pubDate - Sets the RSS date String
     */
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * Getter method for the RSS description String
     * @return - RSS description string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for the RSS descriptions String
     * @param description - Sets the RSS description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for the RSS title String
     * @return - RSS title String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter method for the RSS title String
     * @param title - Sets the RSS title String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Override method for the toString method
     * @return - passed in value to a type String
     */
    @Override
    public String toString() {
        return this.title;
    }

    /**
     * Getter method for the RSS ID number
     * @return - RSS ID number
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for the RSS ID number
     * @param id - Sets the RSS ID value
     */
    public void setId(long id) {
        this.id = id;
    }
}
