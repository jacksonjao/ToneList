package it.save.tonelist.control;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by Andres Villegas on 2016-12-04.
 */

@IgnoreExtraProperties
public class FiestaSimple {

    protected long creationDate;
    protected String creator;
    protected String name;
    protected String imgUrl;
    protected String direccion;

    @Exclude
    protected String code;

    @Exclude
    protected HashMap<String, Integer> songs = new HashMap<>();

    public FiestaSimple() {

    }

}
