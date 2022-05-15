package ru.mirea.kryazhin.networkstate

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import java.lang.Boolean


class NetworkLiveData private constructor(context: Context) : LiveData<String?>() {
    private val context: Context
    private var broadcastReceiver: BroadcastReceiver? = null

    // Отслеживание изменения состояния сети осществляется через BroadcastReceiver. При
    //изменении состояния генерируется сообщение для всех слушателей в системе
    private fun prepareReceiver(context: Context) {
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                val cm =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = cm.activeNetworkInfo
                value = if (activeNetwork != null) {
                    val isConnected = activeNetwork.isConnectedOrConnecting
                    Boolean.toString(isConnected)
                } else "false"
            }
        }
        context.registerReceiver(broadcastReceiver, filter)
    }

    override fun onActive() {
        prepareReceiver(context)
    }

    override fun onInactive() {
        context.unregisterReceiver(broadcastReceiver)
        broadcastReceiver = null
    }

    companion object {
        private var instance: NetworkLiveData? = null
        fun getInstance(context: Context): NetworkLiveData? {
            if (instance == null) {
                instance = NetworkLiveData(context.getApplicationContext())
            }
            return instance
        }
    }

    init {
        if (instance != null) {
            throw RuntimeException(
                "Use getInstance() method to get the single instance of this " +
                        "class."
            )
        }
        this.context = context
    }
}