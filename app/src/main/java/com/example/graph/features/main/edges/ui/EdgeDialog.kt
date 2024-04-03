package com.example.graph.features.main.edges.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.graph.features.main.edges.models.Node
import com.example.uikit.base.Button
import com.example.uikit.base.CustomTextField
import com.example.uikit.style.*
import com.example.uikit.utills.SpacerHeight
import com.example.uikit.utills.clickable

private const val BACKGROUND_DIALOG_ALPHA = 0.4f
private const val DIALOG_Z_INDEX = 1000f

@Composable
fun StyledKit.Dialog(
    onDismiss: () -> Unit,
    startNode: Node,
    endNode: Node,
    focusRequester: FocusRequester,
    onSaveClicked: (String) -> Unit
) {

    var value by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = BACKGROUND_DIALOG_ALPHA))
            .zIndex(DIALOG_Z_INDEX)
            .clickable {
                onDismiss()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .clickable { }
                .padding(16.dp)
                .background(Colors.defaultBg, shape = RoundedCornerShape(12.dp))
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Add edge", style = TextStyle.headline)
            SpacerHeight(height = 16.dp)
            Text(text = "From ${startNode.name} to ${endNode.name}", style = TextStyle.footnote)
            SpacerHeight(height = 8.dp)

            CustomTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = value,
                onValueChange = {
                    value = it.trim().filter { symbol ->
                        symbol.isDigit()
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Default
                ),
            )

            SpacerHeight(height = 24.dp)


            Row {
                StyledKit.Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(width = 80.dp, height = 48.dp),
                    text = "Cancel"
                ) {
                    onDismiss()
                }

                StyledKit.Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(width = 80.dp, height = 48.dp),
                    text = "Save"
                ) {
                    onSaveClicked(value)
                    onDismiss()
                }
            }

        }
    }
}
