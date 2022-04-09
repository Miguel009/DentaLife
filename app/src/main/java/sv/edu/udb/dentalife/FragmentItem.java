package sv.edu.udb.dentalife;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.List;

public class FragmentItem extends Fragment implements AdapterView.OnItemClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] options_menu = {
                "Configurar perfil",
                "Acerca de DentaLife",
                "Contactar con DentaLife",
                "Cerrar sesión"
        };
        ListView listView = (ListView)view.findViewById(R.id.listOptions);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, options_menu);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
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
                Intent aboutUs = new Intent(getActivity(), AboutUs.class);
                startActivity(aboutUs);
                break;
            case 2:
                // Contacto
                Intent contact = new Intent(getActivity(), Contact.class);
                startActivity(contact);
                break;
            case 3:
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                startActivity(new Intent(getActivity(), Login.class));
                break;
        }
    }
}
