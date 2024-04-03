package com.example.graph.features.main.configuration.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.graph.features.main.configuration.ConfigurationScreen
import com.example.graph.features.main.edges.models.Node
import com.example.graph.features.main.edges.models.WeightedEdge
import com.example.navigation.NavigationDestination
import com.example.navigation.NavigationPayload
import com.example.navigation.NavigationRoute

object ConfigurationRoute : NavigationRoute.ScreenRoute() {

    override fun endpoint(): String = "configuration"

    @Composable
    override fun View(navController: NavController) {
        ConfigurationScreen(navController)
    }

    sealed class Entry : NavigationDestination {
        data class OpenWithPayload(
            private val nodes: List<Node>,
            private val edges: List<WeightedEdge>,
        ) : Entry() {
            override fun getRoute(): NavigationRoute = ConfigurationRoute
            override fun getPayload(): NavigationPayload = ConfigurationPayload(nodes, edges)
        }
    }
}
