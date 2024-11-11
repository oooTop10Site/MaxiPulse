package org.example.project

import android.app.Activity
import android.app.Application
import android.os.Bundle
import org.example.project.di.KoinInjector
import org.example.project.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.dsl.module


class MaxiPulseApp: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
//        MapKitFactory.setApiKey("33aed4ba-1cdd-438c-96b4-7ed82f07520c")
        KoinInjector.koinApp.androidContext(this)
        KoinInjector.loadModules(listOf(platformModule))
//        loadYandexMapToken()
        activityInject()


    }

    private fun activityInject() {
        this.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityResumed(activity: Activity) {
                KoinInjector.koin.loadModules(listOf(module {
                    single { activity }
                }))
            }

            override fun onActivityPaused(activity: Activity) {
                KoinInjector.koin.unloadModules(listOf(module {
                    single { activity }
                }))
            }


            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

            override fun onActivityStarted(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {}


        })
    }

}