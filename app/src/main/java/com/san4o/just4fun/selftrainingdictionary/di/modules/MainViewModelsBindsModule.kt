package com.san4o.just4fun.selftrainingdictionary.di.modules

import androidx.lifecycle.ViewModel
import com.san4o.just4fun.selftrainingdictionary.di.viewmodel.ViewModelKey
import com.san4o.just4fun.selftrainingdictionary.presentation.irregular.list.IrregularVersListViewModel
import com.san4o.just4fun.selftrainingdictionary.presentation.irregular.quizwrite.IrregularVerbWriteQuizViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainViewModelsBindsModule {

    @Binds
    @IntoMap
    @ViewModelKey(IrregularVersListViewModel::class)
    fun IrregularVersListViewModel(viewModel: IrregularVersListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IrregularVerbWriteQuizViewModel::class)
    fun IrregularVerbWriteQuizViewModel(viewModel: IrregularVerbWriteQuizViewModel): ViewModel
}