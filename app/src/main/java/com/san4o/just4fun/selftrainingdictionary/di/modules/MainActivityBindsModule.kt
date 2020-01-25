package com.san4o.just4fun.selftrainingdictionary.di.modules

import com.san4o.just4fun.selftrainingdictionary.di.modules.subcomponents.IrregularVerbsListModule
import dagger.Module

@Module(
    includes = [
        IrregularVerbsListModule::class
    ]
)
interface MainActivityBindsModule {


}