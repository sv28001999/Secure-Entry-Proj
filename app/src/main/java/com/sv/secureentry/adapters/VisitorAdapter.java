package com.sv.secureentry.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sv.secureentry.R;

import java.util.List;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.VisitorViewHolder> {
    private List<VisitorList> visitorList;
    private Context context;

    public VisitorAdapter(Context context, List<VisitorList> visitorList) {
        this.context = context;
        this.visitorList = visitorList;
    }

    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gate_entry_list_item, parent, false);
        return new VisitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
        VisitorList visitor = visitorList.get(position);
        holder.tvName.setText(visitor.getName());
        holder.tvMobile.setText(visitor.getMobile());
        holder.tvWork.setText(visitor.getWork());
        holder.tvPlace.setText(visitor.getPlace());
        holder.tvVisitorVisitRoom.setText(visitor.getVisitorName());
        holder.tvVisitingDate.setText(visitor.getVisitingDate());
        Glide.with(context)
                .load(visitor.getImageUrl())
                .into(holder.visitorImg);
    }

    @Override
    public int getItemCount() {
        return visitorList.size();
    }

    public static class VisitorViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMobile, tvWork, tvPlace, tvVisitorVisitRoom, tvVisitingDate;
        ImageView visitorImg;

        public VisitorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.visitorName);
            tvMobile = itemView.findViewById(R.id.visitorMobile);
            tvWork = itemView.findViewById(R.id.visitorWork);
            tvPlace = itemView.findViewById(R.id.visitorPlace);
            tvVisitorVisitRoom = itemView.findViewById(R.id.visitorVisitRoom);
            visitorImg = itemView.findViewById(R.id.visitorImg);
            tvVisitingDate = itemView.findViewById(R.id.visitorVisitDate);
        }
    }
}
