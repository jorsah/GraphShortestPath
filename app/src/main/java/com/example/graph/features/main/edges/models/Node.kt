package com.example.graph.features.main.edges.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Node(
    val name: String
) : Parcelable
