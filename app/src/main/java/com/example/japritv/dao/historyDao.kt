package com.example.japritv.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface historyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: historyEntity)

    @Query("""
    SELECT h.* FROM history h
    INNER JOIN (
        SELECT idGroup, MAX(timestamp) AS maxTimestamp
        FROM history
        GROUP BY idGroup
    ) groupedHistory
    ON h.idGroup = groupedHistory.idGroup AND h.timestamp = groupedHistory.maxTimestamp
    ORDER BY h.timestamp DESC
""")
    fun getAllHistory(): Flow<List<historyEntity>>

    @Query("DELETE FROM history")
    suspend fun clearAll()
}