package ru.mirea.kryazhin.looper

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log


class MyLooper : Thread() {
    private var number = 0
    var handler: Handler? = null

    @SuppressLint("HandlerLeak")
    override fun run() {
        Log.d("MyLooper", "run")
        Looper.prepare()
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                Log.d("MyLooper", number.toString() + ":" + msg.getData().getString("KEY"))
                number++
            }
        }
        Looper.loop()
    }

}
