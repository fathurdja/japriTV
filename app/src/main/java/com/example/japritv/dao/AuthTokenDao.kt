package com.example.japritv.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthTokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToken(authToken: AuthToken)

    @Query("SELECT * FROM auth_token LIMIT 1")
    suspend fun getToken(): AuthToken?

    @Query("DELETE FROM auth_token")
    suspend fun deleteToken()
}