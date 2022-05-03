package ru.mirea.kryazhin.mireaproject.ui.permissions

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import ru.mirea.kryazhin.mireaproject.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Camera.newInstance] factory method to
 * create an instance of this fragment.
 */
class Camera : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val REQUEST_CODE_PERMISSION_CAMERA = 100
    val TAG: String = Camera::class.java.getSimpleName()
    private var imageView: ImageView? = null
    private val CAMERA_REQUEST = 0
    private var isWork = false
    private var imageUri: Uri? = null
    var buttonPhoto: Button? = null

    fun Camera() {}

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
        val view: View = inflater.inflate(R.layout.fragment_camera, container, false)
        // Камера
        buttonPhoto = view.findViewById(R.id.buttonPhotos) as Button
        buttonPhoto!!.setOnClickListener(View.OnClickListener { view -> imageViewOnClick(view) })
        // Камера
        // Камера
        imageView = view.findViewById(R.id.imageView1)
        // Выполняется проверка на наличие разрешений на использование камеры и запись в

        // Выполняется проверка на наличие разрешений на использование камеры и запись в
        val cameraPermissionStatus =
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
        val storagePermissionStatus = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true
        } else {
            // Выполняется запрос к пользователь на получение необходимых разрешений
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_PERMISSION_CAMERA
            )
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageView = requireActivity().findViewById<View>(R.id.imageView1) as ImageView
        // Если приложение камера вернула RESULT_OK, то производится установка изображению imageView
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            imageView!!.setImageURI(imageUri)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun imageViewOnClick(view: View?) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // проверка на наличие разрешений для камеры
        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null && isWork) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            // генерирование пути к файлу на основе authorities
            val authorities = requireActivity().applicationContext.packageName + ".fileprovider"
            assert(photoFile != null)
            imageUri = FileProvider.getUriForFile(requireContext(), authorities, photoFile as File)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMAGE_" + timeStamp + "_"
        val storageDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDirectory)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
// производится проверка полученного результата от пользователя на запрос разрешения Camera
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
            isWork = if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                // permission granted
                true
            } else {
                false
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Camera.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Camera().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}