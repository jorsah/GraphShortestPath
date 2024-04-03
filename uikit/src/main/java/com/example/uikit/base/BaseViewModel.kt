package com.example.uikit.base

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.navigation.NavigationPayload
import com.example.navigation.NavigationProvider
import com.example.uikit.utills.launchWithCatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

abstract class BaseViewModel<UIState : BaseUIState>(
    navigationProvider: NavigationProvider,
) : ViewModel(), NavigationProvider by navigationProvider {
    abstract val savedStateHandle: SavedStateHandle
    lateinit var currentSavedStateHandle: SavedStateHandle

    @Suppress("LeakingThis")
    private val _uiState = mutableStateOf(getStartUIState())
    val uiState: State<UIState>
        get() = _uiState

    abstract fun getStartUIState(): UIState

    protected fun <Payload : NavigationPayload> getPayload(
        key: String,
        notFound: (() -> Unit) = { },
        removePayload: Boolean = true,
        doWithPayload: (Payload) -> Unit
    ) {
        /**
         * "currentSavedStateHandle" is initialized only if
         * it was goBackResult navigation & it's not empty.
         * For all other cases used "savedStateHandle"
         */
        try {
            if (::currentSavedStateHandle.isInitialized) {
                doWithPayload(currentSavedStateHandle.get<Payload>(key)!!)
                if (removePayload) currentSavedStateHandle.remove<Payload>(key)
            } else {
                doWithPayload(savedStateHandle.get<Payload>(key)!!)
                if (removePayload) savedStateHandle.remove<Payload>(key)
            }
        } catch (th: Throwable) {
            notFound()
        }
    }

    open fun onUIEvent(uiEvent: BaseUIEvent) {
        when (uiEvent) {
            is HandledBackUIEvent -> reduce(uiEvent)
            is BackUIEvent -> reduce(uiEvent)
            is HandledDestinationUIEvent -> reduce(uiEvent)
            is LifecycleUIEvent.OnResume -> reduce(uiEvent)
            is LifecycleUIEvent.OnPause -> reduce(uiEvent)
        }
    }

    protected fun onUIState(newUIState: UIState) {
        _uiState.value = newUIState
    }

    protected fun launchWithCatch(
        catch: suspend (Throwable) -> Unit,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launchWithCatch(
            block = block,
            catch = {
                catch(it)
            }
        )
    }

    private fun reduce(event: LifecycleUIEvent.OnResume) {
        lockNavigation(false)
    }

    private fun reduce(event: LifecycleUIEvent.OnPause) {
        lockNavigation(true)
    }

    private fun reduce(event: HandledBackUIEvent) {
        clearGoBack()
    }

    private fun reduce(event: BackUIEvent) {
        goBack()
    }

    private fun reduce(event: HandledDestinationUIEvent) {
        clearDestination()
    }
}

interface BaseUIState

interface BaseUIEvent
object HandledBackUIEvent : BaseUIEvent
object BackUIEvent : BaseUIEvent
object HandledDestinationUIEvent : BaseUIEvent

sealed class LifecycleUIEvent : BaseUIEvent {
    object OnStart : LifecycleUIEvent()
    object OnResume : LifecycleUIEvent()
    object OnPause : LifecycleUIEvent()
    object OnStop : LifecycleUIEvent()
}
