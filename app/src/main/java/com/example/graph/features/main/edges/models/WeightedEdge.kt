package com.example.graph.features.main.edges.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeightedEdge(
    val start: Node,
    val end: Node,
    val weight: Int? = null,
) : Parcelable {

    fun sameNodes(other: WeightedEdge): Boolean =
        (start == other.start && end == other.end) ||
                (start == other.end && end == other.start)
}


