package com.example.graph.features.graph.model

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import com.example.graph.features.main.edges.models.Node
import com.example.graph.features.main.edges.models.WeightedEdge

data class WeightedEdgeUi(
    val start: Node,
    val end: Node,
    val weight: Int? = null,
    val animVal: Animatable<Float, AnimationVector1D> = Animatable(0f),
) {
    constructor(
        start: String,
        end: String,
        weight: Int? = null
    ) : this(Node(start), Node(end), weight)
}

fun WeightedEdge.toUi() = WeightedEdgeUi(start, end, weight)
