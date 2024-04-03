package com.example.uikit.base

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.uikit.style.*
import com.example.uikit.utills.Drawables
import com.example.uikit.utills.clickable

@Composable
fun StyledKit.RadioItem(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val (iconRes, iconColor) = if (isSelected)
        Drawables.ic_selected_radio to Colors.primaryMain
    else Drawables.ic_not_selected_radio to Colors.secondaryMain

    Row(
        modifier = modifier
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.weight(1f), text = title, style = TextStyle.default)

        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = "Radio button icon",
            tint = iconColor
        )
    }
}
