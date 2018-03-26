package util

import org.json.JSONException
import org.json.JSONObject


object Utils {
    val BASE_URL="http://api.openweathermap.org/data/2.5/weather?q="
    val iconUrl="http://openweathermap.org/img/w/"
    @Throws(JSONException::class)
    fun getObject(tagName: String, JsonObject: JSONObject):JSONObject{
        val Jobject=JsonObject.getJSONObject(tagName)
        return Jobject
    }
    @Throws(JSONException::class)
    fun getString(tagName: String, JsonObject: JSONObject):String{
        return  JsonObject.getString(tagName)
    }
    @Throws(JSONException::class)
    fun getFloat(tagName: String, JsonObject: JSONObject):Float{
        return JsonObject.getDouble(tagName).toFloat()
    }
    @Throws(JSONException::class)
    fun getDouble(tagName: String, JsonObject: JSONObject):Double{
        return JsonObject.getDouble(tagName)
    }
    @Throws(JSONException::class)
    fun getInt(tagName: String, JsonObject: JSONObject):Int{
        return JsonObject.getInt(tagName)
    }
    @Throws(JSONException::class)
    fun getLong(tagName: String, JsonObject: JSONObject):Long{
        return JsonObject.getLong(tagName)
    }


}