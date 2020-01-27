package com.san4o.just4fun.selftrainingdictionary.di.modules

import com.san4o.just4fun.selftrainingdictionary.di.modules.subcomponents.IrregularVerbsListModule
import com.san4o.just4fun.selftrainingdictionary.ui.IrregularVerbWriteQuizFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [
        IrregularVerbsListModule::class
    ]
)
interface MainActivityBindsModule {

    @ContributesAndroidInjector
    fun IrregularVerbWriteQuizFragmentInjector(): IrregularVerbWriteQuizFragment
}