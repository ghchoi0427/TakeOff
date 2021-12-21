package com.choi.takeoff.db

import androidx.annotation.WorkerThread
import com.choi.takeoff.db.dao.MemoDao
import com.choi.takeoff.db.entity.Memo
import kotlinx.coroutines.flow.Flow

class MemoRepository(private val memoDao: MemoDao) {

    val allMemos: Flow<List<Memo>> = memoDao.loadAllMemos()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(memo: Memo) {
        memoDao.insertMemos(memo)
    }
}