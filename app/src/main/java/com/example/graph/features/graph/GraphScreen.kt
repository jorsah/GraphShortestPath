package com.example.graph.features.graph

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.graph.features.graph.ui.WeightedGraphView
import com.example.uikit.base.*
import com.example.uikit.style.StyledKit
import com.example.uikit.utills.SpacerHeight

@Composable
fun GraphScreen(navController: NavController) {
    val viewModel = hiltViewModel<GraphViewModel>()

    BaseScreen(viewModel = viewModel, navController = navController) {
        Column {
            StyledKit.TopAppBar(
                title = "Graph",
                true,
                onBackButtonClick = {
                    viewModel.onUIEvent(BackUIEvent)
                }
            )

            SpacerHeight(16.dp)


            InternalGraphScreen(
                uiState = viewModel.uiState.value,
                onUIEvent = viewModel::onUIEvent
            )
        }
    }
}


@Composable
private fun InternalGraphScreen(
    uiState: GraphViewModel.UIState,
    onUIEvent: (BaseUIEvent) -> Unit = {}
) {
    WeightedGraphView(
        nodes = uiState.nodes,
        edges = uiState.edges,
        includedEdges = uiState.includedEdges
    )
}