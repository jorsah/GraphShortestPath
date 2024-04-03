package com.example.graph.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.graph.features.graph.navigation.GraphRoute
import com.example.graph.features.main.configuration.navigation.ConfigurationRoute
import com.example.graph.features.main.edges.navigation.EdgeRoute
import com.example.graph.features.main.nodes.navigation.NodeRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RootNavGraph(navController: NavHostController) {
    Scaffold {
        NavHost(
            navController = navController,
            startDestination = NodeRoute.endpoint()
        ) {
            listOf(
                NodeRoute,
                EdgeRoute,
                GraphRoute,
                ConfigurationRoute
            ).forEach { route ->
                composable(
                    route = route.endpoint(),
                    deepLinks = route.deepLinks()
                ) {
                    route.View(navController = navController)
                }
            }
        }
    }
}

