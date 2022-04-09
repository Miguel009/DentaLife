package sv.edu.udb.dentalife.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import sv.edu.udb.dentalife.R;

public class ContactActivity extends AppCompatActivity {

    private Button button_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        button_close = findViewById(R.id.button_close_option);
        button_close.setOnClickListener(view -> {
            finish();
        });
    }
}