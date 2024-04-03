package com.example.uikit.base

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.*
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uikit.style.Colors
import kotlinx.coroutines.delay
import java.util.*

private const val DEFAULT_ANIMATION_DELAY = 200

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape =
        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    contentPadding: PaddingValues = if (label == null) {
        TextFieldDefaults.textFieldWithoutLabelPadding()
    } else {
        TextFieldDefaults.textFieldWithLabelPadding()
    },
    onClick: () -> Unit = {}
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
    val isPressed = interactionSource.collectIsPressedAsState()

    if (isPressed.value) {
        onClick()
    }

    @OptIn(ExperimentalMaterialApi::class)
    BasicTextField(
        value = value,
        modifier = modifier
            .background(Colors.cardBg, shape)
            .indicatorLine(enabled, isError, interactionSource, colors),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            // places leading icon, text field with label and placeholder, trailing icon
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = contentPadding
            )
        }
    )
}

@Composable
fun Footer(
    color: Color,
    isVisible: Boolean,
    text: String?,
) {
    val textValue = remember {
        mutableStateOf<String?>(null)
    }

    val shouldShowValue = remember {
        mutableStateOf(isVisible)
    }

    LaunchedEffect(isVisible, text) {
        shouldShowValue.value = isVisible
        if (!isVisible) {
            delay(DEFAULT_ANIMATION_DELAY.toLong())
        }
        textValue.value = text
    }

    AnimatedVisibility(
        modifier = Modifier.fillMaxWidth(),
        visible = shouldShowValue.value,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {

        if (textValue.value != null) {
            val labelColor = animateColorAsState(
                targetValue = color, label = "label color"
            )

            Text(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = textValue.value.orEmpty(),
                style = com.example.uikit.style.TextStyle.footnote,
                color = labelColor.value
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun InputPreview1() {
    CustomTextField(value = "gagas", onValueChange = {})
}
