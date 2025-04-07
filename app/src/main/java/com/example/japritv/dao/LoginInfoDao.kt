package com.example.japritv.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LoginInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLoginInfo(loginInfo: LoginInfo)

    @Query("SELECT * FROM login_info LIMIT 1")
    suspend fun getLoginInfo(): LoginInfo?

    @Query("SELECT tokenAuth FROM login_info WHERE id = 1")
    suspend fun getTokenAuth(): String?

    @Query("DELETE FROM login_info")
    suspend fun clearLoginInfo()

    @Query("SELECT role FROM login_info WHERE id = 1")
    suspend fun getRoleAccount(): String?
}