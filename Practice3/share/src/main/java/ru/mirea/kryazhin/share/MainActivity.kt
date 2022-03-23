package ru.mirea.kryazhin.share

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_TEXT, "Mirea")
        startActivity(Intent.createChooser(intent, "Выбор за вами!"))
    }
}