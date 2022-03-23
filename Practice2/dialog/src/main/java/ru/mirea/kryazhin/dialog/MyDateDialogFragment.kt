package ru.mirea.kryazhin.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class MyDateDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]

            var daySetListener = DatePickerDialog.OnDateSetListener { datePicker,
                                                                      year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
            }
            val mDateDialog = DatePickerDialog(
                requireActivity(), daySetListener,
                year, month, day
            )
            return mDateDialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}