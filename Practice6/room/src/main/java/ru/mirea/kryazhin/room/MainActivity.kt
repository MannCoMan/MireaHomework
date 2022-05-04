package ru.mirea.kryazhin.room

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db: AppDatabase = App.getInstance().getDatabase()
        val employeeDao = db.employeeDao()
        var employee: Employee? = Employee()
        employee!!.id = 1
        employee.name = "John Smith"
        employee.salary = 10000
        // запись сотрудников в базу
        employeeDao!!.insert(employee)
        // Загрузка всех работников
        val employees = employeeDao.all
        // Получение определенного работника с id = 1
        employee = employeeDao.getById(1)
        // Обновление полей объекта
        employee!!.salary = 20000
        employeeDao.update(employee)
        Log.d(TAG, employee.name + " " + employee.salary)
    }
}