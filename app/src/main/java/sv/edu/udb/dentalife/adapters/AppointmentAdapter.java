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
import sv.edu.udb.dentalife.models.AppointmentModel;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private final int resource;
    private final ArrayList<AppointmentModel> appointment_list;
    private final OnAppointmentListener mOnAppointmentListener;

    public AppointmentAdapter(ArrayList<AppointmentModel> appointment_list, int resource, OnAppointmentListener onAppointmentListener) {
        this.appointment_list = appointment_list;
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
        AppointmentModel appointments = appointment_list.get(position);
        DatabaseReference database_reference = FirebaseDatabase.getInstance().getReference();
        //Aqui agregamos nuevps campos
       holder.tv_date.setText(appointments.getDay()+" "+appointments.getHour());
        //Para cargar las imagenes desde url
        database_reference.child("dentist/"+appointments.getDoctor_id()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot DentistSnapshot) {
                        if (DentistSnapshot.exists()) {
                            holder.tv_name.setText(DentistSnapshot.child("name").getValue().toString());
                            Picasso.get()
                                    .load(DentistSnapshot.child("img").getValue().toString()) //Cargamos recurso
                                    .error(R.mipmap.ic_launcher_round) //Si da error mostramos otra imagen
                                    .into(holder.v_img); //En donde mostraremos la img*/
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
        return appointment_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Aqui declaramos los objetos de las vistas

        private TextView tv_name, tv_date;

        private ImageView v_img;

        public View view;

        OnAppointmentListener onAppointmentListener;

        public ViewHolder(View view, OnAppointmentListener onAppointmentListener) {
            super(view);
            this.view = view;
            this.tv_name = (TextView) view.findViewById(R.id.appointment_dent_name);
            this.tv_date = (TextView) view.findViewById(R.id.appointment_dent_specialty);
            this.v_img = (ImageView) view.findViewById(R.id.appointment_dent_img);
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