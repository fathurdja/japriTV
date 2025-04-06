package com.example.japritv.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.japritv.converters.Converter

@Database(
    entities = [VideoData::class, AuthToken::class, LoginInfo::class, newAuthDao::class,FcmToken::class],
    version = 13,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
    abstract fun authTokenDao(): AuthTokenDao
    abstract fun loginInfoDao(): LoginInfoDao
    abstract fun NewauthTokenDao(): newInterfaceAuth
    abstract fun fcmToken(): FcmTokenDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "video-db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}