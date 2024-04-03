package com.example.graph.features.main.edges.utills

fun String.edgeWeightOrNull():Int?{
    return toIntOrNull()?.apply {
        if (this == 0) return null
    }
}
