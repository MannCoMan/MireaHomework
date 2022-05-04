package ru.mirea.kryazhin.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Employee {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var name: String? = null
    var salary = 0
}