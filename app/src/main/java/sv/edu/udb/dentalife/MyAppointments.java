package sv.edu.udb.dentalife;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sv.edu.udb.dentalife.adapters.Appointment_Adapter;
import sv.edu.udb.dentalife.adapters.Dentist_Adapter;
import sv.edu.udb.dentalife.models.Appointment_Model;


public class MyAppointments extends Fragment implements Appointment_Adapter.OnAppointmentListener{

    private ProgressBar progressBar;

    private DatabaseReference dataRef;

    private Appointment_Adapter appointmentAdapter;
    private RecyclerView recyvlerView;
    private ArrayList<Appointment_Model> appointmentList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_appointments, container, false);
        initializeUI(view);
        recyvlerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataRef = FirebaseDatabase.getInstance().getReference();
        getAppointmentsFromFirebase();
        return view;
    }

    private void getAppointmentsFromFirebase() {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.VISIBLE);
        dataRef.child("appointments/"+fAuth.getCurrentUser().getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot AppointmentSnapshot) {
                        if (AppointmentSnapshot.exists()) {
                            appointmentList.clear();
                            for (DataSnapshot ds : AppointmentSnapshot.getChildren()) {
                                String id = ds.getKey();
                                String comment = ds.child("comment").getValue().toString();
                                String day = ds.child("day").getValue().toString();
                                String hour = ds.child("hour").getValue().toString();
                                String doctor_id = ds.child("dentist_id").getValue().toString();
                                appointmentList.add(new Appointment_Model(id, day, hour, comment, doctor_id));
                            }
                            appointmentAdapter = new Appointment_Adapter(appointmentList, R.layout.view_appointments, MyAppointments.this);
                            recyvlerView.setAdapter(appointmentAdapter);
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("Error", error.getMessage());
                    }
                }
        );
    }

    private void initializeUI(View view) {
        progressBar = view.findViewById(R.id.progressBar);
        recyvlerView = view.findViewById(R.id.item_appointments);
        FloatingActionButton fab_agregar= view.findViewById(R.id.fab_agregar);

        fab_agregar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment ap = new Dentist();
                        FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
                        fr.replace(R.id.fragment, ap).commit();
                    }
                }
        );
    }

    @Override
    public void onAppointmentClick(int position) {

    }
}