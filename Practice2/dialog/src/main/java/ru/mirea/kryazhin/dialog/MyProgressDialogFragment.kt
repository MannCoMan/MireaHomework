package ru.mirea.kryazhin.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.app.ProgressDialog.STYLE_SPINNER
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyProgressDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            return ProgressDialog(activity, STYLE_SPINNER)
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}