package it.save.tonelist.control;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

/**
 * Created by Andres Villegas on 2016-11-25.
 */

public class TrackSimple implements Serializable {

    public String trackId;
    public String name;
    public String artist;
    public String album;
    public String imgURL;

    @Exclude
    public boolean liked;

    @Exclude
    public long likes;

    public TrackSimple() {
    }

    public TrackSimple(String trackId, String name, String artist, String album, String imgURL, boolean liked) {
        this.trackId = trackId;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.imgURL = imgURL;
        this.liked = liked;

        this.likes = 0;
    }


}
