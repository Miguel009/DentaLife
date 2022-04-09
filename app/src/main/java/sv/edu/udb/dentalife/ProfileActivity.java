package sv.edu.udb.dentalife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sv.edu.udb.dentalife.models.User_Model;

public class ProfileActivity extends AppCompatActivity {

    private EditText tvEmail, tvFirstName, tvLastName, tvPhone, tvAddress;
    private Button regBtn, cancelbtn;
    private ProgressBar progressBar;
    private DatabaseReference dataRef;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dataRef = FirebaseDatabase.getInstance().getReference("users");
        fAuth = FirebaseAuth.getInstance();
        initializeUI();
        getProfileData();
        regBtn.setOnClickListener(v -> updateProfileData());
        cancelbtn.setOnClickListener(v -> finish());
    }
    private void getProfileData(){
        progressBar.setVisibility(View.VISIBLE);
        dataRef.child(fAuth.getCurrentUser().getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ProfileSnapshot) {
                        if (ProfileSnapshot.exists()) {
                           tvFirstName.setText( ProfileSnapshot.child("first_name").getValue().toString());
                           tvLastName.setText( ProfileSnapshot.child("last_name").getValue().toString());
                           tvEmail.setText( ProfileSnapshot.child("email").getValue().toString());
                           tvPhone.setText( ProfileSnapshot.child("phone").getValue().toString());
                           tvAddress.setText( ProfileSnapshot.child("address").getValue().toString());
                        }
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }

    public void updateProfileData()
    {
        progressBar.setVisibility(View.VISIBLE);
        String email, firstName, lastName, phone, address;
        email = tvEmail.getText().toString();
        firstName = tvFirstName.getText().toString();
        lastName = tvLastName.getText().toString();
        phone = tvPhone.getText().toString();
        address = tvAddress.getText().toString();
        dataRef.child(fAuth.getCurrentUser().getUid()).setValue(new User_Model(firstName, lastName, email, phone, address));
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), "Informacion Actualizada!", Toast.LENGTH_LONG).show();
        finish();
    }

    private void initializeUI() {
        progressBar = findViewById(R.id.profile_progressBar);
        tvEmail = findViewById(R.id.profile_email);
        tvFirstName = findViewById(R.id.profile_name);
        tvLastName = findViewById(R.id.profile_last_name);
        tvPhone = findViewById(R.id.profile_phone);
        tvAddress = findViewById(R.id.profile_address);
        regBtn = findViewById(R.id.updatebtn);
        cancelbtn = findViewById(R.id.cancelbtn);
    }
}