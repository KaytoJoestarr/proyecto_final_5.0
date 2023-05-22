package com.example.proyecto_final

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.TareasAdapter.MyViewHolder

class TareasAdapter(var context: Context, var tareasArrayList: ArrayList<Tarea>) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_tarea, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tarea = tareasArrayList[position]
        holder.nombre_tarea.text = tarea.nombre_tarea
        holder.fecha_tarea.text = tarea.fecha_tarea
        holder.hora_tarea.text = tarea.hora_tarea.toString()
        holder.itemView.setOnClickListener { view ->
            val context = view.context
            val intent = Intent(context, DetalleTareaActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return tareasArrayList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombre_tarea: TextView
        var fecha_tarea: TextView
        var hora_tarea: TextView

        init {
            nombre_tarea = itemView.findViewById(R.id.tv_nombre_tarea)
            fecha_tarea = itemView.findViewById(R.id.tv_fecha_tarea)
            hora_tarea = itemView.findViewById(R.id.tv_hora_tarea)
        }
    }
}