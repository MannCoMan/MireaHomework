package ru.mirea.kryazhin.room

import androidx.room.*


@Dao
interface EmployeeDao {
    @get:Query("SELECT * FROM employee")
    val all: List<Employee?>?

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getById(id: Long): Employee?

    @Insert
    fun insert(employee: Employee?)

    @Update
    fun update(employee: Employee?)

    @Delete
    fun delete(employee: Employee?)
}