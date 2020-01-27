package com.san4o.just4fun.selftrainingdictionary.di.modules.subcomponents

import androidx.lifecycle.ViewModel
import com.san4o.just4fun.selftrainingdictionary.di.viewmodel.ViewModelKey
import com.san4o.just4fun.selftrainingdictionary.domain.IrregularVerbRepository
import com.san4o.just4fun.selftrainingdictionary.presentation.irregular.list.IrregualrVersListViewModel
import com.san4o.just4fun.selftrainingdictionary.ui.IrrgularVerbsListFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(
    includes = [
        IrregularVerbsListBindsModule::class
    ]
)
class IrregularVerbsListModule {

    @Provides
    fun provideIrregualrVersListViewModel(repository: IrregularVerbRepository) =
        IrregualrVersListViewModel(
            repository
        )
}

@Module
interface IrregularVerbsListBindsModule {

    @ContributesAndroidInjector
    fun irrgularVerbsListFragmentInjector(): IrrgularVerbsListFragment


    @Binds
    @IntoMap
    @ViewModelKey(IrregualrVersListViewModel::class)
    fun irrgularVerbsListViewModel(viewModel: IrregualrVersListViewModel): ViewModel
}