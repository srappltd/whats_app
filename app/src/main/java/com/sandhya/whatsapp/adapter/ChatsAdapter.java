package com.sandhya.whatsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sandhya.whatsapp.R;
import com.sandhya.whatsapp.activity.UserViewActivity;
import com.sandhya.whatsapp.model.ChatsModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {

    Context context;
    ArrayList<ChatsModel> callsModels;

    public ChatsAdapter(Context context, ArrayList<ChatsModel> callsModels) {
        this.context = context;
        this.callsModels = callsModels;
    }

    @NonNull
    @Override
    public ChatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chats_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsAdapter.ViewHolder holder, int position) {
        ChatsModel model = callsModels.get(position);

        initBindHolder(holder,model);

    }

    private void initBindHolder(ViewHolder holder, ChatsModel model) {
        Glide.with(context).load(model.getImage()).placeholder(R.drawable.ic_account).into(holder.imgLogo);
        holder.txtDes.setText(model.getDes());
        holder.txtName.setText(model.getName());
        holder.txtDate.setText(model.getDate());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, UserViewActivity.class);
            intent.putExtra("name",model.getName());
            intent.putExtra("des",model.getDes());
            intent.putExtra("logo",model.getImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return callsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtDes,txtDate,txtCount;
        CircleImageView imgLogo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            txtDes = itemView.findViewById(R.id.txtDes);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtCount = itemView.findViewById(R.id.txtCount);
            txtName = itemView.findViewById(R.id.txtAppName);
        }
    }
}
