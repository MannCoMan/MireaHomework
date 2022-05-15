package ru.mirea.kryazhin.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel


class ProgressViewModel : ViewModel() {
    private val isShowProgress = MutableLiveData<Boolean>()
    fun getProgressState(): MutableLiveData<Boolean> {
        return isShowProgress
    }


    fun showProgress() {
        isShowProgress.postValue(true)
        Handler().postDelayed(Runnable { isShowProgress.postValue(false) }, 10000)
    }
}
