package com.example.uikit.utills

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.clickable(hasRipple: Boolean = false, onClick: () -> Unit): Modifier = composed {
    if (hasRipple) clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple()
    ) {
        onClick()
    } else clickable(
        interactionSource = MutableInteractionSource(),
        indication = null
    ) {
        onClick()
    }
}
