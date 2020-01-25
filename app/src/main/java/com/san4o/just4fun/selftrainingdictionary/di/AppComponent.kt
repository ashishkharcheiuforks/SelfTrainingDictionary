package com.san4o.just4fun.selftrainingdictionary.di

import com.san4o.just4fun.selftrainingdictionary.App
import com.san4o.just4fun.selftrainingdictionary.di.modules.AppBindsModule
import com.san4o.just4fun.selftrainingdictionary.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppBindsModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): AppComponent.Builder

        fun build(): AppComponent
    }
}