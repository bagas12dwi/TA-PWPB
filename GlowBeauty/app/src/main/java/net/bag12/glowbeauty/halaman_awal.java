package net.bag12.glowbeauty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.nio.file.Files;

import static net.bag12.glowbeauty.Login.TIME_INTERVAL;

public class halaman_awal extends AppCompatActivity {

    CardView cv_by, cv_Jovie, cv_Fanisa, cv_Pou;
    FloatingActionButton fabLogout;
    private long mBackPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_awal);
        cv_by = (CardView) findViewById(R.id.cv_bylea);
        fabLogout = (FloatingActionButton) findViewById(R.id.btnLogout);
        cv_Jovie = findViewById(R.id.cvJovie);
        cv_Fanisa = findViewById(R.id.cvFanisa);
        cv_Pou = findViewById(R.id.cvPou);

        cv_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(halaman_awal.this, list_masker.class);
                String Merk = "By Lea";
                intent.putExtra("Merk", Merk);
                startActivity(intent);
            }
        });

        cv_Jovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(halaman_awal.this, list_masker.class);
                String Merk = "Jovie";
                intent.putExtra("Merk", Merk);
                startActivity(intent);
            }
        });

        cv_Fanisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(halaman_awal.this, list_masker.class);
                String Merk = "Fanisa";
                intent.putExtra("Merk", Merk);
                startActivity(intent);
            }
        });

        cv_Pou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(halaman_awal.this, list_masker.class);
                String Merk = "Pou Pou";
                intent.putExtra("Merk", Merk);
                startActivity(intent);
            }
        });

        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(halaman_awal.this, Login.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            return;
        }
        else {
            Toast.makeText(getBaseContext(), "Tekan Sekali Lagi Untuk Keluar!", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }
}
