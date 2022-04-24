package sv.edu.udb.dentalife.activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sv.edu.udb.dentalife.R;
import sv.edu.udb.dentalife.models.UserModel;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_email, et_password, et_first_name, et_last_name, et_phone, et_address;
    private Button button_register, button_cancel;
    private ProgressBar register_progress_bar;

    private FirebaseAuth firebase_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebase_auth = FirebaseAuth.getInstance();

        initializeUI();

        button_register.setOnClickListener(v -> registerNewUser());
        button_cancel.setOnClickListener(v -> finish());
    }
    private void registerNewUser() {
        register_progress_bar.setVisibility(View.VISIBLE);

        String email, password, firstName, lastName, phone, address;
        email = et_email.getText().toString();
        password = et_password.getText().toString();
        firstName = et_first_name.getText().toString();
        lastName = et_last_name.getText().toString();
        phone = et_phone.getText().toString();
        address = et_address.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Porfavor Ingresa el email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Porfavor ingresa la contraseña!", Toast.LENGTH_LONG).show();
            return;
        }

        firebase_auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DatabaseReference dataRef;
                        dataRef = FirebaseDatabase.getInstance().getReference("users");
                        dataRef.child(firebase_auth.getCurrentUser().getUid()).setValue(new UserModel(firstName, lastName, email, phone, address));
                        Toast.makeText(getApplicationContext(), "Usuario Registrado", Toast.LENGTH_LONG).show();
                        register_progress_bar.setVisibility(View.GONE);

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Registro fallido", Toast.LENGTH_LONG).show();
                        register_progress_bar.setVisibility(View.GONE);
                    }
                });
    }
    private void initializeUI() {
        et_email = findViewById(R.id.user_email);
        et_password = findViewById(R.id.password);
        et_first_name = findViewById(R.id.user_name);
        et_last_name = findViewById(R.id.user_last_name);
        et_phone = findViewById(R.id.user_phone);
        et_address = findViewById(R.id.user_address);
        button_register = findViewById(R.id.registerbtn);
        button_cancel = findViewById(R.id.button_cancel);
        register_progress_bar = findViewById(R.id.register_progress_bar);
    }
}