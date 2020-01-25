package com.san4o.just4fun.selftrainingdictionary.di.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.san4o.just4fun.selftrainingdictionary.di.inject

class DaggerActivityLifecycleCallback : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        activity.inject()
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                DaggerFragmentLifecycleCallbacks(),
                true
            )
        }
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }


}

private class DaggerFragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        if (f !is NavHostFragment) {
            f.inject()
        } else {
            f.childFragmentManager.registerFragmentLifecycleCallbacks(
                DaggerFragmentLifecycleCallbacks(),
                true
            )
        }
    }

}