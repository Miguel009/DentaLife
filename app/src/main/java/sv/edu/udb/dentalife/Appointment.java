package sv.edu.udb.dentalife;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sv.edu.udb.dentalife.models.Appointment_Model;

public class Appointment extends Fragment {

    TextView dentist_name, dentist_speciality;
    ImageView dentist_img;
    Spinner spinner_dates, spinner_hours;
    EditText comments;
    String dentist_id;
    private DatabaseReference dataRef;

    ArrayList<String> day_list = new ArrayList<String>();
    ArrayList<String> hour_list = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_appointment, container, false);
       dentist_name = view.findViewById(R.id.dentist_name);
       dentist_speciality = view.findViewById(R.id.dentist_speciality);
       dentist_img = view.findViewById(R.id.dentist_image);
       spinner_dates = view.findViewById(R.id.spinner_dates);
       spinner_hours = view.findViewById(R.id.spinner_hours);
       comments = view.findViewById(R.id.edit_text);
       Bundle bundle = this.getArguments();
        assert bundle != null;
        dentist_id =bundle.getString("dentist_id");
        String name =bundle.getString("dentist_name");
        String img =bundle.getString("dentist_image");
        String speciality =bundle.getString("dentist_speciality");
        dentist_name.setText(name);
        dentist_speciality.setText(speciality);
        Picasso.get()
                .load(img) //Cargamos recurso
                .error(R.mipmap.ic_launcher_round) //Si da error mostramos otra imagen
                .into(dentist_img); //En donde mostraremos la img
        dataRef = FirebaseDatabase.getInstance().getReference();
        dataRef.child("schedule/"+dentist_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot scheduleSnapshot) {
                    if (scheduleSnapshot.exists()) {
                        day_list.clear();
                        for (DataSnapshot ss: scheduleSnapshot.getChildren()) {
                            String id = ss.getKey();
                            day_list.add(id);
                        }
                        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, day_list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_dates.setAdapter(adapter);

                        spinner_dates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                dataRef.child("schedule/"+dentist_id+"/"+parent.getItemAtPosition(position).toString()+"/hours").addValueEventListener(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot hourSnapshot) {
                                                hour_list.clear();
                                                if (hourSnapshot.exists()) {
                                                    for (DataSnapshot sd: hourSnapshot.getChildren()) {
                                                        String value = sd.getValue().toString();
                                                        hour_list.add(value);
                                                    }
                                                    ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, hour_list);
                                                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    spinner_hours.setAdapter(adapter2);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        }
                                );
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Error", error.getMessage());
                }
            }
        );

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                String comment = comments.getText().toString();
                String day = spinner_dates.getSelectedItem().toString();
                String hour = spinner_hours.getSelectedItem().toString();
                Map<String, Object> new_appointment = new HashMap<>();
                new_appointment.put("day", day);
                new_appointment.put("hour", hour);
                new_appointment.put("comment", comment);
                new_appointment.put("dentist_id", dentist_id);
                dataRef = FirebaseDatabase.getInstance().getReference();
                dataRef.child("appointments/"+fAuth.getCurrentUser().getUid()).push().setValue(new_appointment);
                Handler handler = new Handler();
                handler.postDelayed(runnable, 2000);
            }
        });


        return view;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Fragment ap = new MyAppointments();
            FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.fragment, ap).commit();
        }
    };
}