package com.example.proyecto_final

class Tarea {
    @JvmField
    var nombre_tarea: String? = null
    @JvmField
    var des_tarea: String? = null
    @JvmField
    var fecha_tarea: String? = null
    @JvmField
    var hora_tarea: String? = null

    constructor() {}
    constructor(
        nombre_tarea: String?,
        des_tarea: String?,
        fecha_tarea: String?,
        hora_tarea: String?
    ) {
        this.nombre_tarea = nombre_tarea
        this.des_tarea = des_tarea
        this.fecha_tarea = fecha_tarea
        this.hora_tarea = hora_tarea
    }
}