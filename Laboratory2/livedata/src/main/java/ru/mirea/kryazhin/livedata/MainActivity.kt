package ru.mirea.kryazhin.livedata

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer



class MainActivity : AppCompatActivity(), Observer<String?> {
    private var networkNameTextView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        networkNameTextView = findViewById(R.id.textView)
        TimeLiveData.getDate()?.observe(this, this)
        val handler = Handler()
        handler.postDelayed(TimeLiveData::setTime, 5000)
    }

    override fun onChanged(@Nullable s: String?) {
        Log.d(MainActivity::class.java.simpleName, s + "")
        networkNameTextView!!.text = "" + s
    }
}