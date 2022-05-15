package ru.mirea.kryazhin.livedata

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import java.text.SimpleDateFormat
import java.util.*
import androidx.arch.core.util.Function

class TimeLiveData {
    companion object{
    private val data = MutableLiveData<Long>()

    fun getTime(): MutableLiveData<Long>? {
        data.setValue(Date().getTime())
        return data
    }

    fun setTime() {
        data.setValue(Date().getTime())
    }

    // преобразование long в дату

    private val getStringTime: LiveData<String?>? =
        Transformations.map(data, object : Function<Long?, String?> {
            override fun apply(input: Long?): String? {
                val calendar = Calendar.getInstance()
                @SuppressLint("SimpleDateFormat") val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                return df.format(calendar.time)
            }
        })

    fun getDate(): LiveData<String?>? {
        return getStringTime
    }}
}