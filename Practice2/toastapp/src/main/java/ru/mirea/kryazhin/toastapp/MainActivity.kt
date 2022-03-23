package ru.mirea.kryazhin.toastapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toast = Toast.makeText(applicationContext, "Здравствуй, MIREA! Кряжин Александр Александрович", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0,0)
        val toastContainer = toast.view as LinearLayout
        val catImageView = ImageView(applicationContext)
        catImageView.setImageResource(R.drawable.ic_launcher_background)
        toastContainer.addView(catImageView,0)
        toast.show()
    }
}