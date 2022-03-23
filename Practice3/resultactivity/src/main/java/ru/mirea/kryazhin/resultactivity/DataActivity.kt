package ru.mirea.kryazhin.resultactivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class DataActivity : AppCompatActivity() {
    private var universityEditText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        universityEditText = findViewById<EditText>(R.id.universityEditText)
    }
    fun sendResultOnMainActivityOnClick(view: View?) {
        val intent = Intent()
        intent.putExtra("name", universityEditText!!.text.toString())
        setResult(RESULT_OK, intent)
        finish()
    }
}