package com.san4o.just4fun.selftrainingdictionary.di.modules

import android.app.Application
import android.content.Context
import com.san4o.just4fun.selftrainingdictionary.App
import com.san4o.just4fun.selftrainingdictionary.ui.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AppBindsModule {
    @Binds
    fun application(app: App): Application

    @Binds
    fun context(app: App): Context

    @ContributesAndroidInjector(
        modules = [
            MainActivityBindsModule::class
        ]
    )
    fun mainActivityInjector(): MainActivity
}