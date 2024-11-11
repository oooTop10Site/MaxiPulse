package org.example.project.di

import org.example.project.platform.MultipartManager
import org.example.project.platform.MultipartManagerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val platformModule = module {
    singleOf(::MultipartManagerImpl) { bind<MultipartManager>() }
}