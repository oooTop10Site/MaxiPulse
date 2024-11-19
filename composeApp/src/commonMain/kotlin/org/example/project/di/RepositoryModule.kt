package org.example.project.di

import org.example.project.data.repository.AuthRepositoryImpl
import org.example.project.data.repository.GamerRepositoryImpl
import org.example.project.data.repository.GroupRepositoryImpl
import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.repository.GamerRepository
import org.example.project.domain.repository.GroupRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    factoryOf(::GamerRepositoryImpl) { bind<GamerRepository>() }
    factoryOf(::GroupRepositoryImpl) { bind<GroupRepository>() }
}