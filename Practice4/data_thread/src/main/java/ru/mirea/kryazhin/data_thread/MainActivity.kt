package ru.mirea.kryazhin.data_thread

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var tvInfo = findViewById<View>(R.id.textView) as TextView
        val runn1 = Runnable { tvInfo.setText("runn1") }
        val runn2 = Runnable { tvInfo.setText("runn2") }
        val runn3 = Runnable { tvInfo.setText("runn3") }
        val t = Thread {
            try {
                TimeUnit.SECONDS.sleep(2)
                runOnUiThread(runn1)
                TimeUnit.SECONDS.sleep(1)
                tvInfo.postDelayed(runn3, 2000)
                tvInfo.post(runn2)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        t.start()
    }
}