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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sv.edu.udb.dentalife.R;
import sv.edu.udb.dentalife.adapters.AppointmentAdapter;
import sv.edu.udb.dentalife.models.AppointmentModel;


public class MyAppointmentsFragment extends Fragment implements AppointmentAdapter.OnAppointmentListener{

    private ProgressBar my_appointments_progress_bar;
    FloatingActionButton fab_add;
    private DatabaseReference database_reference;

    private AppointmentAdapter appointment_adapter;
    private RecyclerView recyvler_view;
    private ArrayList<AppointmentModel> appointment_list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_appointments, container, false);
        initializeUI(view);
        recyvler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        database_reference = FirebaseDatabase.getInstance().getReference();
        getAppointmentsFromFirebase();
        fab_add.setOnClickListener(v -> goToDentistFragment());
        return view;
    }

    private void getAppointmentsFromFirebase() {
        FirebaseAuth firebase_auth = FirebaseAuth.getInstance();

        my_appointments_progress_bar.setVisibility(View.VISIBLE);
        database_reference.child("appointments/"+firebase_auth.getCurrentUser().getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot AppointmentSnapshot) {
                        if (AppointmentSnapshot.exists()) {
                            appointment_list.clear();
                            for (DataSnapshot ds : AppointmentSnapshot.getChildren()) {
                                String id = ds.getKey();
                                String comment = ds.child("comment").getValue().toString();
                                String day = ds.child("day").getValue().toString();
                                String hour = ds.child("hour").getValue().toString();
                                String doctor_id = ds.child("dentist_id").getValue().toString();
                                appointment_list.add(new AppointmentModel(id, day, hour, comment, doctor_id));
                            }
                            appointment_adapter = new AppointmentAdapter(appointment_list, R.layout.view_appointments, MyAppointmentsFragment.this);
                            recyvler_view.setAdapter(appointment_adapter);
                        }
                        my_appointments_progress_bar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("Error", error.getMessage());
                    }
                }
        );
    }

    private void initializeUI(View view) {
        my_appointments_progress_bar = view.findViewById(R.id.my_appointments_progress_bar);
        recyvler_view = view.findViewById(R.id.item_appointments);
        fab_add= view.findViewById(R.id.fab_add);
    }

    private void goToDentistFragment()
    {
        Fragment ap = new DentistFragment();
        FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.fragment, ap).commit();
    }

    @Override
    public void onAppointmentClick(int position) {

    }
}