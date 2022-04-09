package sv.edu.udb.dentalife.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sv.edu.udb.dentalife.R;
import sv.edu.udb.dentalife.adapters.DentistAdapter;
import sv.edu.udb.dentalife.models.DentistModel;
import sv.edu.udb.dentalife.models.SpecialtyModel;

public class DentistFragment extends Fragment implements DentistAdapter.OnDentistListener {

    private ProgressBar dentist_progress_bar;

    private DatabaseReference database_reference;

    private DentistAdapter dentist_adapter;
    private RecyclerView recyvler_view;
    private ArrayList<DentistModel> dentist_list = new ArrayList<>();
    private ArrayList<SpecialtyModel> specialty_list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dentist, container, false);
        initializeUI(view);
        recyvler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        database_reference = FirebaseDatabase.getInstance().getReference();
        //Obtenemos todos los datos
        getDentistsFromFirebase();
        return view;
    }

    private void getDentistsFromFirebase() {
        dentist_progress_bar.setVisibility(View.VISIBLE);
        database_reference.child("specialty").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot specialtySnapshot) {
                if (specialtySnapshot.exists()) {
                    specialty_list.clear();
                    //Obtenemos las especialidades
                    for (DataSnapshot ss: specialtySnapshot.getChildren()) {
                        String id = ss.getKey();
                        String name = ss.child("name").getValue().toString();
                        specialty_list.add(new SpecialtyModel(id, name));
                    }
                    database_reference.child("dentist").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dentistSnapshot) {
                            if (dentistSnapshot.exists()) {
                                dentist_list.clear();
                                for (DataSnapshot ds : dentistSnapshot.getChildren()) {
                                    String id = ds.getKey();
                                    String name = "Dmd. " + ds.child("name").getValue().toString();
                                    String id_specialty = ds.child("id_specialty").getValue().toString().trim();
                                    String img = ds.child("img").getValue().toString();
                                    String specialty = "";
                                    for (int i = 0; i < specialty_list.size(); i++) {
                                        SpecialtyModel specialties = specialty_list.get(i);
                                        if (specialties.getId().trim().equals(id_specialty)) {
                                            specialty = specialties.getName();
                                        }
                                    }
                                    dentist_list.add(new DentistModel(id, name, specialty, img));
                                }
                                dentist_adapter = new DentistAdapter(dentist_list, R.layout.view_dentist, DentistFragment.this);
                                recyvler_view.setAdapter(dentist_adapter);
                                dentist_progress_bar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("Error", error.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error", error.getMessage());
            }
        });
    }

    private void initializeUI(View view) {
        dentist_progress_bar = view.findViewById(R.id.dentist_progress_bar);
        recyvler_view = view.findViewById(R.id.item_dentist);
    }

    @Override
    public void onDentistClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("dentist_id", dentist_list.get(position).getId());
        bundle.putString("dentist_name", dentist_list.get(position).getName());
        bundle.putString("dentist_speciality", dentist_list.get(position).getId_specialty());
        bundle.putString("dentist_image", dentist_list.get(position).getImg());
        Fragment ap = new NewAppointmentFragment();
        ap.setArguments(bundle);
        FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.fragment, ap).commit();
    }
}