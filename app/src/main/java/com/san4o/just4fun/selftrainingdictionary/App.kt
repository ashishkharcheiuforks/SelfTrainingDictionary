package com.san4o.just4fun.selftrainingdictionary

import android.app.Application
import com.san4o.just4fun.selftrainingdictionary.di.AppComponent
import com.san4o.just4fun.selftrainingdictionary.di.DaggerAppComponent
import com.san4o.just4fun.selftrainingdictionary.di.lifecycle.DaggerActivityLifecycleCallback
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    lateinit var daggerComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        registerActivityLifecycleCallbacks(DaggerActivityLifecycleCallback())
        initDagger(this)
    }

    private fun initDagger(app: App) {
        daggerComponent = DaggerAppComponent.builder()
            .application(app)
            .build()

        daggerComponent.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}