package com.example.graph.features.graph

import androidx.lifecycle.SavedStateHandle
import com.example.graph.features.graph.model.*
import com.example.graph.features.graph.navigation.GraphPayload
import com.example.graph.features.main.edges.models.WeightedEdge
import com.example.navigation.NavigationProvider
import com.example.uikit.base.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    navigationProvider: NavigationProvider,
    override val savedStateHandle: SavedStateHandle
) : BaseViewModel<GraphViewModel.UIState>(navigationProvider) {

    init {
        getPayload<GraphPayload>(GraphPayload.PAYLOAD_KEY) { payload ->
            val edges = payload.edges

            val includedNodes = findShortestPath(
                edges,
                payload.startNode,
                payload.endNode
            ).shortestPath().zipWithNext().map {
                WeightedEdge(it.first, it.second)
            }

            val includedEdges = edges.filter { withWeight ->
                includedNodes.any {
                    it.sameNodes(withWeight)
                }
            }.map { it.toUi() }

            onUIState(
                uiState.value.copy(
                    nodes = payload.nodes.map { it.toUi() },
                    edges = edges.map { it.toUi() },
                    includedEdges = includedEdges
                )
            )
        }
    }


    data class UIState(
        val nodes: List<NodeUi> = emptyList(),
        val edges: List<WeightedEdgeUi> = mutableListOf(),
        val includedEdges: List<WeightedEdgeUi> = mutableListOf(),
    ) : BaseUIState

    override fun getStartUIState(): UIState = UIState()
}