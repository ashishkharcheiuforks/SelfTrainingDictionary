    package com.san4o.just4fun.selftrainingdictionary.di.modules

import android.content.Context
import com.san4o.just4fun.selftrainingdictionary.data.local.AppDatabase
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsDao
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
    fun provide(database: AppDatabase): IrregularVerbsDao {
        return database.provideIrregularVerbsDao()
    }
}