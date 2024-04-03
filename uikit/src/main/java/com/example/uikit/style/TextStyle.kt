package com.example.uikit.style

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object TextStyle {

    val footnote = TextStyle(
        lineHeight = 16.sp,
        fontWeight = FontWeight(600),
        color = Colors.textDefault,
    )

    val headline = TextStyle(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight(700),
    )

    val default = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight(500),
    )

    val defaultBold = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight(600),
    )

    val defaultSmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight(700),
        color = Colors.textSecondary,
    )

    val button = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight(500),
        letterSpacing = 0.1.sp,
        color = Colors.onPrimaryMain
    )
}
