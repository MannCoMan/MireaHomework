package ru.mirea.kryazhin.socketconnection

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.kryazhin.socketconnection.SocketUtils.getReader
import java.io.IOException
import java.net.Socket


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private var mTextView: TextView? = null
    private val host = "time-a.nist.gov"
    private val port = 13
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTextView = findViewById(R.id.textView)
    }

    fun OnClick(view: View?) {
        val timeTask = GetTimeTask()
        timeTask.execute()
    }

    private inner class GetTimeTask : AsyncTask<Void?, Void?, String>() {
        override fun doInBackground(vararg p0: Void?): String {
            var timeResult = ""
            try {
                val socket = Socket(host, port)
                val reader = getReader(socket)
                reader.readLine() // игнорируем первую строку
                timeResult = reader.readLine() // считываем вторую строку
                Log.d(TAG, timeResult)
                socket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return timeResult!!
        }
        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            mTextView?.setText(result)
        }

    }
}