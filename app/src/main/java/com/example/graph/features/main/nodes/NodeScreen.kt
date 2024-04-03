package com.example.graph.features.main.nodes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.uikit.base.*
import com.example.uikit.style.*

@Composable
fun NodeScreen(navController: NavController) {
    val viewModel = hiltViewModel<NodeViewModel>()

    BaseScreen(viewModel = viewModel, navController = navController) {
        InternalNodeScreen(
            uiState = viewModel.uiState.value,
            onUIEvent = viewModel::onUIEvent
        )
    }
}


@Composable
private fun InternalNodeScreen(
    uiState: NodeViewModel.UIState,
    onUIEvent: (BaseUIEvent) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nodes Count",
                style = TextStyle.headline
            )

            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                value = uiState.count,
                onValueChange = {
                    onUIEvent(
                        NodeViewModel.UIEvent.OnCountChanged(
                            it.trim().filter { symbol ->
                                symbol.isDigit()
                            })
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onUIEvent(NodeViewModel.UIEvent.OnProceedClick)
                    }
                )
            )

            Footer(
                color = Colors.errorText,
                isVisible = uiState.isError,
                text = "Enter value from 2 to 13"
            )
        }

        StyledKit.Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .size(width = 240.dp, height = 48.dp),
            text = "Proceed"
        ) {
            onUIEvent(NodeViewModel.UIEvent.OnProceedClick)
        }
    }
}