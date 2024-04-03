package com.example.graph.features.graph.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.graph.features.graph.GraphScreen
import com.example.graph.features.main.edges.models.Node
import com.example.graph.features.main.edges.models.WeightedEdge
import com.example.navigation.NavigationDestination
import com.example.navigation.NavigationPayload
import com.example.navigation.NavigationRoute

object GraphRoute : NavigationRoute.ScreenRoute() {

    override fun endpoint(): String = "graph"

    @Composable
    override fun View(navController: NavController) {
        GraphScreen(navController)
    }

    sealed class Entry : NavigationDestination {
        data class OpenWithPayload(
            private val nodes: List<Node>,
            private val edges: List<WeightedEdge>,
            private val startNode: Node,
            private val endNode: Node,
        ) : Entry() {
            override fun getRoute(): NavigationRoute = GraphRoute
            override fun getPayload(): NavigationPayload =
                GraphPayload(nodes, edges, startNode, endNode)
        }
    }
}
