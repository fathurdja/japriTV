package com.example.japritv.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PaymentDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(payment: PaymentDataEntity)

    @Query("SELECT * FROM payment_data LIMIT 1")
    suspend fun getLatestPayment(): PaymentDataEntity?

    @Query("DELETE FROM payment_data")
    suspend fun clearAll()

    @Query("DELETE FROM payment_data WHERE timestamp < :timeThreshold")
    suspend fun clearIfOlderThan(timeThreshold: Long)
}