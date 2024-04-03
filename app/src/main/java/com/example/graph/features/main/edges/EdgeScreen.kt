package com.example.graph.features.main.edges

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.graph.features.main.edges.ui.Dialog
import com.example.graph.features.main.edges.ui.TableView
import com.example.uikit.base.*
import com.example.uikit.style.Colors
import com.example.uikit.style.StyledKit
import com.example.uikit.style.TextStyle
import com.example.uikit.utills.SpacerHeight

@Composable
fun EdgeScreen(navController: NavController) {
    val viewModel = hiltViewModel<EdgeViewModel>()

    BaseScreen(viewModel = viewModel, navController = navController) {
        Column {
            StyledKit.TopAppBar(
                title = "Edges",
                true,
                onBackButtonClick = {
                    viewModel.onUIEvent(BackUIEvent)
                }
            )

            SpacerHeight(16.dp)

            InternalEdgeScreen(
                uiState = viewModel.uiState.value,
                onUIEvent = viewModel::onUIEvent
            )
        }
    }
}

@Composable
private fun InternalEdgeScreen(
    uiState: EdgeViewModel.UIState,
    onUIEvent: (BaseUIEvent) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(uiState.showDialog) {

        if (uiState.showDialog) focusRequester.requestFocus()
        else focusManager.clearFocus()
    }

    uiState.selectedEdge?.let { selected ->
        if (uiState.showDialog) {
            StyledKit.Dialog(
                onDismiss = {
                    onUIEvent(EdgeViewModel.UIEvent.OnDialogDismiss)
                },
                startNode = selected.start,
                endNode = selected.end,
                focusRequester = focusRequester,
                onSaveClicked = {
                    onUIEvent(EdgeViewModel.UIEvent.OnAddWeightClick(it))
                }
            )
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        Footer(
            color = Colors.errorText,
            uiState.isError,
            text = "Graph is not connected"
        )
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            StyledKit.TableView(
                nodes = uiState.nodes,
                edges = uiState.edges
            ) {
                onUIEvent(EdgeViewModel.UIEvent.OnBoxClicked(it))
            }
        }

        StyledKit.Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .size(width = 240.dp, height = 48.dp),
            text = "Proceed"
        ) {
            onUIEvent(EdgeViewModel.UIEvent.OnProceedClick)
        }
    }
}