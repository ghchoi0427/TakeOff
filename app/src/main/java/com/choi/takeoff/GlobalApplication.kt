package com.choi.takeoff

import android.app.Application
import com.choi.takeoff.db.MemoDatabase
import com.choi.takeoff.db.MemoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GlobalApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { MemoDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MemoRepository(database.memoDao()) }
}