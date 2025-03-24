package com.example.japritv.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface newInterfaceAuth {

    @Query("SELECT * FROM auth_token_New WHERE id = 1 LIMIT 1")
    suspend fun getToken(): newAuthDao?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToken(authToken: newAuthDao)

    @Query("DELETE FROM auth_token_New WHERE id = 1")
    suspend fun clearToken()
}