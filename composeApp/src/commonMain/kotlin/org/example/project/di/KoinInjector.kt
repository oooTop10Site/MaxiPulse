package org.example.project.di

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.example.project.platform.permission.platformPermissionsModule

object KoinInjector {

    val koinApp = startKoin{

        loadKoinModules(
            listOf(
                networkModule,
                apiModule,
                moduleManager,
                preferencesModule,
                repositoryModule,
                platformPermissionsModule,
                callBackModule
            )
        )

    }

    val koin = koinApp.koin

    fun loadModules(modules: List<Module>){
        koinApp.koin.loadModules(modules)
    }
}