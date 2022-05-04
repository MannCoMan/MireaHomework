package ru.mirea.kryazhin.notebook

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private val LOG_TAG = MainActivity::class.java.simpleName
    private var editTextFileName: EditText? = null
    private var editForText: EditText? = null
    private var preferences: SharedPreferences? = null
    val SAVED_TEXT = "saved_text"
    var fileName: String? = null
    var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextFileName = findViewById(R.id.editTextFileName);
        editForText = findViewById(R.id.editForText);
        preferences = getPreferences(MODE_PRIVATE);
    }

    fun onSaveText(view: View?) {
        val editor = preferences!!.edit()
        // Сохранение значения по ключу SAVED_TEXT
        editor.putString(SAVED_TEXT, editForText!!.text.toString())
        editor.apply()
        fileName = editTextFileName!!.text.toString()
        text = editForText!!.text.toString()
        val outputStream: FileOutputStream
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            outputStream.write(text!!.toByteArray())
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Toast.makeText(this, "File saved", Toast.LENGTH_SHORT).show()
    }

    fun onLoadText(view: View?) {
        // Загрузка значения по ключу SAVED_TEXT
        val text = preferences!!.getString(SAVED_TEXT, "Empty")
        editForText!!.setText(text)
    }
}