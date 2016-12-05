package it.save.tonelist.control;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void addVoto(final String listCode, final String songCode) {
        listReference.child("lists").child(listCode).child("songs").child(songCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long data = (long) dataSnapshot.getValue();
                listReference.child("lists").child(listCode).child("songs").child(songCode).setValue(data + 1);
                Log.d("vote", "onDataChange: " + data + 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("OnCancelled", "onCancelled: " + databaseError);
            }
        });

    }

}
