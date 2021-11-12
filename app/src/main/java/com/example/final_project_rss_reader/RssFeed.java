package com.example.final_project_rss_reader;

public class RssFeed {

    public int id;
    public String rssFeedTitle;
    public String rssFeedAddress;

    public RssFeed (String title, String address) {
        rssFeedTitle = title;
        rssFeedAddress = address;
    }

    public RssFeed (int feedId, String title, String address) {
        id = feedId;
        rssFeedTitle = title;
        rssFeedAddress = address;
    }

}
