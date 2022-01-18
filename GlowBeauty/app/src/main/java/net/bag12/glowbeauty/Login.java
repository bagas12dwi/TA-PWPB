package net.bag12.glowbeauty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    public static final int TIME_INTERVAL = 2000;
    private long mBackpressed;

    EditText txEmail, txPass;
    Button btnLogin;
    TextView tvRegister;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase getDatabase;
    private DatabaseReference database, gwtReference;
    private String GetUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("user");

        FirebaseUser usr = firebaseAuth.getCurrentUser();

        getDatabase = FirebaseDatabase.getInstance();
        gwtReference = getDatabase.getReference();

        if (firebaseAuth.getCurrentUser() != null) {
            GetUserId = usr.getUid();
            gwtReference.child("user").child(GetUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    model_user user = dataSnapshot.getValue(model_user.class);
                    String level = user.getLevel();
                    if (level.equals("admin")) {
                        Intent i = new Intent(Login.this, admin_page.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(Login.this, Halaman_Awal2.class);
                        startActivity(i);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        txEmail = (EditText) findViewById(R.id.editUser);
        txPass = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        tvRegister = (TextView) findViewById(R.id.txtdaftar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        database = FirebaseDatabase.getInstance().getReference("user");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txEmail.getText().toString().trim();
                String pass = txPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Masukkan alamat Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    Toast.makeText(Login.this, "Masukkan Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.length()<5){
                    Toast.makeText(getApplicationContext(),"password minimal 6 karakter", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull final Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                ValueEventListener valueEventListener = database.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        model_user user = dataSnapshot.getValue(model_user.class);
                                        String level = user.getLevel();
                                        if (task.isSuccessful()){
                                            if (level.equals("admin")){
                                                Intent intent = new Intent(Login.this, admin_page.class);
                                                startActivity(intent);
                                                finish();
                                            }else {
                                                Intent i = new Intent(Login.this, Halaman_Awal2.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }else{
                                            Toast.makeText(Login.this, "Email atau password anda salah !", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Daftar.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mBackpressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(getBaseContext(), "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }
        mBackpressed = System.currentTimeMillis();
    }
}
