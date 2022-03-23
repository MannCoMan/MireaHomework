package ru.mirea.kryazhin.simplefragmentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    var fragment1: Fragment? = null
    var fragment2: Fragment? = null
    var fragmentManager: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        fragmentManager = supportFragmentManager
        when (view.getId()) {
            R.id.btnFragment1 -> fragmentManager!!.beginTransaction().replace(
                R.id.fragmentContainer,
                fragment1!!
            ).commit()
            R.id.btnFragment2 -> fragmentManager!!.beginTransaction().replace(
                R.id.fragmentContainer,
                fragment2!!
            ).commit()
            else -> {}
        }
    }
}