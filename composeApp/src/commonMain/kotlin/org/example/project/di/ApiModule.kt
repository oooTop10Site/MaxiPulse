package org.example.project.di

import de.jensklingenberg.ktorfit.Ktorfit
import org.example.project.data.api.MaxiPulseApi
import org.example.project.data.api.createMaxiPulseApi
import org.koin.dsl.module

internal val apiModule = module {
    factory { get<Ktorfit>().createMaxiPulseApi() }
}