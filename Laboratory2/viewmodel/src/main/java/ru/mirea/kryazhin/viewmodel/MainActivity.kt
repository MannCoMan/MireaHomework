package ru.mirea.kryazhin.viewmodel


import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        // получение доступа к создайнной ViewModel
        val viewModel = ViewModelProvider(this).get(
            ProgressViewModel::class.java
        )
        viewModel.getProgressState().observe(this, object : Observer<Boolean> {
            override fun onChanged(isVisibleProgressBar: Boolean) {
                if (isVisibleProgressBar) {
                    progressBar?.setVisibility(View.VISIBLE)
                } else {
                    progressBar?.setVisibility(View.GONE)
                }
            }
        })
        viewModel.showProgress()
    }
}