package ru.mirea.kryazhin.intentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val textView = findViewById<TextView>(R.id.textView)

        val dateString = intent.getStringExtra("date")
        textView.text = dateString
    }
}