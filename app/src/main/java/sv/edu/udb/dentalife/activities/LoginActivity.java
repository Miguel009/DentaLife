package sv.edu.udb.dentalife.activities;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import sv.edu.udb.dentalife.R;


public class LoginActivity extends AppCompatActivity {

    private static final String google_tag = "GoogleActivity";
    private static final int rc_sign_in = 9001;

    private EditText tv_email, tv_password;
    private Button button_login, button_sign_up, button_google_login;
    private ProgressBar progress_bar;

    private FirebaseAuth firebase_auth;

    private GoogleSignInClient m_google_sign_in_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        m_google_sign_in_client = GoogleSignIn.getClient(this, gso);

        firebase_auth = FirebaseAuth.getInstance();

        initializeUI();

        button_login.setOnClickListener(v -> loginUserAccount());
        button_sign_up.setOnClickListener(v -> goToRegister());
        button_google_login.setOnClickListener(v -> googleSignIn());
    }
    private void loginUserAccount() {
        progress_bar.setVisibility(View.VISIBLE);

        String email, password;
        email = tv_email.getText().toString();
        password = tv_password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        firebase_auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                        progress_bar.setVisibility(View.GONE);

                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                        progress_bar.setVisibility(View.GONE);
                    }
                });
    }

    private void goToRegister()
    {
        Intent login = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(login);
    }

    private void googleSignIn()
    {
        Intent signInIntent = m_google_sign_in_client.getSignInIntent();
        startActivityForResult(signInIntent, rc_sign_in);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == rc_sign_in) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(google_tag, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(google_tag, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebase_auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(google_tag, "signInWithCredential:success");
                        FirebaseUser user = firebase_auth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(google_tag, "signInWithCredential:failure", task.getException());
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
            progress_bar.setVisibility(View.GONE);

            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
            progress_bar.setVisibility(View.GONE);
        }
    }

    private void initializeUI() {
        tv_email = findViewById(R.id.email);
        tv_password = findViewById(R.id.password);
        button_sign_up = findViewById(R.id.button_sign_up);
        button_login = findViewById(R.id.button_login);
        button_google_login = findViewById(R.id.button_google_login);
        progress_bar = findViewById(R.id.dentist_progress_bar);
    }
}