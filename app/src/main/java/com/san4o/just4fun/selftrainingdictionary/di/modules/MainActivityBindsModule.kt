package com.san4o.just4fun.selftrainingdictionary.di.modules

import com.san4o.just4fun.selftrainingdictionary.ui.IrregularVerbsListFragment
import com.san4o.just4fun.selftrainingdictionary.ui.quiz.IrregularVerbWriteQuizFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [
        MainViewModelsBindsModule::class,
        MainViewModelModule::class,
        MainModule::class
    ]
)
interface MainActivityBindsModule {

    @ContributesAndroidInjector
    fun IrregularVerbWriteQuizFragmentInjector(): IrregularVerbWriteQuizFragment

    @ContributesAndroidInjector
    fun IrregularVerbsListFragmentInjector(): IrregularVerbsListFragment
}