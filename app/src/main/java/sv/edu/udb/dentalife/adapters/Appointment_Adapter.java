package sv.edu.udb.dentalife.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sv.edu.udb.dentalife.R;
import sv.edu.udb.dentalife.models.Appointment_Model;

public class Appointment_Adapter extends RecyclerView.Adapter<Appointment_Adapter.ViewHolder> {

    private final int resource;
    private final ArrayList<Appointment_Model> appointmentList;
    private final OnAppointmentListener mOnAppointmentListener;

    public Appointment_Adapter(ArrayList<Appointment_Model> appointmentList, int resource, OnAppointmentListener onAppointmentListener) {
        this.appointmentList = appointmentList;
        this.resource = resource;
        this.mOnAppointmentListener = onAppointmentListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(resource, parent,false);
        return new ViewHolder(view, mOnAppointmentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment_Model appointments = appointmentList.get(position);
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
        //Aqui agregamos nuevps campos
       holder.tvDate.setText(appointments.getDay()+" "+appointments.getHour());
        //Para cargar las imagenes desde url
        dataRef.child("dentist/"+appointments.getDoctor_id()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot DentistSnapshot) {
                        if (DentistSnapshot.exists()) {
                            holder.tvName.setText(DentistSnapshot.child("name").getValue().toString());
                            Picasso.get()
                                    .load(DentistSnapshot.child("img").getValue().toString()) //Cargamos recurso
                                    .error(R.mipmap.ic_launcher_round) //Si da error mostramos otra imagen
                                    .into(holder.vImg); //En donde mostraremos la img*/
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Aqui declaramos los objetos de las vistas

        private TextView tvName, tvDate;

        private ImageView vImg;

        public View view;

        OnAppointmentListener onAppointmentListener;

        public ViewHolder(View view, OnAppointmentListener onAppointmentListener) {
            super(view);
            this.view = view;
            this.tvName = (TextView) view.findViewById(R.id.appointment_dent_name);
            this.tvDate = (TextView) view.findViewById(R.id.appointment_dent_specialty);
            this.vImg = (ImageView) view.findViewById(R.id.appointment_dent_img);
            this.onAppointmentListener = onAppointmentListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAppointmentListener.onAppointmentClick(getAdapterPosition());
        }
    }

    public interface OnAppointmentListener {
        void onAppointmentClick(int position);
    }
}