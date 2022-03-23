package ru.mirea.kryazhin.lifecycleactivity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG,"OnStart()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState()")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.i(TAG, "onPostCreate()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume()")
    }

    override fun onPostResume() {
        super.onPostResume()
        Log.i(TAG, "onPostResume()")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.i(TAG, "onAttachedToWindow()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy()")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.i(TAG, "onDetachedFromWindows()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart()")
    }
}