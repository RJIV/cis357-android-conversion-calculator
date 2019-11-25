package com.chuahamilton.conversioncalculator.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * helper methods.
 */
class WeatherService : IntentService("WeatherService") {

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_WEATHER_AT == action) {
                val key = intent.getStringExtra(EXTRA_KEY)
                val lat = intent.getStringExtra(EXTRA_LAT)
                val lng = intent.getStringExtra(EXTRA_LNG)
                fetchWeatherData(key!!, lat!!, lng!!)
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun fetchWeatherData(
        key: String,
        lat: String,
        lon: String
    ) {
        try {
            val url =
                URL("$BASE_URL/$lat,$lon")
            val conn =
                url.openConnection() as HttpURLConnection
            conn.readTimeout = 5000
            conn.connectTimeout = 10000
            conn.requestMethod = "GET"
            conn.doInput = true
            // Starts the query
            conn.connect()
            val response = conn.responseCode
            if (response == HttpURLConnection.HTTP_OK) {
                val bis =
                    BufferedInputStream(conn.inputStream)
                val baos = ByteArrayOutputStream()
                val buffer = ByteArray(4096)
                var len: Int
                while (bis.read(buffer).also { len = it } > 0) {
                    baos.write(buffer, 0, len)
                }
                val data = JSONObject(String(baos.toByteArray()))
                val current = data.getJSONObject("currently")
                // TODO: extract the values you need out of current
                val condition = current.getString("summary")
                val icon = current.getString("icon")
                val temp = current.getDouble("temperature")

                val result = Intent(BROADCAST_WEATHER)
                // TODO: use putExtra to add the extracted values to your broadcast
                result.putExtra("KEY", key)
                result.putExtra("CONDITION", condition)
                result.putExtra("ICON", icon)
                result.putExtra("TEMP", temp)
                LocalBroadcastManager.getInstance(this).sendBroadcast(result)
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo(param1: String, param2: String) {
        throw UnsupportedOperationException("Not yet implemented")
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionBaz(param1: String, param2: String) {
        throw UnsupportedOperationException("Not yet implemented")
    }

    companion object {
        private const val TAG = "WeatherService"
        // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
        private const val ACTION_WEATHER_AT = "edu.gvsu.cis.webservice.action.WEATHER_AT"
        private const val BASE_URL =
            "https://api.darksky.net/forecast/ce0f224df27b7be3885f99772c9bda04"
        const val BROADCAST_WEATHER = "edu.gvsu.cis.webservice.action.BROADCAST"
        private const val EXTRA_KEY = "edu.gvsu.cis.webservice.extra.KEY"
        private const val EXTRA_LAT = "edu.gvsu.cis.webservice.extra.LAT"
        private const val EXTRA_LNG = "edu.gvsu.cis.webservice.extra.LNG"
        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        fun startGetWeather(
            context: Context,
            lat: String?,
            lng: String?,
            key: String?
        ) {
            val intent = Intent(context, WeatherService::class.java)
            intent.action = ACTION_WEATHER_AT
            intent.putExtra(EXTRA_LAT, lat)
            intent.putExtra(EXTRA_LNG, lng)
            intent.putExtra(EXTRA_KEY, key)
            context.startService(intent)
        }
    }


}
