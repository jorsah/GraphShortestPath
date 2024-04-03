package com.example.uikit.base

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.uikit.style.Colors
import com.example.uikit.style.StyledKit

@Composable
fun StyledKit.RadioButton(
    modifier: Modifier = Modifier,
    title: String,
    isSelect: Boolean,
    styledKit: StyledRadioButton.RadioButton = StyledRadioButton.RadioButton.Default,
    textRtlIgnore: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable(
            interactionSource = MutableInteractionSource(),
            indication = null
        ) {
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            if (textRtlIgnore) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Text(
                        text = title,
                        style = styledKit.getStyle().textStyle,
                        color = styledKit.getStyle().textColor,
                        textAlign = TextAlign.Start
                    )
                }
            } else {
                Text(
                    text = title,
                    style = styledKit.getStyle().textStyle,
                    color = styledKit.getStyle().textColor,
                    textAlign = TextAlign.Start
                )
            }
        }

        StyledKit.RadioButtonIndicator(
            modifier = Modifier.padding(start = 16.dp),
            styledKit = styledKit,
            isSelect = isSelect
        )
    }
}

@Composable
fun StyledKit.RadioButtonIndicator(
    modifier: Modifier = Modifier,
    styledKit: StyledRadioButton.RadioButton = StyledRadioButton.RadioButton.Default,
    isSelect: Boolean
) {
    val animateBorderWidth =
        animateDpAsState(
            targetValue = if (isSelect)
                styledKit.getStyle().checkedWidth else styledKit.getStyle().uncheckedWidth
        )

    val animateBorderColor =
        animateColorAsState(
            targetValue = if (isSelect)
                styledKit.getStyle().checkedColor else styledKit.getStyle().uncheckedColor
        )

    Box(
        modifier = Modifier
            .size(styledKit.getStyle().radioSize)
            .border(
                width = animateBorderWidth.value,
                color = animateBorderColor.value,
                shape = RoundedCornerShape(styledKit.getStyle().radioSize / 2)
            )
    )
}

sealed interface StyledRadioButton<T : StyledRadioButton.Style> {

    interface Style

    @Composable
    fun getStyle(): T = getStyle(true)

    @Composable
    fun getStyle(enabled: Boolean): T = error("getStyle Should be overriden")

    interface RadioButton : StyledRadioButton<RadioButton.RadioButtonStyle> {
        data class RadioButtonStyle(
            val checkedColor: Color,
            val uncheckedColor: Color,
            val textColor: Color,
            val checkedWidth: Dp = 6.dp,
            val uncheckedWidth: Dp = 2.dp,
            val radioSize: Dp = 20.dp,
            val textStyle: TextStyle = com.example.uikit.style.TextStyle.footnote
        ) : Style

        object Default : RadioButton {
            @Composable
            override fun getStyle() = RadioButtonStyle(
                checkedColor = Colors.controlRadioOn,
                uncheckedColor = Colors.controlRadioOff,
                textColor = Colors.textDefault
            )
        }
    }
}
