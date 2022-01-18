package net.bag12.glowbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase getDatabase;
    private DatabaseReference database, gwtReference;
    private String GetUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.imageView);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        iv.startAnimation(myanim);

        final Intent i = new Intent(this, Login.class);

        Thread timer = new Thread(){
            public void run () {
                try {
                    sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {

                        startActivity(new Intent(MainActivity.this, Login.class));
                        finish();
                    }
            }
        };
        timer.start();
    }
}
