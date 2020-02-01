package com.san4o.just4fun.selftrainingdictionary.di.modules

import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbRepository
import com.san4o.just4fun.selftrainingdictionary.presentation.irregular.list.IrregularVersListViewModel
import com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quizwrite.IrregularVerbWriteQuizViewModel
import dagger.Module
import dagger.Provides

@Module
class MainViewModelModule {
    @Provides
    fun provideIrregualrVersListViewModel(repository: IrregularVerbRepository) =
        IrregularVersListViewModel(
            repository
        )

    @Provides
    fun provideIrregularVerbWriteQuizViewModel(repository: IrregularVerbRepository) =
        IrregularVerbWriteQuizViewModel(
            repository
        )
}