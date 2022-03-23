package ru.mirea.kryazhin.mireaproject.ui.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is calculator Fragment"
    }
    val text: LiveData<String> = _text
}