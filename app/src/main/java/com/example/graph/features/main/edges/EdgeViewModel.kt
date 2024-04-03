package com.example.graph.features.main.edges

import androidx.lifecycle.SavedStateHandle
import com.example.graph.features.graph.navigation.GraphRoute
import com.example.graph.features.main.configuration.navigation.ConfigurationRoute
import com.example.graph.features.main.edges.models.Node
import com.example.graph.features.main.edges.models.WeightedEdge
import com.example.graph.features.main.edges.navigation.EdgePayload
import com.example.graph.features.main.edges.utills.CheckConnectivity
import com.example.graph.features.main.edges.utills.edgeWeightOrNull
import com.example.navigation.NavigationProvider
import com.example.uikit.base.BaseUIEvent
import com.example.uikit.base.BaseUIState
import com.example.uikit.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EdgeViewModel @Inject constructor(
    navigationProvider: NavigationProvider, override val savedStateHandle: SavedStateHandle
) : BaseViewModel<EdgeViewModel.UIState>(navigationProvider) {

    private var nodes: List<Node> = emptyList()

    init {

        getPayload<EdgePayload>(EdgePayload.PAYLOAD_KEY) { payload ->
            nodes = ('A'..'Z').take(payload.nodeCount).map { Node(it.toString()) }

            onUIState(
                uiState.value.copy(
                    nodes = nodes
                )
            )
        }
    }

    override fun onUIEvent(uiEvent: BaseUIEvent) {
        when (uiEvent) {
            is UIEvent.OnProceedClick -> reduce(uiEvent)
            is UIEvent.OnBoxClicked -> reduce(uiEvent)
            is UIEvent.OnDialogDismiss -> reduce(uiEvent)
            is UIEvent.OnAddWeightClick -> reduce(uiEvent)
            else -> super.onUIEvent(uiEvent)
        }
    }

    private fun reduce(uiEvent: UIEvent.OnAddWeightClick) {
        with(uiState.value) {
            if (selectedEdge != null) {
                onUIState(copy(edges = edges.apply {
                    removeIf { edge ->
                        edge.sameNodes(selectedEdge)
                    }

                    if (selectedEdge.start.name < selectedEdge.end.name) {
                        add(
                            WeightedEdge(
                                selectedEdge.start,
                                selectedEdge.end,
                                uiEvent.weight.edgeWeightOrNull()
                            )
                        )
                    } else {
                        add(
                            WeightedEdge(
                                selectedEdge.end,
                                selectedEdge.start,
                                uiEvent.weight.edgeWeightOrNull()
                            )
                        )
                    }
                }))
            }

        }
    }

    private fun reduce(uiEvent: UIEvent.OnDialogDismiss) {
        onUIState(
            uiState.value.copy(
                showDialog = false
            )
        )
    }

    private fun reduce(uiEvent: UIEvent.OnBoxClicked) {
        if (uiEvent.edge.start == uiEvent.edge.end) return
        onUIState(
            uiState.value.copy(
                isError = false, showDialog = true, selectedEdge = uiEvent.edge
            )
        )
    }

    private fun reduce(uiEvent: UIEvent.OnProceedClick) {
        if (uiState.value.isError) return //todo

        val graph = CheckConnectivity.Graph(nodes.size)
        val finalEdges = uiState.value.edges.filter {
            it.weight != null
        }
        graph.addEdges(finalEdges)

        if (CheckConnectivity().isConnected(graph)) {
            openDestination(
                ConfigurationRoute.Entry.OpenWithPayload(
                    uiState.value.nodes, finalEdges
                )
            )
        } else {
            onUIState(
                uiState.value.copy(
                    isError = true
                )
            )
        }
    }

    data class UIState(
        val nodes: List<Node> = emptyList(),
        val edges: MutableList<WeightedEdge> = mutableListOf(),
        val selectedEdge: WeightedEdge? = null,
        val showDialog: Boolean = false,
        val isError: Boolean = false,
        val isLoading: Boolean = true
    ) : BaseUIState

    sealed class UIEvent : BaseUIEvent {
        data object OnProceedClick : UIEvent()
        data object OnDialogDismiss : UIEvent()
        data class OnBoxClicked(val edge: WeightedEdge) : UIEvent()
        data class OnAddWeightClick(val weight: String) : UIEvent()
    }

    override fun getStartUIState(): UIState = UIState()
}