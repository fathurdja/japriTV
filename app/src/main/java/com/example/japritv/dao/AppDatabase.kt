package com.example.japritv.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.japritv.converters.Converter

@Database(entities = [VideoData::class], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}