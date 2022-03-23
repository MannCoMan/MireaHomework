package ru.mirea.kryazhin.mireaproject.ui.browser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BrowserViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is browser Fragment"
    }
    val text: LiveData<String> = _text
}