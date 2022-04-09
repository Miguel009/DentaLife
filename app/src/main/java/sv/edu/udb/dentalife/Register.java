package sv.edu.udb.dentalife;

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

import sv.edu.udb.dentalife.models.User_Model;

public class Register extends AppCompatActivity {
    private EditText tvEmail, tvPassword, tvFirstName, tvLastName, tvPhone, tvAddress;
    private Button regBtn, cancelbtn;
    private ProgressBar progressBar;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();

        initializeUI();

        regBtn.setOnClickListener(v -> registerNewUser());
        cancelbtn.setOnClickListener(v -> finish());
    }
    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);

        String email, password, firstName, lastName, phone, address;
        email = tvEmail.getText().toString();
        password = tvPassword.getText().toString();
        firstName = tvFirstName.getText().toString();
        lastName = tvLastName.getText().toString();
        phone = tvPhone.getText().toString();
        address = tvAddress.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Porfavor Ingresa el email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Porfavor ingresa la contraseña!", Toast.LENGTH_LONG).show();
            return;
        }

        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DatabaseReference dataRef;
                        dataRef = FirebaseDatabase.getInstance().getReference("users");
                        dataRef.child(fAuth.getCurrentUser().getUid()).setValue(new User_Model(firstName, lastName, email, phone, address));
                        Toast.makeText(getApplicationContext(), "Usuario Registrado", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);

                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Registro fallido", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
    private void initializeUI() {
        tvEmail = findViewById(R.id.user_email);
        tvPassword = findViewById(R.id.password);
        tvFirstName = findViewById(R.id.user_name);
        tvLastName = findViewById(R.id.user_last_name);
        tvPhone = findViewById(R.id.user_phone);
        tvAddress = findViewById(R.id.user_address);
        regBtn = findViewById(R.id.registerbtn);
        cancelbtn = findViewById(R.id.cancelbtn);
        progressBar = findViewById(R.id.progressBar);
    }
}