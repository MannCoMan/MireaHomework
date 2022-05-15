package ru.mirea.kryazhin.networkstate


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer


class MainActivity : AppCompatActivity() {
    private var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)

        val networkLoveData: NetworkLiveData? = NetworkLiveData.getInstance(this)
        networkLoveData!!.observe(this, object : Observer<String?> {
            override fun onChanged(s: String?) {
                textView?.setText(s)
            }
        })
    }
}