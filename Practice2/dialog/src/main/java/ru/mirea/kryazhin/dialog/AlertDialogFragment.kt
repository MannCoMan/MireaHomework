package ru.mirea.kryazhin.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class AlertDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Здравствуй, МИРЕА!")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Успех близок?")
                .setPositiveButton(
                    "Иду дальше",
                    DialogInterface.OnClickListener { dialog, id ->
                        (activity as MainActivity?)!!.onOkClicked()
                        dialog.cancel()
                    })
                .setNeutralButton(
                    "На паузе",
                    DialogInterface.OnClickListener { dialog, id ->
                        (activity as MainActivity?)!!.onNeutralClicked()
                        dialog.cancel()
                    })
                .setNegativeButton(
                    "Нет",
                    DialogInterface.OnClickListener { dialog, id ->
                        (activity as MainActivity?)!!.onCancelClicked()
                        dialog.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

