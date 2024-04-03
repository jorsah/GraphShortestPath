package com.example.graph.features.main.edges.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.graph.features.main.edges.models.Node
import com.example.graph.features.main.edges.models.WeightedEdge
import com.example.uikit.base.TableCell
import com.example.uikit.style.StyledKit

@Preview(showBackground = true)
@Composable
fun StyledKit.TableView(
    nodes: List<Node> = listOf(
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
    ).map { Node(it) },
    edges: List<WeightedEdge> = emptyList(),
    onCellClick: (WeightedEdge) -> Unit = {}
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val itemSize = screenWidth / if (nodes.size < 8) nodes.size + 1 else 9

    LazyRow {
        item {
            Column {
                TableCell(text = "", size = itemSize.dp)
                nodes.forEach {
                    TableCell(it.name, itemSize.dp)
                }
            }
        }

        items(nodes) { rowItem ->
            Column {
                TableCell(text = rowItem.name, size = itemSize.dp)
                nodes.forEach { columnItem ->
                    val edge = edges.firstOrNull {
                        it.start == columnItem && it.end == rowItem ||
                                (it.start == rowItem && it.end == columnItem)
                    }

                    TableCell(
                        text = when {
                            columnItem == rowItem -> "-"
                            edge != null -> edge.weight?.toString().orEmpty()
                            else -> ""
                        },
                        itemSize.dp
                    ) {
                        onCellClick(WeightedEdge(columnItem, rowItem, null))
                    }
                }
            }
        }
    }
}

