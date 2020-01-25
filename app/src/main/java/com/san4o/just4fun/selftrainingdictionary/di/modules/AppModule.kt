package com.san4o.just4fun.selftrainingdictionary.di.modules

import androidx.lifecycle.ViewModel
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsDao
import com.san4o.just4fun.selftrainingdictionary.data.repositories.IrregularVerbRepositoryImpl
import com.san4o.just4fun.selftrainingdictionary.di.viewmodel.ViewModelFactory
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbRepository
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module(
    includes = [
        DatabaseModule::class
    ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(
        creators: Map<Class<out ViewModel?>, Provider<ViewModel?>>
    ): ViewModelFactory {
        return ViewModelFactory(creators)
    }


    @Provides
    fun provideIrregularVerbRepository(
        dao: IrregularVerbsDao
    ): IrregularVerbRepository {
        return IrregularVerbRepositoryImpl(dao)
    }
}