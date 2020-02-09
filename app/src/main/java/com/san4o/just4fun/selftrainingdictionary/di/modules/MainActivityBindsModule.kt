package com.san4o.just4fun.selftrainingdictionary.di.modules

import com.san4o.just4fun.selftrainingdictionary.ui.IrregularVerbsListFragment
import com.san4o.just4fun.selftrainingdictionary.ui.quiz.IrregularVerbQuizFragment
import com.san4o.just4fun.selftrainingdictionary.ui.quiz.IrregularVerbQuizResultErrorFragment
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
    fun WriteAnswersIrregularVerbQuizFragmentInjector(): IrregularVerbQuizFragment

    @ContributesAndroidInjector
    fun IrregularVerbsListFragmentInjector(): IrregularVerbsListFragment

    @ContributesAndroidInjector
    fun IrregularVerbQuizResultErrorFragmentInjector(): IrregularVerbQuizResultErrorFragment
}