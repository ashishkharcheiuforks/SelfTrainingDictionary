package com.san4o.just4fun.selftrainingdictionary.di.modules

import androidx.lifecycle.ViewModel
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsDao
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsQuizDao
import com.san4o.just4fun.selftrainingdictionary.data.repositories.IrregularVerbQuizRepositoryImpl
import com.san4o.just4fun.selftrainingdictionary.di.viewmodel.ViewModelFactory
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbQuizRepository

import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module(
    includes = [
        DatabaseModule::class
    ]
)
class AppModule {


    @Provides
    fun provideViewModelFactory(
        creators: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
    ): ViewModelFactory {
        return ViewModelFactory(creators)
    }

    @Provides
    fun provideIrregularVerbQuizRepository(
        verbsDao: IrregularVerbsDao,
        quizDao: IrregularVerbsQuizDao
    ): IrregularVerbQuizRepository {
        return IrregularVerbQuizRepositoryImpl(verbsDao, quizDao)
    }
}