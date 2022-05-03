package ru.mirea.kryazhin.mireaproject.ui.permissions

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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import ru.mirea.kryazhin.mireaproject.MainActivity
import ru.mirea.kryazhin.mireaproject.R
import java.io.File
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AudioRecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioRecordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val TAG_AUDIO = MainActivity::class.java.simpleName
    val TAG: String = AudioRecordFragment::class.java.getSimpleName()
    private val REQUEST_CODE_PERMISSION = 100
    private val PERMISSIONS = arrayOf<String>(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )
    private var isWorkAudio = false
    private var startRecordButton: Button? = null
    private var stopRecordButton: Button? = null
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null
    var playAudioButton: Button? = null
    var stopAudioButton: Button? = null
    var buttonPlayRecord: Button? = null
    var buttonStopRecord: Button? = null


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
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_audio_record, container, false)

        startRecordButton = view.findViewById<Button>(R.id.btnStart)
        stopRecordButton = view.findViewById<Button>(R.id.btnStop)
        buttonPlayRecord = view.findViewById<Button>(R.id.button_play_audio)
        buttonStopRecord = view.findViewById<Button>(R.id.button_stop_audio)

        startRecordButton?.setOnClickListener(View.OnClickListener { view -> onRecordStart(view) })

        stopRecordButton?.setOnClickListener(View.OnClickListener { view -> onStopRecord(view) })

        buttonPlayRecord?.setOnClickListener(View.OnClickListener { view -> buttonPlayRecord(view) })

        buttonStopRecord?.setOnClickListener(View.OnClickListener { view -> buttonStopRecord(view) })

        // инициализация объекта MediaRecorder

        // инициализация объекта MediaRecorder
        mediaRecorder = MediaRecorder()
        // проверка наличия разрешений на выполнение аудиозаписи и сохранения на карту памяти
        // проверка наличия разрешений на выполнение аудиозаписи и сохранения на карту памяти
        isWorkAudio = hasPermissions(activity, PERMISSIONS)
        if (!isWorkAudio) {
            ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS, REQUEST_CODE_PERMISSION)
        }

        audioFile = File(Environment.getExternalStorageDirectory().toString() + "/mirea.3gp")

        return view
    }
    fun hasPermissions(context: Context?, vararg permissions: Array<String>): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!.toString()
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
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
// производится проверка полученного результата от пользователя на запрос разрешения Camera
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Audio
        if (requestCode == REQUEST_CODE_PERMISSION) {
            // permission granted
            isWorkAudio = (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
    }

    // Аудиозапись
    // нажатие на кнопку старт
    fun onRecordStart(view: View?) {
        try {
            startRecordButton!!.isEnabled = false
            stopRecordButton!!.isEnabled = true
            stopRecordButton!!.requestFocus()
            startRecording()
        } catch (e: Exception) {
            Log.e(TAG, "Caught io exception " + e.message)
        }
    }

    // нажатие на копку стоп
    fun onStopRecord(view: View?) {
        startRecordButton!!.isEnabled = true
        stopRecordButton!!.isEnabled = false
        startRecordButton!!.requestFocus()
        stopRecording()
        processAudioFile()
    }

    @Throws(IOException::class)
    private fun startRecording() {
        // проверка доступности sd - карты
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            Log.d(TAG, "sd-card success")
            // выбор источника звука
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            // выбор формата данных
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            // выбор кодека
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            val path = Environment.getExternalStorageDirectory().toString() + "/mirea.3gp"
            mediaRecorder!!.setOutputFile(path)
            mediaRecorder!!.prepare()
            mediaRecorder!!.start()
            Toast.makeText(activity, "Recording started!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopRecording() {
        if (mediaRecorder != null) {
            Log.d(TAG, "stopRecording")
            mediaRecorder!!.stop()
            mediaRecorder!!.reset()
            mediaRecorder!!.release()
            Toast.makeText(activity, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun processAudioFile() {
        Log.d(TAG, "processAudioFile")
        val values = ContentValues(4)
        val current = System.currentTimeMillis()
        // установка meta данных созданному файлу
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile!!.name)
        values.put(MediaStore.Audio.Media.DATE_ADDED, (current / 1000).toInt())
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp")
        values.put(MediaStore.Audio.Media.DATA, audioFile!!.absolutePath)
        val contentResolver = requireActivity().contentResolver
        Log.d(TAG, "audioFile: " + audioFile!!.canRead())
        val baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val newUri = contentResolver.insert(baseUri, values)
        // оповещение системы о новом файле
        requireActivity().sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri))
    }

    fun buttonPlayRecord(view: View?) {
        requireActivity().startService(Intent(activity, AudioRecordService::class.java))
        Toast.makeText(activity, "Listening started!", Toast.LENGTH_SHORT).show()
    }

    fun buttonStopRecord(view: View?) {
        requireActivity().stopService(Intent(activity, AudioRecordService::class.java))
        Toast.makeText(activity, "Stop listening", Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AudioRecordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AudioRecordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}