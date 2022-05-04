package ru.mirea.kryazhin.preferences

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var editText: EditText? = null
    private var textView: TextView? = null
    private var preferences: SharedPreferences? = null
    val SAVED_TEXT = "saved_text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        preferences = getPreferences(MODE_PRIVATE);
    }

    fun onSaveText(view: View?) {
        val editor = preferences!!.edit()
        // Сохранение значения по ключу SAVED_TEXT
        editor.putString(SAVED_TEXT, editText!!.text.toString())
        editor.apply()
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show()
    }

    fun onLoadText(view: View?) {
        // Загрузка значения по ключу SAVED_TEXT
        val text = preferences!!.getString(SAVED_TEXT, "Empty")
        textView!!.text = text
    }
}