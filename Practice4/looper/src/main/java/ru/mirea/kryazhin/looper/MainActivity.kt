package ru.mirea.kryazhin.looper

import android.os.Bundle
import android.os.Message
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private var myLooper: MyLooper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myLooper = MyLooper()
        myLooper!!.start()
    }

    fun onClick(view: View?) {
        val msg = Message()
        val bundle = Bundle()
        bundle.putString("KEY", "23 DevOps")
        msg.setData(bundle)
        try {
            TimeUnit.SECONDS.sleep(23)
            if (myLooper != null) {
                myLooper!!.handler!!.sendMessage(msg)
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}