package com.example.uikit.utills

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpacerHeight(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun SpacerWidth(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun RowScope.SpacerFill(minWidth: Dp = 0.dp) {
    Spacer(modifier = Modifier.defaultMinSize(minWidth = minWidth).weight(1f))
}

@Composable
fun ColumnScope.SpacerFill(minHeight: Dp = 0.dp) {
    Spacer(modifier = Modifier.defaultMinSize(minHeight = minHeight).weight(1f))
}
