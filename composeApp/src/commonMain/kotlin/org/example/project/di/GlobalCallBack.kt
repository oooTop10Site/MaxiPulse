package org.example.project.di


import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module

 class GlobalCallBacks<T>(

) {
    /**Очищение всех callback'ов*/
    fun clearCallBacks() {
    }
}


val callBackModule = module {
    single { GlobalCallBacks<Unit>() }
}
