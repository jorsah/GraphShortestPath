package com.example.graph.features.main.nodes

import androidx.lifecycle.SavedStateHandle
import com.example.graph.features.main.edges.navigation.EdgeRoute
import com.example.navigation.NavigationProvider
import com.example.uikit.base.BaseUIEvent
import com.example.uikit.base.BaseUIState
import com.example.uikit.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NodeViewModel @Inject constructor(
    navigationProvider: NavigationProvider,
    override val savedStateHandle: SavedStateHandle
) : BaseViewModel<NodeViewModel.UIState>(navigationProvider) {
    override fun onUIEvent(uiEvent: BaseUIEvent) {
        when (uiEvent) {
            is UIEvent.OnCountChanged -> reduce(uiEvent)
            is UIEvent.OnProceedClick -> reduce(uiEvent)
            else -> super.onUIEvent(uiEvent)
        }
    }

    private fun reduce(uiEvent: UIEvent.OnProceedClick) {
        if (uiState.value.isError) return
        openDestination(EdgeRoute.Entry.OpenWithPayload(uiState.value.count.toInt()))
    }

    private fun reduce(uiEvent: UIEvent.OnCountChanged) {
        val count = uiEvent.count.toIntOrNull()

        onUIState(
            uiState.value.copy(
                isError = count !in 2..13,
                count = uiEvent.count
            )
        )
    }

    data class UIState(
        val count: String = "4",
        val isError: Boolean = false,
        val isLoading: Boolean = true,
    ) : BaseUIState

    sealed class UIEvent : BaseUIEvent {
        data class OnCountChanged(val count: String) : UIEvent()
        data object OnProceedClick : UIEvent()
    }

    override fun getStartUIState(): UIState = UIState()
}