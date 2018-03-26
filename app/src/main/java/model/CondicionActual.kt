package model

data class CondicionActual(var weatherID: Int = 0,
                           var condicion: String? = null,
                           var descripcion: String? = null,
                           var icono: String? = null,
                           var presion: Float = 0.toFloat(),
                           var humedad: Float = 0.toFloat(),
                           var maximaTemperatura: Float = 0.toFloat(),
                           var minTemperatura: Float = 0.toFloat(),
                           var temperatura: Double = 0.toDouble()){


}