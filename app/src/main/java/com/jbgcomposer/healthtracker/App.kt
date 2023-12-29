package com.jbgcomposer.healthtracker

import android.app.Application
import com.jbgcomposer.healthtracker.model.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }
}