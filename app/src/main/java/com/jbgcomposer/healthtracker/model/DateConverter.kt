package com.jbgcomposer.healthtracker.model

import androidx.room.TypeConverter
import java.util.Date


object DateConverter {

    @TypeConverter
    fun dateToLong(date: Date?) : Long? {
        return date?.time
    }

    @TypeConverter
    fun longToDate(dateLong: Long?) : Date? {
        return if(dateLong != null) return Date(dateLong) else return null
    }
}