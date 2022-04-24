package sv.edu.udb.dentalife.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sv.edu.udb.dentalife.R;

public class HomeActivity extends AppCompatActivity {

    private TextView button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button_login = (TextView) findViewById(R.id.txt_login_home);
        button_login.setOnClickListener(v -> goLoginAccount());
    }
    private void goLoginAccount() {
        Intent login = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
    }

}