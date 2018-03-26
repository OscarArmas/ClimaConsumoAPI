package model

/**
 * Created by asus on 24/01/2018.
 */
data class clima(var lugar:lugar?=null,
                 var icon:String?=null,
                 var condicionActual: CondicionActual=CondicionActual(),
                 var temperatura: temperatura=temperatura(),
                 var viento: viento= viento(),
                 var nieve: nieve=nieve(),
                 var nubes: nubes=nubes()
                 )