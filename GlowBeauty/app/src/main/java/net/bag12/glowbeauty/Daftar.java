package net.bag12.glowbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static android.widget.Toast.LENGTH_SHORT;

public class Daftar extends AppCompatActivity {

    private EditText txuser, txpass, txnama, txalamat, txnoHp;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;
    private Button btn_daftar;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        txuser = (EditText) findViewById(R.id.user);
        txpass = (EditText) findViewById(R.id.pass);
        txnama = (EditText) findViewById(R.id.nama);
        txalamat = (EditText) findViewById(R.id.alamat);
        txnoHp = (EditText) findViewById(R.id.no_hp);
        btn_daftar = (Button) findViewById(R.id.btnregister);
        back = (ImageView) findViewById(R.id.back);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("user");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Daftar.this,
                        Login.class));
                finish();
            }
        });

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = txuser.getText().toString().trim();
                String pass2 = txpass.getText().toString().trim();
                String alamat2 = txalamat.getText().toString();
                String noHp2 = txnoHp.getText().toString();
                String nama2 = txnama.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Masukan Nama !!!",
                            LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(alamat2)) {
                    Toast.makeText(getApplicationContext(), "Masukan Alamat !!!",
                            LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(noHp2)) {
                    Toast.makeText(getApplicationContext(), "Masukan No HP !!!",
                            LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(alamat2)) {
                    Toast.makeText(getApplicationContext(), "Masukan Alamat !!!",
                            LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Masukan Email !!!",
                            LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass2)) {
                    Toast.makeText(getApplicationContext(), "Masukan Password !!!",
                            LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nama2)) {
                    Toast.makeText(getApplicationContext(), "Masukan Nama anda !!!",
                            LENGTH_SHORT).show();
                    return;
                }
                if(pass2.length()<5){
                    Toast.makeText(getApplicationContext(),"password minimal 6 karakter", LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, pass2).addOnCompleteListener(Daftar.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    ValueEventListener valueEventListener = database.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            model_user user = new model_user(
                                                    txnama.getText().toString(),
                                                    txalamat.getText().toString(),
                                                    txnoHp.getText().toString(),
                                                    txuser.getText().toString(),
                                                    txpass.getText().toString(),
                                                    "user"

                                            );
                                            database.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Intent i = new Intent(Daftar.this, Halaman_Awal2.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Toast.makeText(getApplicationContext(), "Username atau Password anda salah !!!",
                                                    LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(Daftar.this,"gagal" + task.getException(), LENGTH_SHORT).show();
                                }
                                finish();
                            }
                        });

            }
        });

    }
}
