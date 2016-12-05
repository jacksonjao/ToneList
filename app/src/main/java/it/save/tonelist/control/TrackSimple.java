package it.save.tonelist.control;

import android.graphics.Bitmap;

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
    protected Bitmap image;
    @Exclude
    protected boolean liked;

    @Exclude
    protected long likes;

    public TrackSimple() {
    }

    public TrackSimple(String trackId, String name, String artist, String album, String imgURL, boolean liked, Bitmap image) {
        this.trackId = trackId;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.imgURL = imgURL;
        this.liked = liked;
        this.image = image;
        this.likes = 0;
    }

    public void cargarImagen() {
        new DownloadImageTask(this).execute(this.imgURL);
    }
}
