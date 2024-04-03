package com.example.graph.features.main.configuration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.graph.features.main.edges.models.Node
import com.example.uikit.base.BackUIEvent
import com.example.uikit.base.BaseScreen
import com.example.uikit.base.BaseUIEvent
import com.example.uikit.base.Button
import com.example.uikit.base.RadioButton
import com.example.uikit.base.TopAppBar
import com.example.uikit.style.Colors
import com.example.uikit.style.StyledKit
import com.example.uikit.style.TextStyle
import com.example.uikit.utills.SpacerHeight


@Composable
fun ConfigurationScreen(navController: NavController) {
    val viewModel = hiltViewModel<ConfigurationViewModel>()

    BaseScreen(viewModel = viewModel, navController = navController) {
        Column {
            StyledKit.TopAppBar(
                title = "Configuration",
                true,
                onBackButtonClick = {
                    viewModel.onUIEvent(BackUIEvent)
                }
            )

            SpacerHeight(16.dp)

            CompositionLocalProvider(LocalTextInputService provides null) {

                InternalConfigScreen(
                    viewModel.uiState.value,
                    viewModel::onUIEvent
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InternalConfigScreen(
    uiState: ConfigurationViewModel.UIState,
    onUIEvent: (BaseUIEvent) -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
        ) {
            SpacerHeight(height = 24.dp)
            Text(
                text = "Algorithm",
                style = TextStyle.headline
            )

            SpacerHeight(height = 16.dp)


            StyledKit.RadioButton(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = "Dijkstra",
                isSelect = uiState.isDijkstra
            ) {
                onUIEvent(
                    ConfigurationViewModel.UIEvent.OnAlgorithmClicked(true)
                )
            }

            SpacerHeight(height = 8.dp)

            StyledKit.RadioButton(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = "Floyd",
                isSelect = !uiState.isDijkstra
            ) {
                onUIEvent(
                    ConfigurationViewModel.UIEvent.OnAlgorithmClicked(false)
                )
            }

            SpacerHeight(height = 24.dp)
            NodeDropDown("Start", uiState.startNode, uiState.startNodes) {
                onUIEvent(ConfigurationViewModel.UIEvent.OnStartChanged(it))
            }

            SpacerHeight(height = 8.dp)
            NodeDropDown("End", uiState.endNode, uiState.endNodes) {
                onUIEvent(ConfigurationViewModel.UIEvent.OnEndChanged(it))
            }

            SpacerHeight(height = 64.dp)
        }

        StyledKit.Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .size(width = 240.dp, height = 48.dp),
            text = "Proceed"
        ) {
            onUIEvent(ConfigurationViewModel.UIEvent.OnProceedClick)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NodeDropDown(
    title: String,
    node: Node,
    nodeList: List<Node>,
    onSelected: (Node) -> Unit
) {

    Text(
        text = title,
        style = TextStyle.headline
    )
    var isExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {
                isExpanded = !isExpanded
            }
        ) {
            TextField(
                value = node.name,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                modifier = Modifier.menuAnchor(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Colors.cardBg
                )

            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                nodeList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.name) },
                        onClick = {
                            onSelected(item)
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}
