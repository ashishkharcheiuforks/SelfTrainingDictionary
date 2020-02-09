    package com.san4o.just4fun.selftrainingdictionary.di.modules

import android.content.Context
import com.san4o.just4fun.selftrainingdictionary.data.local.AppDatabase
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsDao
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsQuizDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        context: Context
    ): AppDatabase {
        return AppDatabase.create(context)
    }

    @Provides
    fun provideIrregularVerbsDao(database: AppDatabase): IrregularVerbsDao =
        database.provideIrregularVerbsDao()

    @Provides
    fun provideIrregularVerbsQuizDao(database: AppDatabase): IrregularVerbsQuizDao =
        database.provideIrregularVerbsQuizDao()

}