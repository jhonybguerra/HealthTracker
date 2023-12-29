package com.jbgcomposer.healthtracker.model

import androidx.room.*

@Dao
interface CalcDao {

    @Insert
    fun insert(calc: Calc)

    @Query("SELECT * from Calc WHERE type = :typeQuery")
    fun getRegisterByType(typeQuery: String) : List<Calc>

    @Delete
    fun delete(calc: Calc): Int

    @Update
    fun update(calc: Calc)

}