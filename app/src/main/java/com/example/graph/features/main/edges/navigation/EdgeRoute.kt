package com.example.graph.features.main.edges.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.graph.features.main.edges.EdgeScreen
import com.example.navigation.NavigationDestination
import com.example.navigation.NavigationPayload
import com.example.navigation.NavigationRoute

object EdgeRoute : NavigationRoute.ScreenRoute() {

    override fun endpoint(): String = "edge"

    @Composable
    override fun View(navController: NavController) {
        EdgeScreen(navController)
    }

    sealed class Entry : NavigationDestination {
        data class OpenWithPayload(
            val nodeCount: Int
        ) : Entry() {
            override fun getRoute(): NavigationRoute = EdgeRoute
            override fun getPayload(): NavigationPayload = EdgePayload(nodeCount)
        }
    }
}
