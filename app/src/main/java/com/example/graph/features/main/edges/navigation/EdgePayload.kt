package com.example.graph.features.main.edges.navigation

import com.example.navigation.NavigationPayload
import kotlinx.parcelize.Parcelize

@Parcelize
data class EdgePayload(
    val nodeCount: Int,
    override val payloadKey: String = PAYLOAD_KEY
) : NavigationPayload {

    companion object {
        const val PAYLOAD_KEY = "EDGE_PAYLOAD_KEY"
    }
}
