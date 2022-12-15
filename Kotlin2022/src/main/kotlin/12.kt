import util.Type
import kotlin.math.min

val S = 'S'.code
val E = 'E'.code

class TreeNode(var height: Int, val neighbors: MutableList<TreeNode> = mutableListOf()) {
    fun addNeighbor(node: TreeNode) {
        neighbors.add(node)
    }
}

class Graph(val nodes: MutableList<TreeNode>, private val startNode: TreeNode, private val endNode: TreeNode) {

    /**
     * Defaults to startNode for starting node. Wrapper for task 1
     */
    fun getShortestPath(): Int {
        return getShortestPath(startNode)
    }

    /**
     * Breadth first search (every way costs one so we are fine :^), im sure part two doesnt change that...right?)
     */
    private fun getShortestPath(startNode: TreeNode): Int {
        val visitedNodes = mutableListOf(startNode)
        val queue = mutableListOf(startNode)

        val nodeDistances = mutableMapOf(Pair(startNode, 0))

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            node.neighbors.forEach { neighbor ->
                // we skip visited nodes and the ones that are too high
                if (neighbor.height - node.height <= 1 && !visitedNodes.contains(neighbor)) {
                    // return when end reached
                    if (neighbor == endNode) {
                        return nodeDistances[node]?.plus(1) ?: -1
                    }
                    // add neighbor nodes to the ones we want to visit
                    nodeDistances[neighbor] = nodeDistances[node]?.plus(1) ?: -1
                    visitedNodes.add(neighbor)
                    queue.add(neighbor)
                }
            }
        }
        // println("No end node found for node. Returning max int value as shortest Path")
        return Int.MAX_VALUE
    }

    /**
     * Start from all possible positions with height a. Just calls the function from task 1. Less efficient than thought.
     * Cool people would just calculate the distance from the end node to every node with height a and return the
     * shortest but eeeh...
     */
    fun getShortestPathFromAnyLowestPoint(): Int {
        return nodes.filter { node -> node.height == 'a'.code }.map { getShortestPath(it) }.min()
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
        return getGraphFromInput(inputs).getShortestPath().toString()
    }

    fun task2(inputs: List<String>): String {
        return getGraphFromInput(inputs).getShortestPathFromAnyLowestPoint().toString()
    }

    // need different input file, too lazy to make testFile submittable here
    util.run(Type.TEST, day, ::task1, testOutput1)
    util.run(Type.TASK, day, ::task1)
    util.run(Type.TEST, day, ::task2, testOutput2)
    util.run(Type.TASK, day, ::task2)
}