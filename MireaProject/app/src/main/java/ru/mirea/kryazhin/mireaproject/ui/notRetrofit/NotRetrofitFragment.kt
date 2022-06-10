package ru.mirea.kryazhin.mireaproject.ui.notRetrofit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.json.JSONException
import org.json.JSONObject
import ru.mirea.kryazhin.mireaproject.MainActivity
import ru.mirea.kryazhin.mireaproject.R
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotRetrofitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotRetrofitFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var resultTextView: TextView? = null
    var r: TextView? = null
    var r2: TextView? = null
    var r3: TextView? = null
    var btn: Button? = null
    private var url: String? = null

    fun NotRetrofit() {
        // Required empty public constructor
    }

    @JvmName("newInstance1")
    fun newInstance(param1: String?, param2: String?): ru.mirea.kryazhin.mireaproject.ui.notRetrofit.NotRetrofitFragment? {
        val fragment: ru.mirea.kryazhin.mireaproject.ui.notRetrofit.NotRetrofitFragment =
            ru.mirea.kryazhin.mireaproject.ui.notRetrofit.NotRetrofitFragment()
        val args = Bundle()
        fragment.setArguments(args)
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_not_retrofit, container, false)
        resultTextView = view.findViewById(R.id.textView)
        r = view.findViewById(R.id.textView2)
        r2 = view.findViewById(R.id.textView3)
        r3 = view.findViewById(R.id.textView4)
        url = "http://ip-api.com/json/94.25.60.196"
        btn = view.findViewById(R.id.button)
        btn!!.setOnClickListener(View.OnClickListener {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var networkinfo: NetworkInfo? = null
            networkinfo = connectivityManager.activeNetworkInfo
            if (networkinfo != null && networkinfo.isConnected) {
                DownloadPageTask().execute(url) // запускаем в новом потоке
            } else {
                Toast.makeText(context, "Нет интернета", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }
    inner private class DownloadPageTask :
        AsyncTask<String?, Void?, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            resultTextView?.setText("Загружаем...")
        }

        override fun doInBackground(vararg urls: String?): String? {
            return try {
                downloadIpInfo(urls[0]!!)
            } catch (e: IOException) {
                e.printStackTrace()
                "error"
            }
        }

        override fun onPostExecute(result: String) {
            resultTextView?.setText(result)
            Log.d(MainActivity::class.java.getSimpleName(), result)
            try {
                val responseJson = JSONObject(result)
                val ip = responseJson.getString("query")
                val responseJson1 = JSONObject(result)
                val city = responseJson1.getString("city")
                val region = responseJson.getString("region")
                Log.d(MainActivity::class.java.getSimpleName(), ip)
                r?.setText(ip)
                r2?.setText(city)
                r3?.setText(region)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            super.onPostExecute(result)
        }


    }

    @Throws(IOException::class)
    private fun downloadIpInfo(address: String): String? {
        var inputStream: InputStream? = null
        var data: String? = ""
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotRetrofitFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotRetrofitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}