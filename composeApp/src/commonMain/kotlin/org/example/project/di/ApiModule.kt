package org.example.project.di

import de.jensklingenberg.ktorfit.Ktorfit
import org.example.project.data.api.MaxiPulseApi
import org.example.project.data.api.createAiApi
import org.example.project.data.api.createMaxiPulseApi
import org.example.project.utils.Constants
import org.koin.dsl.module

internal val apiModule = module {
    factory { provideMaxiKtorHttpClient(get(), Constants.BASE_URL, get()).createMaxiPulseApi() }
    factory { provideAiKtorHttpClient(get(), Constants.BASE_URL_AI, get()).createAiApi() }
}