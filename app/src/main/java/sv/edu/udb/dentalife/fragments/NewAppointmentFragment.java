package sv.edu.udb.dentalife.fragments;

import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sv.edu.udb.dentalife.R;
import sv.edu.udb.dentalife.models.NewAppointmentModel;

public class NewAppointmentFragment extends Fragment {

    TextView tv_dentist_name, tv_dentist_speciality;
    ImageView dentist_img;
    Spinner spinner_dates, spinner_hours;
    EditText et_comments;
    String dentist_id;
    Button button_appointment;
    ArrayList<String> day_list = new ArrayList<String>();
    ArrayList<String> hour_list = new ArrayList<String>();
    private DatabaseReference database_reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        initializeUI(view);
        getDentistData();
        button_appointment.setOnClickListener(v -> createNewAppointment());


        return view;
    }

    private void getDentistData() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        dentist_id = bundle.getString("dentist_id");
        String name = bundle.getString("dentist_name");
        String img = bundle.getString("dentist_image");
        String speciality = bundle.getString("dentist_speciality");
        tv_dentist_name.setText(name);
        tv_dentist_speciality.setText(speciality);
        Picasso.get()
                .load(img) //Cargamos recurso
                .error(R.mipmap.ic_launcher_round) //Si da error mostramos otra imagen
                .into(dentist_img); //En donde mostraremos la img
        database_reference = FirebaseDatabase.getInstance().getReference();
        database_reference.child("schedule/" + dentist_id).addValueEventListener(new ValueEventListener() {

             @Override
             public void onDataChange(@NonNull DataSnapshot scheduleSnapshot) {
                 if (scheduleSnapshot.exists()) {
                     day_list.clear();
                     for (DataSnapshot ss : scheduleSnapshot.getChildren()) {
                         String id = ss.getKey();
                         day_list.add(id);
                     }
                     ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, day_list);
                     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                     spinner_dates.setAdapter(adapter);

                     spinner_dates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                         @Override
                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                             database_reference.child("schedule/" + dentist_id + "/" + parent.getItemAtPosition(position).toString() + "/hours").addValueEventListener(
                                     new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot hourSnapshot) {
                                             hour_list.clear();
                                             if (hourSnapshot.exists()) {
                                                 for (DataSnapshot sd : hourSnapshot.getChildren()) {
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
    }

    private void goToMyAppointmentsFragment() {
        Fragment ap = new MyAppointmentsFragment();
        FragmentTransaction fr = requireActivity().getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.fragment, ap).commit();
    }

    private void createNewAppointment()
    {
        FirebaseAuth firebase_auth = FirebaseAuth.getInstance();
        String comment = et_comments.getText().toString();
        String day = spinner_dates.getSelectedItem().toString();
        String hour = spinner_hours.getSelectedItem().toString();

        database_reference = FirebaseDatabase.getInstance().getReference();
        database_reference.child("appointments/" + firebase_auth.getCurrentUser().getUid()).push().setValue(new NewAppointmentModel(day, hour, comment, dentist_id));
        goToMyAppointmentsFragment();
    }

    private void initializeUI(View view) {
        tv_dentist_name = view.findViewById(R.id.dentist_name);
        tv_dentist_speciality = view.findViewById(R.id.dentist_speciality);
        dentist_img = view.findViewById(R.id.dentist_image);
        spinner_dates = view.findViewById(R.id.spinner_dates);
        spinner_hours = view.findViewById(R.id.spinner_hours);
        et_comments = view.findViewById(R.id.edit_text);
        button_appointment = view.findViewById(R.id.button_appointment);
    }
}