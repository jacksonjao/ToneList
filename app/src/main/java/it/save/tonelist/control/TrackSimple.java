package it.save.tonelist.control;

import com.google.firebase.database.Exclude;

/**
 * Created by Andres Villegas on 2016-11-25.
 */

public class TrackSimple {

    protected String trackId;
    protected String name;
    protected String artist;
    protected String album;
    protected String imgURL;
    @Exclude
    protected boolean liked;

    public TrackSimple() {
    }

    public TrackSimple(String trackId, String name, String artist, String album, String imgURL, boolean liked) {
        this.trackId = trackId;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.imgURL = imgURL;
        this.liked = liked;
    }
}