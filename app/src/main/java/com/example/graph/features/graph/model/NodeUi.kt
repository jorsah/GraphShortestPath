package com.example.graph.features.graph.model

import androidx.compose.animation.VectorConverter
import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import com.example.graph.features.main.edges.models.Node

data class NodeUi(
    val name: String,
    val color: Animatable<Color, AnimationVector4D> = Animatable(
        Color.Gray,
        typeConverter = Color.VectorConverter(ColorSpaces.LinearSrgb)
    )
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NodeUi) return false
        return name == other.name
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + color.hashCode()
        return result
    }
}

fun Node.toUi() = NodeUi(name = name)
