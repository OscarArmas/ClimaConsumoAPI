package com.example.asus.clima

import Data.HttpClientClima
import Data.JSONParseClima
import Data.PreferenciasCiudad
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import model.clima
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import java.io.IOException
import java.io.InputStream
import java.text.DateFormat
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var clima=model.clima()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var ciudadPreference: PreferenciasCiudad= PreferenciasCiudad(this)
        renderClimaDatos(ciudadPreference.ciudad)
    }
    fun renderClimaDatos(ciudad: String){
        val climaTast= ClimaTask()
        ClimaTask().execute(*arrayOf(ciudad + "&APPID=" + "dc3fe353f2e8e3be13e23ad63264838a" + "&units=metric"))

    }
    private inner class DescargarImagen: AsyncTask<String,Void,Bitmap>(){
        override fun doInBackground(vararg p0: String?): Bitmap {
            return  descargarImagen(p0[0] as String)
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            imageView.setImageBitmap(result)
        }
        fun descargarImagen(codigo: String):Bitmap{
            val cliente= DefaultHttpClient()
            val getRequest= HttpGet(util.Utils.iconUrl + codigo + ".png")
            try{
                val response= cliente.execute(getRequest)
                val status= response.statusLine.statusCode
                if(status !=  HttpStatus.SC_OK){
                    Log.e("DescargaImagen", "Error" + status)
                    return  null!!
                }
                val entity = response.entity
                if(entity != null){
                    var inputString: InputStream
                    inputString=entity.content
                    val bitmap: Bitmap=BitmapFactory.decodeStream(inputString)
                    return  bitmap
                }

            }catch (e: IOException){
                e.printStackTrace()
            }
            return null!!
        }

    }
    private inner class ClimaTask : AsyncTask<String,Void, clima>(){
        override fun doInBackground(vararg p0: String?): clima {
            val datos= HttpClientClima().GetWeatherData(p0[0])
            clima=JSONParseClima.getWeatger(datos)!!
            clima.icon=clima.condicionActual.icono
            DescargarImagen().execute(clima.icon)
            return clima

        }

        override fun onPostExecute(result: clima?) {
            super.onPostExecute(result)

            val formatoFecha=DateFormat.getTimeInstance()
            val amanecer = formatoFecha.format(Date(clima.lugar!!.Amanecer))
            val puestaSol=formatoFecha.format(Date(clima.lugar!!.PuestaSol))
            val actualizar=formatoFecha.format(Date(clima.lugar!!.UltimaAct))

            val formatoDecimal= DecimalFormat("#.#")
            val formatoTemp= formatoDecimal.format(clima.condicionActual.temperatura)

            textViewCiudad.text=clima.lugar!!.Ciudad + "," + clima.lugar!!.Pais
            textViewTemp.text= "" + formatoTemp + "C"
            textViewHumedad.text= "Humedad: " + clima.condicionActual.humedad +"%"
            textViewPresion.text= "Presion: "+ clima.condicionActual.presion
            textViewViento.text="Viento:" + clima.viento.velocidad +"mps"
            textViewSunset.text="Puesta de Sol: " + puestaSol
            textViewSunrise.text="Amanecer:" + amanecer
            textViewUpdate.text= "Utltima Actualizacion: "+ actualizar
            textViewNube.text= "Nube: " + clima.condicionActual.condicion + "("+ clima.condicionActual.descripcion+ ")"

        }

    }
    fun mostrarDialog(){
        var builder= AlertDialog.Builder(this)
        builder.setTitle("Cambiar ciudad")
        val ponerCiudad= EditText(this)
        ponerCiudad.inputType = InputType.TYPE_CLASS_TEXT
        ponerCiudad.hint="Merida,MX"
        builder.setView(ponerCiudad)
        builder.setPositiveButton("OK"){dialogInterface, i ->
            val ciudadPrefe=PreferenciasCiudad(this)
            ciudadPrefe.ciudad=ponerCiudad.text.toString()
            val ciudadNueva= ciudadPrefe.ciudad
            renderClimaDatos(ciudadNueva)
        }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override  fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.MenuCambiar -> mostrarDialog()


        }
        return super.onOptionsItemSelected(item)
    }
}
