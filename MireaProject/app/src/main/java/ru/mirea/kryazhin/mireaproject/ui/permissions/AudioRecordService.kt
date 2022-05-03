package ru.mirea.kryazhin.mireaproject.ui.permissions

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Environment
import android.os.IBinder
import java.io.IOException


class AudioRecordService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
    override fun onCreate() {
        val audioFile = Environment.getExternalStorageDirectory().toString() + "/mirea.3gp"
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(audioFile)
            mediaPlayer?.prepare()
            mediaPlayer?.setLooping(true)
        } catch (e: IOException) {
            e.printStackTrace()
        }
//        mediaPlayer=MediaPlayer.create(this, );
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer?.start()
        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
    }
}