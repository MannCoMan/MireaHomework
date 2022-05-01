package ru.mirea.kryazhin.mireaproject.ui.musicplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import ru.mirea.kryazhin.mireaproject.R

class PlayerService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer!!.isLooping = true
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mediaPlayer!!.start()
        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer!!.stop()
    }
}