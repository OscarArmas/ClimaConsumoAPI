package Data

import android.icu.util.UniversalTimeScale
import model.clima
import model.lugar
import org.json.JSONObject
import util.Utils
import java.util.jar.JarException

/**
 * Created by asus on 28/01/2018.
 */
object JSONParseClima {
    fun getWeatger(data: String): clima?{
        val clima=clima()
        try {
            val jsonObject= JSONObject(data)
            val lugar= lugar()
            val coorObj= util.Utils.getObject("coord", jsonObject)

            lugar.latitud= util.Utils.getFloat("lat", coorObj)
            lugar.longitud = util.Utils.getFloat("lon", coorObj)
            val sysObj= util.Utils.getObject("sys", jsonObject)
            lugar.Pais=util.Utils.getString("country", sysObj)
            lugar.UltimaAct = util.Utils.getLong("dt", jsonObject)
            lugar.Amanecer=util.Utils.getLong("sunrise", sysObj)
            lugar.PuestaSol= util.Utils.getLong("sunset", sysObj)
            lugar.Ciudad= util.Utils.getString("name", jsonObject)
            clima.lugar= lugar
            val mainObjc= util.Utils.getObject("main", jsonObject)
            clima.condicionActual.humedad=util.Utils.getFloat("humidity", mainObjc)
            clima.condicionActual.temperatura=util.Utils.getDouble("temp", mainObjc)
            clima.condicionActual.presion=util.Utils.getFloat("pressure", mainObjc)
            clima.condicionActual.maximaTemperatura= util.Utils.getFloat("temp_max", mainObjc)
            clima.condicionActual.minTemperatura = util.Utils.getFloat("temp_min", mainObjc)
            val JsonArray=jsonObject.getJSONArray("weather")
            val Jsonweather= JsonArray.getJSONObject(0)
            clima.condicionActual.weatherID= util.Utils.getInt("id",Jsonweather)
            clima.condicionActual.descripcion= util.Utils.getString("description", Jsonweather)
            clima.condicionActual.condicion=util.Utils.getString("main",Jsonweather)
            clima.condicionActual.icono=util.Utils.getString("icon", Jsonweather)
            val vientoObj= util.Utils.getObject("wind",jsonObject)
            clima.viento.velocidad= util.Utils.getFloat("speed", vientoObj)
            clima.viento.cent = util.Utils.getFloat("deg", vientoObj)
            val nubeObjc=Utils.getObject("clouds", jsonObject)
            clima.nubes.precipitacion= util.Utils.getInt("all", nubeObjc)
            return clima



        }catch (e:JarException){
            e.printStackTrace()
        }
        return null
    }
}