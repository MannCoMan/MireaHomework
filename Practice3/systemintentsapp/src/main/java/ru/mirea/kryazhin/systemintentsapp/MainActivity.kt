package ru.mirea.kryazhin.systemintentsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickCall(view: View?) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:89811112233")
        startActivity(intent)
    }

    fun onClickOpenBrowser(view: View?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("http://developer.android.com")
        startActivity(intent)
    }

    fun onClickOpenMaps(view: View?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("geo:55.749479,37.613944")
        startActivity(intent)
    }
}