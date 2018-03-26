package model

/**
 * Created by asus on 24/01/2018.
 */
data class lugar(var longitud: Float=0.toFloat(),
                 var latitud: Float= 0.toFloat(),
                 var Amanecer: Long=0,
                 var PuestaSol: Long= 0,
                 var Pais: String?=null,
                 var Ciudad: String?=null,
                 var UltimaAct: Long=0) {
}