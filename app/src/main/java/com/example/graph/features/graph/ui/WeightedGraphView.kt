package com.example.graph.features.graph.ui

import android.util.Log
import androidx.annotation.ColorInt
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp
import com.example.graph.features.graph.model.NodeUi
import com.example.graph.features.graph.model.WeightedEdgeUi
import com.example.graph.ui.theme.Teal700
import kotlinx.coroutines.launch
import kotlin.math.*

@Composable
fun WeightedGraphView(
    nodes: List<NodeUi>, edges: List<WeightedEdgeUi>,
    includedEdges: List<WeightedEdgeUi>
) {

    var sumText by remember {
        mutableStateOf(" ${includedEdges[0].weight}")
    }

    var valuesText by remember {
        mutableStateOf(includedEdges[0].start.name)
    }

    LaunchedEffect(includedEdges) {
        launch {
            nodes.first { node -> node.name == includedEdges[0].start.name }.color.animateTo(
                Magenta,
                animationSpec = tween(1000)
            )

            includedEdges.forEachIndexed { index, it ->
                if (it.animVal.value == 0f) {
                    it.animVal.animateTo(1f, animationSpec = tween(2000))
                    valuesText += "->${it.end.name}"

                    if (index != 0) sumText += "+${it.weight}"

                    nodes.first { node -> node.name == it.end.name }.color.animateTo(
                        Magenta,
                        animationSpec = tween(1000)
                    )
                }
            }

            sumText += "=${includedEdges.sumOf { it.weight ?: 0 }}"

        }
    }

    Box {
        // Draw the graph on Canvas
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            edges.forEach {
                drawEdge(
                    nodes.indexOfFirst { node -> it.start.name == node.name },
                    nodes.indexOfFirst { node -> it.end.name == node.name },
                    nodes.size,
                    it.weight.toString(),
                    1f,
                    Color(0x7E585858)
                )
            }

            includedEdges.forEach {
                drawEdge(
                    nodes.indexOfFirst { node -> it.start.name == node.name },
                    nodes.indexOfFirst { node -> it.end.name == node.name },
                    nodes.size,
                    it.weight.toString(),
                    it.animVal.value,
                    Teal700
                )
            }

            nodes.forEachIndexed { index, it ->
                val indexes = getIndexes(index, nodes.size)

                drawNode(
                    it.name,
                    color = it.color.value,
                    isIncluded = includedEdges.any { edge -> edge.start.name == it.name || edge.end.name == it.name },
                    indexes.first, indexes.second
                )
            }

        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(
                    16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = sumText,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = valuesText,
            )
        }
    }
}

fun DrawScope.drawNode(
    node: String,
    color: Color,
    isIncluded: Boolean,
    x: Float,
    y: Float,
) {
    val radius = 40f

    drawCircle(
        color = if (isIncluded) color else Color.Gray,
        radius = radius,
        center = Offset(x, y)
    )

    drawContentDescription(node, Offset(x, y))
}

fun DrawScope.drawEdge(
    startIndex: Int,
    endIndex: Int,
    nodesSize: Int,
    weight: String,
    animVal: Float,
    color: Color,
) {

    val centerX = size.width / 2
    val centerY = size.height / 2

    val startAngle = 2 * Math.PI * startIndex / nodesSize
    val startX = centerX + (size.width / 3) * cos(startAngle).toFloat()
    val startY = centerY + (size.height / 3) * sin(startAngle).toFloat()

    val endAngle = 2 * Math.PI * endIndex / nodesSize

    val endX = centerX + (size.width / 3) * cos(endAngle).toFloat()
    val endY = centerY + (size.height / 3) * sin(endAngle).toFloat()

    val tsx = startX + (endX - startX) * animVal
    val tey = startY + (endY - startY) * animVal


    drawLine(
        color = color,
        start = Offset(startX, startY),
        end = Offset(tsx, tey),
        strokeWidth = 4f
    )

    val dx = endX - startX
    val dy = endY - startY
    val arrowSize = 20f
    val angle = atan2(dy.toDouble(), dx.toDouble()) + Math.PI
    val arrowX1 = tsx + arrowSize * cos(angle + Math.PI / 6)
    val arrowY1 = tey + arrowSize * sin(angle + Math.PI / 6)
    val arrowX2 = tsx + arrowSize * cos(angle - Math.PI / 6)
    val arrowY2 = tey + arrowSize * sin(angle - Math.PI / 6)

    // Draw the arrowhead
    drawPath(
        Path().apply {
            moveTo(tsx, tey)
            lineTo(arrowX1.toFloat(), arrowY1.toFloat())
            lineTo(arrowX2.toFloat(), arrowY2.toFloat())
            close()
        },
        color = color,
        style = Stroke(width = 4f)
    )

    // Draw edge weight
    val weightX = (startX + endX) / 2
    val weightY = (startY + endY) / 2
    if (animVal == 1f) {
        drawContentDescription(
            weight,
            Offset(weightX, weightY),
            true,
            angle,
            if (color == Teal700) Magenta.toArgb() else
                Black.toArgb()
        )
    }
}

fun DrawScope.drawContentDescription(
    text: String,
    offset: Offset,
    isEdge: Boolean = false,
    angle: Double = 0.0,
    @ColorInt colorArgb: Int = Black.toArgb()
) {
    val offsetDif = if (isEdge) {
        Log.d("GOVNO", "abs(angle): $angle")
        if (abs(angle) > PI) 30 else -25
    } else {
        12
    }

    drawIntoCanvas {
        it.nativeCanvas.drawText(text, offset.x - offsetDif,
            offset.y + offsetDif,
            android.graphics.Paint().apply {
                color = colorArgb
                textSize = 36f
                isFakeBoldText = true
            })
    }
}


fun DrawScope.getIndexes(index: Int, nodesSize: Int): Pair<Float, Float> {
    val angle = 2 * Math.PI * index / nodesSize
    val centerX = size.width / 2
    val centerY = size.height / 2

    val x = centerX + (size.width / 3) * cos(angle).toFloat()
    val y = centerY + (size.height / 3) * sin(angle).toFloat()

    return x to y
}