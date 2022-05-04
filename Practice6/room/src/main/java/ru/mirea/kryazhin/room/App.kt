package ru.mirea.kryazhin.room

import android.app.Application
import androidx.room.Room


class App : Application() {
    private lateinit var database: AppDatabase

    companion object {
        private lateinit var instance: App

        fun getInstance(): App {
            return instance
        }


    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }
    fun getDatabase(): AppDatabase {
        return database
    }



}