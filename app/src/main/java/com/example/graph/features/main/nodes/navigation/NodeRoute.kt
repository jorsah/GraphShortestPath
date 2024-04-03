package com.example.graph.features.main.nodes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.graph.features.main.nodes.NodeScreen
import com.example.navigation.NavigationDestination
import com.example.navigation.NavigationRoute

object NodeRoute : NavigationRoute.ScreenRoute() {

    override fun endpoint(): String = "node"

    @Composable
    override fun View(navController: NavController) {
        NodeScreen(navController)
    }

    sealed class Entry : NavigationDestination {
        data object Single : Entry() {
            override fun getRoute(): NavigationRoute = NodeRoute
        }
    }
}
