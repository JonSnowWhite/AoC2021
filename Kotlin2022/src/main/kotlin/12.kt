import util.Type
import kotlin.math.min

val S = 'S'.code
val E = 'E'.code

class TreeNode(var height: Int, val neighbors: MutableList<TreeNode> = mutableListOf()) {
    fun addNeighbor(node: TreeNode) {
        neighbors.add(node)
    }
}

class Graph(private val nodes: MutableList<TreeNode>, private val startNode: TreeNode, private val endNode: TreeNode) {

    /**
     * Defaults to startNode for starting node. Wrapper for task 1
     */
    fun getShortestPath(): Int {
        return getDistancesFromEndNode().filter { it.key.height == 'a'.code }.minOf { it.value }
    }

    private fun getShortestPathToNode(node: TreeNode): Int {
        return getDistancesFromEndNode()[node] ?: Int.MAX_VALUE
    }

    fun getShortestPathToStartNode(): Int {
        return getShortestPathToNode(startNode)
    }

    /**
     * BFS reverse search to get distance from every node to end node. Allows easy filtering for distance from any node
     */
    private fun getDistancesFromEndNode(): Map<TreeNode, Int> {
        val visitedNodes = mutableListOf(endNode)
        val queue = mutableListOf(endNode)

        val nodeDistances = mutableMapOf(Pair(endNode, 0))

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            node.neighbors.forEach { neighbor ->
                // we skip visited nodes and the ones that are too high
                if (node.height - neighbor.height <= 1 && !visitedNodes.contains(neighbor)) {
                    // add neighbor nodes to the ones we want to visit
                    nodeDistances[neighbor] = nodeDistances[node]?.plus(1) ?: -1
                    visitedNodes.add(neighbor)
                    queue.add(neighbor)
                }
            }
        }
        return nodeDistances
    }
}

fun getGraphFromInput(inputs: List<String>): Graph {
    // parse inputs letters to nodes
    val nodes = inputs.map { line -> line.toCharArray().map { char -> TreeNode(char.code) } }
    var startNode: TreeNode? = null
    var endNode: TreeNode? = null
    val nodesList = mutableListOf<TreeNode>()

    // add all neighbors
    nodes.indices.forEach { y ->
        nodes[y].indices.forEach { x ->
            run {
                val node = nodes[y][x]
                nodesList.add(node)
                // set start and end node
                if (node.height == S) {
                    startNode = node
                    node.height = 'a'.code
                }
                if (node.height == E) {
                    endNode = node
                    node.height = 'z'.code
                }

                val maxX = nodes[y].size - 1
                val maxY = nodes.size - 1

                var leftNode: TreeNode? = null
                var rightNode: TreeNode? = null
                var upNode: TreeNode? = null
                var downNode: TreeNode? = null
                if (x > 0) {
                    leftNode = nodes[y][x-1]
                }
                if (x < maxX) {
                    rightNode = nodes[y][x+1]
                }
                if (y > 0) {
                    upNode = nodes[y-1][x]
                }
                if (y < maxY) {
                    downNode = nodes[y+1][x]
                }
                // add all neighbor nodes to this node if present
                leftNode?.let { node.addNeighbor(it) }
                rightNode?.let { node.addNeighbor(it) }
                upNode?.let { node.addNeighbor(it) }
                downNode?.let { node.addNeighbor(it) }
            }
        }
    }

    return Graph(nodesList, startNode!!, endNode!!)
}

fun main() {

    val day = 12
    val testOutput1 = "31"
    val testOutput2 = "29"


    fun task1(inputs: List<String>): String {
        return getGraphFromInput(inputs).getShortestPathToStartNode().toString()
    }

    fun task2(inputs: List<String>): String {
        return getGraphFromInput(inputs).getShortestPath().toString()
    }

    // need different input file, too lazy to make testFile submittable here
    util.run(Type.TEST, day, ::task1, testOutput1)
    util.run(Type.TASK, day, ::task1)
    util.run(Type.TEST, day, ::task2, testOutput2)
    util.run(Type.TASK, day, ::task2)
}