package com.example.japritv.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FcmTokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToken(fcmToken: FcmToken)

    @Query("SELECT * FROM fcmtoken LIMIT 1")
    suspend fun getToken(): FcmToken?

    @Query("DELETE FROM fcmtoken")
    suspend fun deleteToken()
}
