package com.san4o.just4fun.selftrainingdictionary.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsDao
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbWordEntity
import kotlinx.coroutines.runBlocking
import org.apache.commons.io.IOUtils
import timber.log.Timber
import java.nio.charset.StandardCharsets

@Database(
    entities = [
        IrregularVerbWordEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun provideIrregularVerbsDao(): IrregularVerbsDao

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "selfttrainning.disctionary.db"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also {
                    insertIrregularVerbsFirstValues(it, context)
                }
        }

        private fun insertIrregularVerbsFirstValues(database: AppDatabase, context: Context) =
            runBlocking {

                val inputStream = context.assets.open("irregular_verbs.txt")
                val readLines = IOUtils.readLines(inputStream, StandardCharsets.UTF_8)

                readLines.forEach { Timber.d(it) }
                IrregularVerbsProvider.insertFirstValues(
                    database.provideIrregularVerbsDao(),
                    context
                )
            }
    }
}