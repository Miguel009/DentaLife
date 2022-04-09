package sv.edu.udb.dentalife.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import sv.edu.udb.dentalife.activities.AboutUsActivity;
import sv.edu.udb.dentalife.activities.ContactActivity;
import sv.edu.udb.dentalife.activities.LoginActivity;
import sv.edu.udb.dentalife.activities.ProfileActivity;
import sv.edu.udb.dentalife.R;

public class ItemFragment extends Fragment implements AdapterView.OnItemClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView list_view = (ListView)view.findViewById(R.id.list_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.menu_options));
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                // Lista de opciones del perfil
                Intent profile = new Intent(getActivity(), ProfileActivity.class);
                startActivity(profile);
                break;
            case 1:
                // Acerca de nosotros
                Intent about_us = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(about_us);
                break;
            case 2:
                // Contacto
                Intent contact = new Intent(getActivity(), ContactActivity.class);
                startActivity(contact);
                break;
            case 3:
                FirebaseAuth firebase_auth = FirebaseAuth.getInstance();
                firebase_auth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }
}
