package com.example.graph.features.main.edges.utills

import com.example.graph.features.main.edges.models.Node
import com.example.graph.features.main.edges.models.WeightedEdge

class CheckConnectivity {

    data class Graph(val vertices: Int) {
        val adjacencyList: MutableList<MutableList<Int>> = mutableListOf()

        init {
            repeat(vertices) { adjacencyList.add(mutableListOf()) }
        }

        fun addEdge(source: Int, dest: Int) {
            adjacencyList[source].add(dest)
            adjacencyList[dest].add(source)
        }

        fun addEdges(edges: List<WeightedEdge>) {
            edges.forEach { edge ->
                adjacencyList[edge.start.getPosition()].add(edge.end.getPosition())
                adjacencyList[edge.end.getPosition()].add(edge.start.getPosition())
            }
        }
    }

    fun isConnected(graph: Graph):Boolean {
        val visited = BooleanArray(graph.vertices) { false }
        DFS(0, graph.adjacencyList, visited)

        // Check if all elements in visited are true
        return visited.all { it }
    }

    private fun DFS(source: Int, adjacencyList: List<MutableList<Int>>, visited: BooleanArray) {
        visited[source] = true // Mark the current vertex as visited

        for (neighbor in adjacencyList[source]) {
            if (!visited[neighbor]) {
                DFS(neighbor, adjacencyList, visited)
            }
        }
    }
}

fun Node.getPosition(): Int {
    val char = name.lowercase().toCharArray().first()

    if (char !in 'a'..'z') {
        throw IllegalArgumentException("Input must be a lowercase letter")
    }
    return char.code - 'a'.code
}
