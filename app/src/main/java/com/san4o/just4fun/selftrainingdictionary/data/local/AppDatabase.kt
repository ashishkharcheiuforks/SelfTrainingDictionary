package com.san4o.just4fun.selftrainingdictionary.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.san4o.just4fun.selftrainingdictionary.data.local.converters.DateConverter
import com.san4o.just4fun.selftrainingdictionary.data.local.converters.IrregularVerbQuizResultConverter
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsDao
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsQuizDao
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbQuizEntity
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbQuizItemEntity
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbWordEntity
import kotlinx.coroutines.runBlocking
import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets

@Database(
    entities = [
        IrregularVerbWordEntity::class,
        IrregularVerbQuizEntity::class,
        IrregularVerbQuizItemEntity::class
    ],
    version = 1
)
@TypeConverters(
    DateConverter::class,
    IrregularVerbQuizResultConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun provideIrregularVerbsDao(): IrregularVerbsDao
    abstract fun provideIrregularVerbsQuizDao(): IrregularVerbsQuizDao

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


                IrregularVerbsProvider.insertFirstValues(
                    database.provideIrregularVerbsDao(),
                    context
                )
            }
    }
}