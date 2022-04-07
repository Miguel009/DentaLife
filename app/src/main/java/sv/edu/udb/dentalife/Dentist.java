package sv.edu.udb.dentalife;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import sv.edu.udb.dentalife.adapters.Dentist_Adapter;
import sv.edu.udb.dentalife.databinding.FragmentAppointmentBinding;
import sv.edu.udb.dentalife.models.Dentist_Model;
import sv.edu.udb.dentalife.models.Specialty_Model;

public class Dentist extends Fragment implements Dentist_Adapter.OnDentistListener {

    private ProgressBar progressBar;

    private DatabaseReference dataRef;

    private Dentist_Adapter dentAdapter;
    private RecyclerView recyvlerView;
    private ArrayList<Dentist_Model> dentistList = new ArrayList<>();
    private ArrayList<Specialty_Model> specialtyList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dentist, container, false);
        initializeUI(view);
        recyvlerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataRef = FirebaseDatabase.getInstance().getReference();
        //Obtenemos todos los datos
        getDentistsFromFirebase();
        return view;
    }

    private void getDentistsFromFirebase() {
        progressBar.setVisibility(View.VISIBLE);
        dataRef.child("specialty").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot specialtySnapshot) {
                if (specialtySnapshot.exists()) {
                    specialtyList.clear();
                    //Obtenemos las especialidades
                    for (DataSnapshot ss: specialtySnapshot.getChildren()) {
                        String id = ss.getKey();
                        String name = ss.child("name").getValue().toString();
                        specialtyList.add(new Specialty_Model(id, name));
                    }
                    dataRef.child("dentist").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dentistSnapshot) {
                            if (dentistSnapshot.exists()) {
                                dentistList.clear();
                                for (DataSnapshot ds : dentistSnapshot.getChildren()) {
                                    String id = ds.getKey();
                                    String name = "Dmd. " + ds.child("name").getValue().toString();
                                    String id_specialty = ds.child("id_specialty").getValue().toString().trim();
                                    String img = ds.child("img").getValue().toString();
                                    String specialty = "";
                                    for (int i = 0; i < specialtyList.size(); i++) {
                                        Specialty_Model specialties = specialtyList.get(i);
                                        if (specialties.getId().trim().equals(id_specialty)) {
                                            specialty = specialties.getName();
                                        }
                                    }
                                    dentistList.add(new Dentist_Model(id, name, specialty, img));
                                }
                                dentAdapter = new Dentist_Adapter(dentistList, R.layout.view_dentist, Dentist.this);
                                recyvlerView.setAdapter(dentAdapter);
                                progressBar.setVisibility(View.GONE);
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
        progressBar = view.findViewById(R.id.progressBar);
        recyvlerView = view.findViewById(R.id.item_dentist);
    }

    @Override
    public void onDentistClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("dentist_id", dentistList.get(position).getId());
        bundle.putString("dentist_name", dentistList.get(position).getName());
        bundle.putString("dentist_speciality", dentistList.get(position).getId_specialty());
        bundle.putString("dentist_image", dentistList.get(position).getImg());
        Fragment ap = new appointment();
        ap.setArguments(bundle);
        FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.fragment, ap).commit();
    }
}