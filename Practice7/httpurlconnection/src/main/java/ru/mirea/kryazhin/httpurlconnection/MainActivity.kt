package ru.mirea.kryazhin.httpurlconnection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class MainActivity : AppCompatActivity() {
    var ipTextView: TextView? = null
    var countryTextView: TextView? = null
    var cityTextView: TextView? = null
    var timeZoneTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ipTextView = findViewById(R.id.textViewIp);
        countryTextView = findViewById(R.id.textViewCountry);
        cityTextView = findViewById(R.id.textViewCity);
        timeZoneTextView = findViewById(R.id.textViewTimezone);
    }

    fun onClick(view: View?) {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo: NetworkInfo? = null
        if (connectivityManager != null) {
            networkInfo = connectivityManager.activeNetworkInfo
        }
        if (networkInfo != null && networkInfo.isConnected) {
            val url = "http://ip.jsontest.com/"
            DownloadPageTask().execute("http://ip-api.com/json/95.31.176.218") // запускаем в новом потоке
        } else {
            Toast.makeText(this, "Нет интернета", Toast.LENGTH_SHORT).show()
        }
    }

    inner private class DownloadPageTask : AsyncTask<String?, Void?, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            ipTextView?.setText("Загружаем...")
        }

        override fun doInBackground(vararg urls: String?): String? {
            return downloadIpInfo(urls[0].toString())
        }

        override fun onPostExecute(result: String) {
            Log.d(MainActivity::class.java.simpleName, result)
            try {
                val responseJson = JSONObject(result)
                val ip = responseJson.getString("query")
                val country = responseJson.getString("country")
                val city = responseJson.getString("city")
                val timeZone = responseJson.getString("timezone")
                Log.d(MainActivity::class.java.simpleName, ip)
                Log.d(MainActivity::class.java.simpleName, country)
                Log.d(MainActivity::class.java.simpleName, city)
                Log.d(MainActivity::class.java.simpleName, timeZone)
                ipTextView?.setText("IP: $ip")
                countryTextView?.setText("Country: $country")
                cityTextView?.setText("City: $city")
                timeZoneTextView?.setText("TimeZone: $timeZone")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            super.onPostExecute(result)
        }


    }

    @Throws(IOException::class)
    private fun downloadIpInfo(address: String): String {
        var inputStream: InputStream? = null
        var data: String = ""
        try {
            val url = URL(address)
            val connection = url
                .openConnection() as HttpURLConnection
            connection.readTimeout = 100000
            connection.connectTimeout = 100000
            connection.requestMethod = "GET"
            connection.instanceFollowRedirects = true
            connection.useCaches = false
            connection.doInput = true
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                inputStream = connection.inputStream
                val bos = ByteArrayOutputStream()
                var read = 0
                while (inputStream.read().also { read = it } != -1) {
                    bos.write(read)
                }
                val result = bos.toByteArray()
                bos.close()
                data = String(result)
            } else {
                data = connection.responseMessage + " . Error Code : " + responseCode
            }
            connection.disconnect()
            //return data;
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }
        return data
    }
}