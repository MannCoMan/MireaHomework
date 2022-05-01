package ru.mirea.kryazhin.loadermanger

import android.app.Service
import android.content.Intent
import android.os.IBinder

class PlayerService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_STICKY
    }
}