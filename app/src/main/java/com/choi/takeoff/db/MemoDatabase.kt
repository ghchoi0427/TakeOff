package com.choi.takeoff.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.choi.takeoff.db.dao.MemoDao
import com.choi.takeoff.db.entity.Converters
import com.choi.takeoff.db.entity.Memo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@TypeConverters(Converters::class)
@Database(entities = [Memo::class], version = 1, exportSchema = false)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao

    private class MemoDatabaseCallback(
        private val scope: CoroutineScope,
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    //TODO: pre-fill database
                }
            }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: MemoDatabase? = null

        fun getDatabase(
            context: Context, scope: CoroutineScope,
        ): MemoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    MemoDatabase::class.java,
                    "memo_database")
                    .addCallback(MemoDatabaseCallback(scope))
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}