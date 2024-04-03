package com.example.uikit.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.uikit.style.*
import com.example.uikit.style.Colors
import com.example.uikit.utills.clickable


@Composable
fun StyledKit.IconButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier
            .background(
                color = Colors.defaultBg, RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = Colors.secondaryMain,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(vertical = 4.dp)
            .then(modifier),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Colors.defaultBg
        ),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "Button Icon",
            tint = Colors.primaryMain
        )
    }
}

@Composable
fun StyledKit.Button(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Colors.buttonBgPrimary
        ),
        modifier = Modifier
            .padding(8.dp)
            .padding(horizontal = 24.dp)
            .then(modifier),
        onClick = onClick,
    ) {
        Text(text = text, style = TextStyle.button)
    }
}
