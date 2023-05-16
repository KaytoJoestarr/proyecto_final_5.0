package com.example.proyecto_final;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.MyViewHolder> {

    Context context;
    ArrayList<Tarea> tareasArrayList;




    public TareasAdapter(Context context, ArrayList<Tarea> tareasArrayList) {
        this.context = context;
        this.tareasArrayList = tareasArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_tarea,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Tarea tarea = tareasArrayList.get(position);

        holder.nombre_tarea.setText(tarea.nombre_tarea);
        holder.fecha_tarea.setText(tarea.fecha_tarea);
        holder.hora_tarea.setText(String.valueOf(tarea.hora_tarea));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetalleTareaActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tareasArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombre_tarea, fecha_tarea, hora_tarea;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_tarea = itemView.findViewById(R.id.tv_nombre_tarea);
            fecha_tarea = itemView.findViewById(R.id.tv_fecha_tarea);
            hora_tarea = itemView.findViewById(R.id.tv_hora_tarea);
        }
    }
}
