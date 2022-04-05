package sv.edu.udb.dentalife;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sv.edu.udb.dentalife.adapters.Dentist_Adapter;
import sv.edu.udb.dentalife.models.Dentist_Model;

public class Dentist extends Fragment {

    private ImageView vwImg;
    private TextView tvName, tvSpecialty;
    private ProgressBar progressBar;

    private DatabaseReference dataRef;

    private Dentist_Adapter dentAdapter;
    private RecyclerView recyvlerView;
    private ArrayList<Dentist_Model> dentistList = new ArrayList<>();

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


    private String specialty;
    private String newNameSpecialty = "";

    private void getDentistsFromFirebase() {
        progressBar.setVisibility(View.VISIBLE);
        //Obtenemos la tabla dentist y sus hijos
        dataRef.child("dentist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //Obtenemos los datos de los hijos
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        String name = "Dmd. " + ds.child("name").getValue().toString();
                        specialty = ds.child("id_specialty").getValue().toString();
                        //Obtenemos la especialidad del dentista
                        dataRef.child("specialty").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshotspec) {
                                if (snapshotspec.exists()) {
                                    //Obtenemos los datos de los hijos
                                    for (DataSnapshot dsspec : snapshotspec.getChildren()) {
                                        if (dsspec.getValue().toString() == specialty) {
                                            newNameSpecialty = "Especialista en " + dsspec.child("name").getValue().toString();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        //Agregamos los datos encontrados al view List
                        if (newNameSpecialty.trim().equals("")) {
                            dentistList.add(new Dentist_Model(name, ""));
                        }
                        else {
                            dentistList.add(new Dentist_Model(name, newNameSpecialty));
                        }
                    }

                    dentAdapter = new Dentist_Adapter(dentistList, R.layout.view_dentist);
                    recyvlerView.setAdapter(dentAdapter);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializeUI(View view) {
        tvName = view.findViewById(R.id.dent_name);
        tvSpecialty = view.findViewById(R.id.dent_specialty);
        vwImg = view.findViewById(R.id.dent_img);
        progressBar = view.findViewById(R.id.progressBar);
        recyvlerView = view.findViewById(R.id.item_dentist);
    }
}