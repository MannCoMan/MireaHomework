package ru.mirea.kryazhin.intentapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var dateInMillis = System.currentTimeMillis()
        var format = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(format)
        var dateString: String = sdf.format(Date(dateInMillis))

        var intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("date", dateString)
        startActivity(intent)
    }
}