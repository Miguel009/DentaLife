package sv.edu.udb.dentalife.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sv.edu.udb.dentalife.R;
import sv.edu.udb.dentalife.models.UserModel;

public class ProfileActivity extends AppCompatActivity {

    private EditText et_first_name, et_last_name, et_phone, et_address;
    private TextView tv_email;
    private Button button_update, button_cancel;
    private ProgressBar profile_progress_bar;
    private DatabaseReference database_reference;
    private FirebaseAuth firebase_auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        database_reference = FirebaseDatabase.getInstance().getReference("users");
        firebase_auth = FirebaseAuth.getInstance();
        initializeUI();
        getProfileData();
        button_update.setOnClickListener(v -> updateProfileData());
        button_cancel.setOnClickListener(v -> finish());
    }
    private void getProfileData(){
        profile_progress_bar.setVisibility(View.VISIBLE);
        database_reference.child(firebase_auth.getCurrentUser().getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ProfileSnapshot) {
                        if (ProfileSnapshot.exists()) {
                           et_first_name.setText( ProfileSnapshot.child("first_name").getValue().toString());
                           et_last_name.setText( ProfileSnapshot.child("last_name").getValue().toString());
                           tv_email.setText( ProfileSnapshot.child("email").getValue().toString());
                           et_phone.setText( ProfileSnapshot.child("phone").getValue().toString());
                           et_address.setText( ProfileSnapshot.child("address").getValue().toString());
                        }
                        profile_progress_bar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Error"+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void updateProfileData()
    {
        profile_progress_bar.setVisibility(View.VISIBLE);
        String email, firstName, lastName, phone, address;
        email = tv_email.getText().toString();
        firstName = et_first_name.getText().toString();
        lastName = et_last_name.getText().toString();
        phone = et_phone.getText().toString();
        address = et_address.getText().toString();
        if (email.isEmpty() && firstName.isEmpty() && lastName.isEmpty() && phone.isEmpty() && address.isEmpty())
        {
            profile_progress_bar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
        }
        else
        {
            database_reference.child(firebase_auth.getCurrentUser().getUid()).setValue(new UserModel(firstName, lastName, email, phone, address));
            profile_progress_bar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Informacion Actualizada!", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void initializeUI() {
        profile_progress_bar = findViewById(R.id.profile_progress_bar);
        tv_email = findViewById(R.id.profile_email);
        et_first_name = findViewById(R.id.profile_name);
        et_last_name = findViewById(R.id.profile_last_name);
        et_phone = findViewById(R.id.profile_phone);
        et_address = findViewById(R.id.profile_address);
        button_update = findViewById(R.id.button_update);
        button_cancel = findViewById(R.id.button_cancel);
    }
}