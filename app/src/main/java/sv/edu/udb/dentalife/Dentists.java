package sv.edu.udb.dentalife;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Dentists extends Fragment {

    private EditText edtName, edtSpecialty, edtUser, edtImg, edtPassword;
    private Button btnSave, btnCancel;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dentists, container, false);
        initializeUI(view);
        btnSave.setOnClickListener(v -> registerNewDentist());
        btnCancel.setOnClickListener(v -> getActivity().finish());
        return view;
    }

    private void registerNewDentist() {
        progressBar.setVisibility(View.VISIBLE);
        String name = edtName.getText().toString();
        String specialty = edtSpecialty.getText().toString();
        String user = edtUser.getText().toString();
        String img = edtImg.getText().toString();
        String password = edtPassword.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getContext(), "Por favor ingrese el nombre completo...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(specialty)) {
            Toast.makeText(getContext(), "Por favor ingrese una especialidad...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(getContext(), "Por favor ingrese un usuario...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(img)) {
            Toast.makeText(getContext(), "Por favor ingrese una imagen...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Por favor ingrese una contraseña...", Toast.LENGTH_LONG).show();
            return;
        }
        progressBar.setVisibility(View.GONE);
    }

    private void initializeUI(View view) {
        edtName = view.findViewById(R.id.dent_name);
        edtSpecialty = view.findViewById(R.id.dent_specialty);
        edtUser = view.findViewById(R.id.dent_user);
        edtImg = view.findViewById(R.id.dent_img);
        edtPassword = view.findViewById(R.id.dent_password);
        btnSave = view.findViewById(R.id.dentists_save_btn);
        btnCancel = view.findViewById(R.id.dentists_cancel_btn);
        progressBar = view.findViewById(R.id.progressBar);
    }
}