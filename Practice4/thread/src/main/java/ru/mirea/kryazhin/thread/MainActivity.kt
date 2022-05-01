package ru.mirea.kryazhin.thread

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    var counter: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val infoTextView: TextView = findViewById(R.id.thread)
        val mainThread: Thread = Thread.currentThread()
        infoTextView.setText("Текущий поток: " + mainThread.name)
        mainThread.setName("MireaThread")
        infoTextView.append("\nНовое имя потока: " + mainThread.name)
    }

    fun onClick(view: View?) {
        val runnable: Runnable = object : Runnable {
            override fun run() {
                val numberThread: Int = counter++
                Log.i("ThreadProject", "Запущен поток № $numberThread")
                val endTime = System.currentTimeMillis() + 20 * 1000
                while (System.currentTimeMillis() < endTime) {
                    synchronized(this) {
                        try {
                            sleep(endTime - System.currentTimeMillis())
                        } catch (e: Exception) {
                        }
                    }
                }
                Log.i("ThreadProject", "Выполнен поток № $numberThread")
            }
        }
        val thread = Thread(runnable)
        thread.start()
    }

    fun Count(view: View?) {
        val lessons = findViewById<EditText>(R.id.lessons)
        val studyDays = findViewById<EditText>(R.id.studyDays)
        val resultText = findViewById<TextView>(R.id.result)
        val runnable = Runnable {
            val num1 = lessons.text.toString().toInt().toFloat()
            val num2 = studyDays.text.toString().toInt().toFloat()
            val result = num1 / num2
            resultText.text = java.lang.Float.toString(result)
        }
        val thread = Thread(runnable)
        thread.start()
    }
}