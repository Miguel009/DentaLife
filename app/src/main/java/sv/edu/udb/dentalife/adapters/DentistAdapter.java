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
import sv.edu.udb.dentalife.models.DentistModel;

public class DentistAdapter extends RecyclerView.Adapter<DentistAdapter.ViewHolder> {

    private final int resource;
    private final ArrayList<DentistModel> dentists_list;
    private final OnDentistListener mOnDentistListener;
    public DentistAdapter(ArrayList<DentistModel> dentists_list, int resource, OnDentistListener onDentistListener) {
        this.dentists_list = dentists_list;
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
        DentistModel dentists = dentists_list.get(position);
        //Aqui agregamos nuevps campos
        holder.tv_name.setText(dentists.getName());
        holder.tv_specialty.setText(dentists.getId_specialty());
        //Para cargar las imagenes desde url
        Picasso.get()
                .load(dentists.getImg()) //Cargamos recurso
                .error(R.mipmap.ic_launcher_round) //Si da error mostramos otra imagen
                .into(holder.v_img); //En donde mostraremos la img
    }

    @Override
    public int getItemCount() {
        return dentists_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Aqui declaramos los objetos de las vistas

        private TextView tv_name, tv_specialty;

        private ImageView v_img;

        public View view;

        OnDentistListener onDentistListener;

        public ViewHolder(View view, OnDentistListener onDentistListener) {
            super(view);
            this.view = view;
            this.tv_name = (TextView) view.findViewById(R.id.dent_name);
            this.tv_specialty = (TextView) view.findViewById(R.id.dent_specialty);
            this.v_img = (ImageView) view.findViewById(R.id.dent_img);
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