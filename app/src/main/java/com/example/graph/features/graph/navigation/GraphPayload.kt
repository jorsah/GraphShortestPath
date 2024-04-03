package com.example.graph.features.graph.navigation

import com.example.graph.features.main.edges.models.Node
import com.example.graph.features.main.edges.models.WeightedEdge
import com.example.navigation.NavigationPayload
import kotlinx.parcelize.Parcelize

@Parcelize
data class GraphPayload(
    val nodes: List<Node>,
    val edges: List<WeightedEdge>,
    val startNode:Node,
    val endNode:Node,
    override val payloadKey: String = PAYLOAD_KEY
) : NavigationPayload {

    companion object {
        const val PAYLOAD_KEY = "GRAPH_PAYLOAD_KEY"
    }
}
