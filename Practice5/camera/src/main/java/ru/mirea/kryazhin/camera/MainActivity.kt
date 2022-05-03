package ru.mirea.kryazhin.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName
    private var imageView: ImageView? = null
    private var isWork = false
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        // Выполняется проверка на наличие разрешений на использование камеры и запись в
        val cameraPermissionStatus =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val storagePermissionStatus =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MainActivity.Companion.REQUEST_CODE_PERMISSION_CAMERA
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Если приложение камера вернула RESULT_OK, то производится установка изображению imageView
        if (requestCode == MainActivity.Companion.CAMERA_REQUEST && resultCode == RESULT_OK) {
            imageView!!.setImageURI(imageUri)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun imageViewOnClick(view: View?) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // проверка на наличие разрешений для камеры
        if (cameraIntent.resolveActivity(packageManager) != null && isWork) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            // генерирование пути к файлу на основе authorities
            val authorities = applicationContext.packageName + ".fileprovider"
            assert(photoFile != null)
            imageUri = FileProvider.getUriForFile(this, authorities, photoFile as File)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(cameraIntent, MainActivity.Companion.CAMERA_REQUEST)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMAGE_" + timeStamp + "_"
        val storageDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDirectory)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
// производится проверка полученного результата от пользователя на запрос разрешения Camera
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MainActivity.Companion.REQUEST_CODE_PERMISSION_CAMERA) {
            isWork = (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSION_CAMERA = 100
        private const val CAMERA_REQUEST = 0
    }
}