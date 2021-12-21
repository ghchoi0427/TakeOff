package com.choi.takeoff.db.dao

import androidx.room.*
import com.choi.takeoff.db.entity.Memo
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemos(vararg memos: Memo)

    @Update
    fun updateMemos(vararg memos: Memo)

    @Delete
    fun deleteMemos(vararg memos: Memo)

    @Query("SELECT * FROM memo")
    fun loadAllMemos(): Flow<Array<Memo>>

    //TODO: fix this s**t
    @Query("SELECT * FROM memo WHERE tags LIKE :search")
    fun findMemoWithTag(search: String): Flow<List<Memo>>
}