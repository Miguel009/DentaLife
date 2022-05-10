package sv.edu.udb.dentalife.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sv.edu.udb.dentalife.R;
import sv.edu.udb.dentalife.models.MessageModel;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private final int resource;
    private final ArrayList<MessageModel> message_list;
    private final OnMessageListener mOnMessageListener;

    public MessageAdapter(ArrayList<MessageModel> message_list, int resource, OnMessageListener onMessageListener) {
        this.message_list = message_list;
        this.resource = resource;
        this.mOnMessageListener = onMessageListener;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(resource, parent,false);
        return new ViewHolder(view, mOnMessageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        MessageModel message = message_list.get(position);
        //Aqui agregamos nuevps campos
        if ( message.getType() == 1)
        {
            holder.chat_right_msg.setVisibility(LinearLayout.VISIBLE);
            holder.messa_name_right.setText(message.getName());
            holder.messa_message_right.setText(message.getMessage());
            holder.chat_left_msg.setVisibility(LinearLayout.GONE);
        }
        else
        {
            holder.chat_left_msg.setVisibility(LinearLayout.VISIBLE);
            holder.messa_name_left.setText(message.getName());
            holder.messa_message_left.setText(message.getMessage());
            holder.chat_right_msg.setVisibility(LinearLayout.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return message_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Aqui declaramos los objetos de las vistas

        private LinearLayout chat_left_msg, chat_right_msg;
        private TextView messa_name_right, messa_message_right, messa_name_left, messa_message_left;

        public View view;

        OnMessageListener onMessageListener;

        public ViewHolder(View view, OnMessageListener onMessageListener) {
            super(view);
            this.view = view;

            this.messa_name_right = (TextView) view.findViewById(R.id.messa_name_right);
            this.messa_message_right = (TextView) view.findViewById(R.id.messa_message_right);
            this.messa_name_left = (TextView) view.findViewById(R.id.messa_name_left);
            this.messa_message_left = (TextView) view.findViewById(R.id.messa_message_left);

            this.chat_left_msg = (LinearLayout) view.findViewById(R.id.chat_left_msg);
            this.chat_right_msg = (LinearLayout) view.findViewById(R.id.chat_right_msg);

            this.onMessageListener=onMessageListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMessageListener.onMessageClick(getAdapterPosition());
        }
    }

    public interface OnMessageListener{
        void onMessageClick(int position);
    }
}
