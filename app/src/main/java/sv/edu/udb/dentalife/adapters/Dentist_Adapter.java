package sv.edu.udb.dentalife.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sv.edu.udb.dentalife.R;
import sv.edu.udb.dentalife.models.Dentist_Model;

public class Dentist_Adapter extends RecyclerView.Adapter<Dentist_Adapter.ViewHolder> {

    private final int resource;
    private final ArrayList<Dentist_Model> dentistsList;
    private final OnDentistListener mOnDentistListener;
    public Dentist_Adapter(ArrayList<Dentist_Model> dentistsList, int resource, OnDentistListener onDentistListener) {
        this.dentistsList = dentistsList;
        this.resource = resource;
        this.mOnDentistListener = onDentistListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(resource, parent,false);
        return new ViewHolder(view, mOnDentistListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dentist_Model dentists = dentistsList.get(position);
        //Aqui agregamos nuevps campos
        holder.tvName.setText(dentists.getName());
        holder.tvSpecialty.setText(dentists.getId_specialty());
        //Para cargar las imagenes desde url
        Picasso.get()
                .load(dentists.getImg()) //Cargamos recurso
                .error(R.mipmap.ic_launcher_round) //Si da error mostramos otra imagen
                .into(holder.vImg); //En donde mostraremos la img
    }

    @Override
    public int getItemCount() {
        return dentistsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Aqui declaramos los objetos de las vistas

        private TextView tvName, tvSpecialty;

        private ImageView vImg;

        public View view;

        OnDentistListener onDentistListener;

        public ViewHolder(View view, OnDentistListener onDentistListener) {
            super(view);
            this.view = view;
            this.tvName = (TextView) view.findViewById(R.id.dent_name);
            this.tvSpecialty = (TextView) view.findViewById(R.id.dent_specialty);
            this.vImg = (ImageView) view.findViewById(R.id.dent_img);
            this.onDentistListener=onDentistListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDentistListener.onDentistClick(getAdapterPosition());
        }
    }

    public interface OnDentistListener{
        void onDentistClick(int position);
    }
}