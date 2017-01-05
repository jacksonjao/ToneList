package it.save.tonelist.control;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Andres Villegas on 2016-12-04.
 */

@IgnoreExtraProperties
public class FiestaSimple implements Serializable {

    public long creationDate;
    public String creator;
    public String name;
    public String imgUrl;
    public String direccion;

    @Exclude
    public String code;

    @Exclude
    public HashMap<String, Integer> songs = new HashMap<>();

    public FiestaSimple() {

    }

}
