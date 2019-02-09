package ru.vlabum.android.apps.nweather

import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class RequesterWeather(private var listener: OnRequestListener) {

    private val LOG_CLASS_NAME = this.javaClass.name

    fun RequesterWeather(listener: OnRequestListener) {
        this.listener = listener
    }

    fun make(city: String) {
        val req = Requester(listener)
        req.execute(city)
    }

    interface OnRequestListener {
        public fun OnRequestListener() {}
        fun onComlete()
    }

    private class Requester(private var listener: OnRequestListener) : AsyncTask<String, String, String>() {

        private val LOG_CLASS_NAME = this.javaClass.name

        override fun doInBackground(vararg city: String): String {
            DataStorage.instance().setCity(city[0])
            getWeather(DataStorage.TypeQuery.CURRENT)
            return city[0]
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            listener.onComlete()
        }

        fun getWeather(current: DataStorage.TypeQuery) {
            val url = DataStorage.instance().getRequestStringWeather(current)
            val obj = URL(url)
            val con = obj.openConnection() as HttpURLConnection

            con.requestMethod = "GET"

            try {
                BufferedReader(InputStreamReader(con.inputStream)).use {
                    val response = StringBuffer()
                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    DataStorage.instance().storeWeather(response.toString(), current)
                }
            } catch (e: Exception) {
                Toast.makeText(App.getInstance(), "Запрос погоды не удался", Toast.LENGTH_LONG).show()
                Log.d(LOG_CLASS_NAME, e.toString())
            }
            con.disconnect()
        }
    }
}