package ru.mirea.kryazhin.audiorecord


import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val PERMISSIONS = arrayOf<String>(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )
    private var isWork = false
    private var startRecordButton: Button? = null
    private var stopRecordButton: Button? = null
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startRecordButton = findViewById(R.id.btnStart)
        stopRecordButton = findViewById(R.id.btnStop)
        // инициализация объекта MediaRecorder
        mediaRecorder = MediaRecorder()

        // проверка наличия разрешений на выполнение аудиозаписи и сохранения на карту памяти
        isWork = hasPermissions(this, PERMISSIONS)
        if (!isWork) {
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS,
                MainActivity.Companion.REQUEST_CODE_PERMISSION
            )
        }
    }

    private fun hasPermissions(context: Context?, permissions: Array<String>?): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MainActivity.Companion.REQUEST_CODE_PERMISSION) {
            // permission granted
            isWork = (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
    }

    // нажатие на кнопку старт
    fun onRecordStart(view: View?) {
        try {
            startRecordButton!!.isEnabled = false
            stopRecordButton!!.isEnabled = true
            stopRecordButton!!.requestFocus()
            startRecording()
        } catch (e: Exception) {
            Log.e(MainActivity.Companion.TAG, "Caught io exception " + e.message)
        }
    }

    @Throws(IOException::class)
    private fun startRecording() {
        // проверка доступности sd - карты
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            Log.d(MainActivity.Companion.TAG, "sd-card success")
            // выбор источника звука
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            // выбор формата данных
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            // выбор кодека
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            if (audioFile == null) {
                // создание файла
                audioFile = File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "mirea.3gp")
            }
            mediaRecorder!!.setOutputFile(audioFile!!.absolutePath)
            mediaRecorder!!.prepare()
            mediaRecorder!!.start()
            Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show()
        }
    }

    // нажатие на копку стоп
    fun onStopRecord(view: View?) {
        stopRecordButton!!.isEnabled = false
        startRecordButton!!.isEnabled = true
        startRecordButton!!.requestFocus()
        stopRecording()
        processAudioFile()
    }

    private fun stopRecording() {
        if (mediaRecorder != null) {
            Log.d(MainActivity.Companion.TAG, "stopRecording")
            mediaRecorder!!.stop()
            mediaRecorder!!.reset()
            mediaRecorder!!.release()
            Toast.makeText(this, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun processAudioFile() {
        Log.d(MainActivity.Companion.TAG, "processAudioFile")
        val values = ContentValues(4)
        val current = System.currentTimeMillis()
        // установка meta данных созданному файлу
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile!!.name)
        values.put(MediaStore.Audio.Media.DATE_ADDED, (current / 1000).toInt())
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp")
        values.put(MediaStore.Audio.Media.DATA, audioFile!!.absolutePath)
        val contentResolver = contentResolver
        Log.d(MainActivity.Companion.TAG, "audioFile: " + audioFile!!.canRead())
        val baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val newUri = contentResolver.insert(baseUri, values)

        // оповещение системы о новом файле
        sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri))
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val REQUEST_CODE_PERMISSION = 100
    }
}