package com.example.graph.features.graph.model

import com.example.graph.features.main.edges.models.Node
import com.example.graph.features.main.edges.models.WeightedEdge

/**
 * See https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 */
fun findShortestPath(edges: List<WeightedEdge>, source: Node, target: Node): ShortestPathResult {

  // Note: this implementation uses similar variable names as the algorithm given do.
  // We found it more important to align with the algorithm than to use possibly more sensible naming.

  val dist = mutableMapOf<Node, Int>()
  val prev = mutableMapOf<Node, Node?>()
  val q = findDistinctNodes(edges)

  q.forEach { v ->
    dist[v] = Integer.MAX_VALUE
    prev[v] = null
  }
  dist[source] = 0

  while (q.isNotEmpty()) {
    val u = q.minByOrNull { dist[it] ?: 0 }
    q.remove(u)

    if (u == target) {
      break // Found shortest path to target
    }
    edges
        .filter { it.start == u }
        .forEach { edge ->
          val v = edge.end
          val alt = (dist[u] ?: 0) + (edge.weight ?: 0)
          if (alt < (dist[v] ?: 0)) {
            dist[v] = alt
            prev[v] = u
          }
        }
  }

  return ShortestPathResult(prev, dist, source, target)
}

private fun findDistinctNodes(edges: List<WeightedEdge>): MutableSet<Node> {
  val nodes = mutableSetOf<Node>()
  edges.forEach {
    nodes.add(it.start)
    nodes.add(it.end)
  }
  return nodes
}

/**
// * Traverse result
// */
class ShortestPathResult(val prev: Map<Node, Node?>, val dist: Map<Node, Int>, val source: Node, val target: Node) {

  fun shortestPath(from: Node = source, to: Node = target, list: List<Node> = emptyList()): List<Node> {
    val last = prev[to] ?: return if (from == to) {
      list + to
    } else {
      emptyList()
    }
    return shortestPath(from, last, list) + to
  }

  fun shortestDistance(): Int? {
    val shortest = dist[target]
    if (shortest == Integer.MAX_VALUE) {
      return null
    }
    return shortest
  }
}