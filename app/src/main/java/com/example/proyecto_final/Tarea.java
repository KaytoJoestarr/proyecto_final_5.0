package com.example.proyecto_final;

public class Tarea {

    String nombre_tarea, des_tarea, fecha_tarea, hora_tarea;


    public Tarea() {
    }

    public Tarea(String nombre_tarea, String des_tarea, String fecha_tarea, String hora_tarea) {
        this.nombre_tarea = nombre_tarea;
        this.des_tarea = des_tarea;
        this.fecha_tarea = fecha_tarea;
        this.hora_tarea = hora_tarea;
    }

    public String getNombre_tarea() {
        return nombre_tarea;
    }

    public void setNombre_tarea(String nombre_tarea) {
        this.nombre_tarea = nombre_tarea;
    }

    public String getDes_tarea() {
        return des_tarea;
    }

    public void setDes_tarea(String des_tarea) {
        this.des_tarea = des_tarea;
    }

    public String getFecha_tarea() {
        return fecha_tarea;
    }

    public void setFecha_tarea(String fecha_tarea) {
        this.fecha_tarea = fecha_tarea;
    }

    public String getHora_tarea() {
        return hora_tarea;
    }

    public void setHora_tarea(String hora_tarea) {
        this.hora_tarea = hora_tarea;
    }
}
