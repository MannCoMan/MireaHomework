package ru.mirea.kryazhin.resultactivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var textViewResult: TextView? = null
    private val REQUEST_CODE = 143
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewResult = findViewById<TextView>(R.id.textViewResult)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val university = data.getStringExtra("name")
            setUniversityTextView(university)
        }
    }

    fun startDataActivityOnClick(view: View?) {
        val intent = Intent(this, DataActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun setUniversityTextView(university: String?) {
        textViewResult!!.text = university
    }
}