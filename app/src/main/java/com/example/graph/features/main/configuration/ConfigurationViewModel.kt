package com.example.graph.features.main.configuration

import androidx.lifecycle.SavedStateHandle
import com.example.graph.features.graph.navigation.GraphRoute
import com.example.graph.features.main.configuration.navigation.ConfigurationPayload
import com.example.graph.features.main.edges.models.Node
import com.example.graph.features.main.edges.models.WeightedEdge
import com.example.navigation.NavigationProvider
import com.example.uikit.base.BaseUIEvent
import com.example.uikit.base.BaseUIState
import com.example.uikit.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfigurationViewModel @Inject constructor(
    navigationProvider: NavigationProvider,
    override val savedStateHandle: SavedStateHandle
) : BaseViewModel<ConfigurationViewModel.UIState>(navigationProvider) {

    private var nodes: List<Node> = emptyList()
    private var edges: List<WeightedEdge> = emptyList()

    init {
        getPayload<ConfigurationPayload>(ConfigurationPayload.PAYLOAD_KEY) { payload ->
            nodes = payload.nodes
            edges = payload.edges
            onUIState(
                uiState.value.copy(
                    startNode = nodes.first(),
                    endNode = nodes.last(),
                    startNodes = nodes.minus(nodes.last()),
                    endNodes = nodes.minus(nodes.first()),
                )
            )
        }
    }

    override fun onUIEvent(uiEvent: BaseUIEvent) {
        when (uiEvent) {
            is UIEvent.OnProceedClick -> reduce(uiEvent)
            is UIEvent.OnAlgorithmClicked -> reduce(uiEvent)
            is UIEvent.OnStartChanged -> reduce(uiEvent)
            is UIEvent.OnEndChanged -> reduce(uiEvent)
            else -> super.onUIEvent(uiEvent)
        }
    }

    private fun reduce(uiEvent: UIEvent.OnStartChanged) {
        onUIState(
            uiState.value.copy(
                startNode = uiEvent.node,
                endNodes = nodes.minus(uiEvent.node)
            )
        )
    }

    private fun reduce(uiEvent: UIEvent.OnEndChanged) {
        onUIState(
            uiState.value.copy(
                endNode = uiEvent.node,
                startNodes = nodes.minus(uiEvent.node)
            )
        )
    }


    private fun reduce(uiEvent: UIEvent.OnAlgorithmClicked) {
        onUIState(
            uiState.value.copy(
                isDijkstra = uiEvent.isDijkstra
            )
        )
    }

    private fun reduce(uiEvent: UIEvent.OnProceedClick) {
        openDestination(
            GraphRoute.Entry.OpenWithPayload(
                nodes, edges, uiState.value.startNode, uiState.value.endNode
            )
        )
    }

    data class UIState(
        val isError: Boolean = false,
        val isDijkstra: Boolean = true,
        val startNodes: List<Node> = emptyList(),
        val startNode: Node = Node("A"),
        val endNode: Node = Node("B"),
        val endNodes: List<Node> = emptyList()
    ) : BaseUIState

    sealed class UIEvent : BaseUIEvent {
        data object OnProceedClick : UIEvent()
        data class OnAlgorithmClicked(val isDijkstra: Boolean) : UIEvent()
        data class OnStartChanged(val node: Node) : UIEvent()
        data class OnEndChanged(val node: Node) : UIEvent()
    }

    override fun getStartUIState(): UIState = UIState()
}