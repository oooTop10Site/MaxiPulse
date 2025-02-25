package org.example.project.platform

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitInternal
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.SimpleContext
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax

internal abstract class BaseScreenModel<State : Any, SideEffect : Any>(private val initState: State) :
    ScreenModel,
    ContainerHost<State, SideEffect>, KoinComponent {
    private val defaultModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    final override val container: Container<State, SideEffect> =
        screenModelScope.container(initState) {

        }

    @OptIn(OrbitInternal::class)
    protected fun <S : Any, SE : Any> SimpleSyntax<S, SE>.reduceLocal(reducer: SimpleContext<S>.() -> S) {
        screenModelScope.launch {
            containerContext.reduce { reducerState ->
                SimpleContext(reducerState).reducer()
            }
        }
    }

    @OptIn(OrbitInternal::class)
    protected fun <S : Any, SE : Any> SimpleSyntax<S, SE>.postSideEffectLocal(sideEffect: SE) {
        screenModelScope.launch {
            containerContext.postSideEffect(sideEffect)
        }
    }


    protected val state: State
        get() = container.stateFlow.value

    val stateFlow = container.stateFlow

    /**
     * Выполнить запросы
     * @param operation - описываем запрос
     * @param loading - статус загрузки(можем переопределить)
     * @param failure - подписка на ошибку(можем переопределить)
     * @param success - результат
     */
    protected fun <T> launchOperation(
        operation: suspend (CoroutineScope) -> Either<Failure, T>,
        saveAsLastCall: Boolean = true,
        loading: (Boolean) -> Unit = { },
        failure: (Failure) -> Unit = { handleError(it) },
        success: (T) -> Unit = {},
    ): Job {
        return screenModelScope.launch(handler) {
            loading.invoke(true)
            withContext(defaultModelScope.coroutineContext) {
                operation(this)
            }.fold({
                failure(it)
//                if (!konnectivity.isConnected && saveAsLastCall) {
//                    println("lastApi init")
//                    observerManager.setLastApiCall {
//                        screenModelScope.launch {
//                            println("RELAUNCHING OPERATION")
//                            launchOperation(operation, saveAsLastCall, loading, failure) {
//                                observerManager.setLastApiCall { null }
//                                success(it)
//                            }
//                        }
//                    }
//                }
            }, success)
            loading.invoke(false)
        }
    }

    protected val handler = CoroutineExceptionHandler { _, exception ->
        println(exception)
        if (exception.message?.contains(
                "HttpClientCall",
                true
            ) == true
        ) return@CoroutineExceptionHandler
        handleError(exception)
    }

    protected fun handleError(failure: Throwable) {
    }


    /**
     * Ошибки наследованные от Failure
     */
    private val _error = Channel<Failure?>(Channel.BUFFERED)
    val error = _error.receiveAsFlow().filterNotNull()


    /**
     * Статус загрузки(launchOperation переключает)
     */
    private val _status = MutableStateFlow(true)
    val status = _status


    protected fun setStatus(status: Boolean) {
        screenModelScope.launch {
            _status.emit(status && state == initState)
        }
    }

    /**
     * Показать ошибку
     */
    protected fun setError(error: String) {
        screenModelScope.launch {
            _status.emit(false)
            _error.send(Failure.Message(error))
            delay(100)
            _error.send(null)
        }
    }

    private fun setError(failure: Failure) {
        screenModelScope.launch {
            _status.emit(false)
            _error.send(failure)
            delay(100)
            _error.send(null)
        }
    }
}