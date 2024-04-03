package com.example.graph

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.graph.features.main.edges.ui.Dialog
import com.example.graph.features.main.edges.ui.TableView
import com.example.graph.features.main.edges.utills.edgeWeightOrNull
import com.example.graph.navigation.RootNavGraph
import com.example.uikit.style.StyledKit
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            GraphShortestPathTheme {
//                val nodes = listOf(
//                    Node("A"), Node("B"), Node("C"), Node("D"), Node("E")
//                )
//
//                val edges = listOf(
//                    WeightedEdge1("A", "B", 1),
//                    WeightedEdge1("A", "C", 5),
//                    WeightedEdge1("B", "C", 2),
//                    WeightedEdge1("B", "D", 7),
//                    WeightedEdge1("B", "E", 10),
//                    WeightedEdge1("C", "D", 5),
//                    WeightedEdge1("D", "E", 7),
//                )
//
//                val includedEdges = listOf(
//                    WeightedEdge1("A", "C", 5),
//                    WeightedEdge1("C", "D", 5),
//                    WeightedEdge1("D", "B", 7),
//                    WeightedEdge1("B", "E", 10),
//                )
//
//
//                WeightedGraphView(nodes, edges, includedEdges)
//            }
            val navController = rememberNavController()
            RootNavGraph(navController)

        }
    }
}
