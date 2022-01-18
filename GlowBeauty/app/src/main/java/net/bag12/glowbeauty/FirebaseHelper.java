package net.bag12.glowbeauty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved=null;

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public ArrayList<String> retrive(){
        final ArrayList<String> kategori =new ArrayList<>();
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fetchData(dataSnapshot, kategori);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fetchData(dataSnapshot, kategori);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return kategori;
    }

    private void fetchData(DataSnapshot snapshot,ArrayList<String> kategori){
        kategori.clear();
        for (DataSnapshot ds:snapshot.getChildren()){
            String nama = ds.getValue(Kategori.class).getNama();
            kategori.add(nama);
        }
    }
}
