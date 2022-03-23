package ru.mirea.kryazhin.dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.DialogFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickShowDialog(view: View){
        val dialogFragment = AlertDialogFragment()
        dialogFragment.show(supportFragmentManager, "mirea")
    }

    fun onOkClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Иду дальше\"!",
            Toast.LENGTH_LONG).show();
    }

    fun onCancelClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Нет\"!",
            Toast.LENGTH_LONG).show();
    }

    fun onNeutralClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"На паузе\"!",Toast.LENGTH_LONG).show()
    }

    fun onClickShowTimeDialog(view: View){
        val timeDialogFragment = MyTimeDialogFragment()
        timeDialogFragment.show(supportFragmentManager, "time picker")
    }
    fun onClickShowDateDialog(view: View){
        val dateDialogFragment = MyDateDialogFragment()
        dateDialogFragment.show(supportFragmentManager, "date picker")
    }
    fun onClickShowProgressDialog(view: View){
        val progressDialogFragment = MyProgressDialogFragment()
        progressDialogFragment.show(supportFragmentManager, "progress picker")
    }
}