package sv.edu.udb.dentalife.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import sv.edu.udb.dentalife.R;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler.postDelayed(() -> {
            Intent login = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(login);
            finish();
        }, 2000);
    }
}