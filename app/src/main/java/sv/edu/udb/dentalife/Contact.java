package sv.edu.udb.dentalife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class Contact extends AppCompatActivity {

    private Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        btnClose = findViewById(R.id.btnBackOptions);
        btnClose.setOnClickListener(view -> {
            finish();
        });
    }
}