package ru.mirea.kryazhin.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MyTimeDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val calendar = Calendar.getInstance()
            val hour = calendar[Calendar.HOUR_OF_DAY]
            val minute = calendar[Calendar.MINUTE]

            var timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker,
                                                                       hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
            }
            val mTimeDialog = TimePickerDialog(
                activity, timeSetListener,
                hour, minute, false
            )
            return mTimeDialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}