package sv.edu.udb.dentalife.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import sv.edu.udb.dentalife.R;

public class AboutUsActivity extends AppCompatActivity {

    private Button button_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        button_close = findViewById(R.id.button_close);
        button_close.setOnClickListener(view -> {
            finish();
        });
    }

}