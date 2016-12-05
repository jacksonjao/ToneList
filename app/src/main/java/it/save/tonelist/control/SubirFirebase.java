package it.save.tonelist.control;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Andres Villegas on 2016-12-05.
 */

public class SubirFirebase {
    private FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    private DatabaseReference listReference;

    public SubirFirebase() {
        listReference = firebase.getReference();
    }

    public void addCancion(String listCode, TrackSimple ts) {

        Log.d("addCancion", "inicio de agregar a firebase");
        listReference.child("lists").child(listCode).child("songs").child(ts.trackId).setValue(0);
        listReference.child("songs").child(ts.trackId).setValue(ts);

    }

}
