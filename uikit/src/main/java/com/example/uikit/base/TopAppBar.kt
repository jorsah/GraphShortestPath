package com.example.uikit.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.uikit.R
import com.example.uikit.style.*
import com.example.uikit.style.Colors
import com.example.uikit.utills.*

@Composable
fun StyledKit.TopAppBar(
    title: String,
    hasBackButton: Boolean = false,
    onBackButtonClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = if (hasBackButton) 0.dp else 16.dp)
                .size(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (hasBackButton) {
                Icon(
                    modifier = Modifier.clickable { onBackButtonClick() },
                    painter = painterResource(id = Drawables.ic_back),
                    tint = Colors.textDefault,
                    contentDescription = "Back Icon"
                )
            }

            Text(text = title, style = TextStyle.headline, color = Colors.textDefault)
        }


        Row {
            SpacerWidth(width = 16.dp)

            content()
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            color = Colors.outlineMain,
            thickness = 1.dp
        )
    }
}
