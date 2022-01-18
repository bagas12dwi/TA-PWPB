package net.bag12.glowbeauty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder> {

    private ArrayList<model_user> mUsers;
    private Context context;

    public userAdapter(ArrayList<model_user> mUsers, Context context) {
        this.mUsers = mUsers;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tx_nama, tx_email, tx_no, tx_alamat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tx_nama = itemView.findViewById(R.id.namaUser);
            tx_email = itemView.findViewById(R.id.emailUser);
            tx_no = itemView.findViewById(R.id.noUser);
            tx_alamat = itemView.findViewById(R.id.alamatUser);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String nUser = mUsers.get(position).getNama();
        final String eUser = mUsers.get(position).getEmail();
        final String noUser = mUsers.get(position).getNoHp();
        final String aUser = mUsers.get(position).getAlamat();

        holder.tx_nama.setText(nUser);
        holder.tx_email.setText(eUser);
        holder.tx_no.setText(noUser);
        holder.tx_alamat.setText(aUser);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
