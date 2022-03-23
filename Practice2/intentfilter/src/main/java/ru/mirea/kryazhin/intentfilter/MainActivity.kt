package ru.mirea.kryazhin.intentfilter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toast = Toast.makeText(applicationContext, "Здравствуй, MIREA!", Toast.LENGTH_SHORT)
        toast.show()
    }


    fun webBrowser(view: View){
        val address: Uri = Uri.parse("https://www.mirea.ru/")
        val openLinkIntent = Intent(Intent.ACTION_VIEW, address)

        if (openLinkIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(openLinkIntent)
        } else {
            Log.d("Intent", "Не получается обработать намерение!")
        }
    }

    fun setIntent(view: View){
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MIREA")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Кряжин Александр Александрович")
        startActivity(Intent.createChooser(shareIntent, "МОИ ФИО"))
    }



}