package com.example.japritv.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PaymentConfigDao {
    @Query("SELECT * FROM payment_config LIMIT 1")
    suspend fun getConfig(): PaymentConfigEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfig(config: PaymentConfigEntity)

    @Query("DELETE FROM payment_config")
    suspend fun clearConfig()
}
