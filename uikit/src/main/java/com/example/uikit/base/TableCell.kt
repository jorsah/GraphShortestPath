package com.example.uikit.base

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.uikit.style.StyledKit
import com.example.uikit.style.TextStyle
import com.example.uikit.utills.clickable

@Composable
fun StyledKit.TableCell(
    text: String,
    size: Dp,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .size(size)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = TextStyle.headline
        )
    }
}
