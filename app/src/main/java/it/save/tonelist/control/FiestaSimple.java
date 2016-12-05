package it.save.tonelist.control;

import android.graphics.Bitmap;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Andres Villegas on 2016-12-04.
 */
@IgnoreExtraProperties
public class FiestaSimple {

    protected long creationDate;
    protected String creator;
    protected String name;
    protected Bitmap img;
    protected String code;

}
