package com.choi.takeoff.db.dao

import androidx.room.*
import com.choi.takeoff.db.entity.Memo
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemos(vararg memos: Memo?)

    @Update
    fun updateMemos(vararg memos: Memo)

    @Delete
    fun deleteMemos(vararg memos: Memo)

    @Query("SELECT *, rowid FROM memo")
    fun loadAllMemos(): Flow<List<Memo>>

    //TODO: fix this s**t
    @Query("SELECT *, rowid FROM memo WHERE tags LIKE :search")
    fun findMemoWithTag(search: String): Flow<List<Memo>>

    @Query("SELECT *, rowid FROM memo WHERE rowid LIKE :search")
    fun findMemoWithId(search: Int): Memo

    @Query("DELETE FROM memo WHERE rowid LIKE :search")
    fun deleteMemoWithId(search: Int)
}