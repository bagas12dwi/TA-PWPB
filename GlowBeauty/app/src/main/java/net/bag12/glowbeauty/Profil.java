package net.bag12.glowbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profil extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase getDatabase;
    private DatabaseReference database, gwtReference;
    private String GetUserId;
    private TextView tvNama, tvAlamat, tvNo, tvEmail;
    private Button btnSimpan;
    private model_user mdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        tvNama = findViewById(R.id.namaAdmin);
        tvNo = findViewById(R.id.no_hp);
        tvAlamat = findViewById(R.id.alamatUser);
        btnSimpan = findViewById(R.id.btnSimpan);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("user");

        getUserInform();

        final model_user user = new model_user();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mNama = tvNama.getText().toString();
                final String mNo = tvNo.getText().toString();
                final String mAlamat = tvAlamat.getText().toString();
                FirebaseUser usr = firebaseAuth.getCurrentUser();
                GetUserId = usr.getUid();
                database.child("user").child(GetUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        database = FirebaseDatabase.getInstance().getReference();
                        database.child("user").child(GetUserId).child("nama").setValue(mNama);
                        database.child("user").child(GetUserId).child("noHp").setValue(mNo);
                        database.child("user").child(GetUserId).child("alamat").setValue(mAlamat);

                        database.push().setValue(mdUser);
                        Toast.makeText(Profil.this, "Berhasil diupdate!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }


    public void getUserInform(){
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("user");


        FirebaseUser usr = firebaseAuth.getCurrentUser();
        GetUserId = usr.getUid();

        getDatabase = FirebaseDatabase.getInstance();
        gwtReference = getDatabase.getReference();

        gwtReference.child("user").child(GetUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                model_user user = dataSnapshot.getValue(model_user.class);
                String nama = user.getNama();
                String email = user.getEmail();
                String no = user.getNoHp();
                String alamat = user.getAlamat();
                tvNama.setText(nama);
                tvNo.setText(no);
                tvAlamat.setText(alamat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
