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

public class Specialties extends Fragment {

    private EditText edtName;
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
        View view = inflater.inflate(R.layout.fragment_specialties, container, false);
        initializeUI(view);
        btnSave.setOnClickListener(v -> registerNewSpecialty());
        btnCancel.setOnClickListener(v -> getActivity().finish());
        return view;
    }

    private void registerNewSpecialty() {
        progressBar.setVisibility(View.VISIBLE);
        String name = edtName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getContext(), "Por favor ingrese una especialidad...", Toast.LENGTH_LONG).show();
            return;
        }
        progressBar.setVisibility(View.GONE);
    }

    private void initializeUI(View view) {
        edtName = view.findViewById(R.id.spec_name);
        btnSave = view.findViewById(R.id.specialties_save_btn);
        btnCancel = view.findViewById(R.id.specialties_cancel_btn);
        progressBar = view.findViewById(R.id.progressBar);
    }
}