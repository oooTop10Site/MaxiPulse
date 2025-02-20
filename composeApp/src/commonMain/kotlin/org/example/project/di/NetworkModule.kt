package org.example.project.di

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.HttpSendPipeline
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.example.project.domain.manager.AuthManager
import org.example.project.ext.toToken
import org.example.project.platform.Failure
import org.example.project.utils.Constants
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val networkModule = module {

    factoryOf(::provideJson)
    factoryOf(::provideHttpClient)
    factory { provideAiKtorHttpClient(get(), Constants.BASE_URL_AI, get()) }
    factory { provideMaxiKtorHttpClient(get(), Constants.BASE_URL, get()) }

}

typealias KtorfitAI = Ktorfit
typealias KtorfitMaxi = Ktorfit


@OptIn(ExperimentalSerializationApi::class)
private fun provideJson(): Json {
    return Json {
        isLenient = true
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = true
        prettyPrint = true
    }
}

private const val TIME_OUT = 20000L

private fun provideHttpClient(
    json: Json,
) = HttpClient {
//    val konnectivity = Konnectivity()
    install(HttpRedirect) {
        checkHttpMethod = false
        allowHttpsDowngrade = false
    }

    defaultRequest {
        header("Content-Type", "application/json")
    }


    install(ContentNegotiation) {
        json(json)
    }

    HttpResponseValidator {
        handleResponseExceptionWithRequest { cause, request ->
            println(cause)
            println(request)
//            if(!konnectivity.isConnected) {
//                throw Failure.Message(getString(Res.string.not_connected))
//            } else {
//                throw Failure.HardMessage(getString(Res.string.try_after))
//            }
        }

        validateResponse { response ->

            val error =
                response.status != HttpStatusCode.OK
                        && response.status != HttpStatusCode.Created
                        && response.status != HttpStatusCode.NoContent
                        && response.status != HttpStatusCode.BadRequest
                        && response.status != HttpStatusCode.Forbidden

            if (error) {
                val body = response.bodyAsText()

                tryCatch {
                    val message =
                        json.decodeFromString(ErrorMessage.serializer(), body)
                    message.detail ?: return@tryCatch
                    throw Failure.Http(code = response.status.value, message.detail)
                }

                tryCatch {
                    val message =
                        json.decodeFromString(ErrorDetailMessage.serializer(), body)
                    message.detail?.message ?: return@tryCatch
                    throw Failure.Http(
                        code = response.status.value,
                        message.detail.message
                    )
                }

                tryCatch {
                    val message =
                        json.decodeFromString(Message.serializer(), body)
                    message.message ?: return@tryCatch
                    throw Failure.Http(
                        code = response.status.value,
                        message.message
                    )
                }

                tryCatch {
                    val message =
                        json.decodeFromString(Message.serializer(), body)
                    message.message ?: return@tryCatch
                    throw Failure.Http(
                        code = response.status.value,
                        message.message
                    )
                }
                throw Failure.Message("Попробуйте позже")

            }
        }
    }

    install(HttpTimeout) {
        connectTimeoutMillis = TIME_OUT
        socketTimeoutMillis = TIME_OUT
        requestTimeoutMillis = TIME_OUT
    }

    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
        // Добавьте дополнительные настройки для логирования конкретных частей запроса/ответа
    }

}

fun provideMaxiKtorHttpClient(
    httpClient: HttpClient,
    baseUrl: String,
    authManager: AuthManager
): KtorfitMaxi {
    httpClient.sendPipeline.intercept(HttpSendPipeline.State){
//        if (authManager.token!!.isNotBlank()){
//            if (authManager.token != null){
//                context.headers["Authorization"] = authManager.token!!.toToken()
//            }else{
//                context.headers["Authorization"] = ""
//            }
//        }
        if (authManager.token != null){
            context.headers["Authorization"] = authManager.token!!.toToken()
        }else{
//            context.headers["Authorization"] = ""
        }
//        proceedWith(subject)
    }
    return ktorfit {
        baseUrl(baseUrl)
        httpClient(httpClient)
    }
}

fun provideAiKtorHttpClient(
    httpClient: HttpClient,
    baseUrl: String,
    authManager: AuthManager
): KtorfitAI {
    httpClient.sendPipeline.intercept(HttpSendPipeline.State){
//        if (authManager.token!!.isNotBlank()){
//            if (authManager.token != null){
//                context.headers["Authorization"] = authManager.token!!.toToken()
//            }else{
//                context.headers["Authorization"] = ""
//            }
//        }
        if (authManager.token != null){
            context.headers["Authorization"] = authManager.token!!.toToken()
        }else{
//            context.headers["Authorization"] = ""
        }
//        proceedWith(subject)
    }
    return ktorfit {
        baseUrl(baseUrl)
        httpClient(httpClient)
    }
}

private suspend fun tryCatch(body: suspend () -> Unit) {
    try {
        body()
    } catch (e: Exception) {
    }
}

@Serializable
private class ErrorMessage(val detail: String?)

@Serializable
private class ErrorDetailMessage(val detail: Message?)

@Serializable
private class Message(val message: String?)