package org.example.project.di

//import com.plusmobileapps.konnectivity.Konnectivity
import org.example.project.domain.manager.AuthManager
import org.example.project.domain.manager.AuthManagerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val moduleManager = module {
//    singleOf(::Konnectivity)
    singleOf(::AuthManagerImpl) { bind<AuthManager>() }
}