package sv.edu.udb.dentalife.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sv.edu.udb.dentalife.R;
import sv.edu.udb.dentalife.adapters.AppointmentAdapter;
import sv.edu.udb.dentalife.adapters.MessageAdapter;
import sv.edu.udb.dentalife.models.AppointmentModel;
import sv.edu.udb.dentalife.models.MessageModel;
import sv.edu.udb.dentalife.models.NewAppointmentModel;

public class MessagesFragment extends Fragment implements MessageAdapter.OnMessageListener {

    private DatabaseReference database_reference;
    private MessageAdapter message_adapter;
    private RecyclerView recyvler_view;
    private EditText tv_message;
    private Button button_send;

    private ArrayList<MessageModel> message_list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        initializeUI(view);
        recyvler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        database_reference = FirebaseDatabase.getInstance().getReference();
        getMessagesFromFirebase();
        button_send.setOnClickListener(v -> sendMessage());
        return view;
    }

    private void getMessagesFromFirebase()
    {
        database_reference.child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot MessageSnapshot) {
                if (MessageSnapshot.exists()) {
                    message_list.clear();
                    for (DataSnapshot ds : MessageSnapshot.getChildren()) {
                        String name = ds.child("name").getValue().toString();
                        String message = ds.child("message").getValue().toString();
                        String type = ds.child("type").getValue().toString();
                        message_list.add(new MessageModel(name, message, Integer.parseInt(type)));
                    }
                    message_adapter = new MessageAdapter(message_list, R.layout.view_messages, MessagesFragment.this);
                    recyvler_view.setAdapter(message_adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }
        );
    }
    private void initializeUI(View view) {
        tv_message = view.findViewById(R.id.message_text);
        recyvler_view = view.findViewById(R.id.item_messages);
        button_send= view.findViewById(R.id.button_send);
    }

    private void sendMessage()
    {
        database_reference.child("message").push().setValue(new MessageModel("Miguel",tv_message.getText().toString(), 1));
    }


    @Override
    public void onMessageClick(int position) {

    }
}