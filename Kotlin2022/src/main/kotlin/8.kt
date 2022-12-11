import util.Type
import kotlin.math.max
import kotlin.math.min

class Tree(val height: Int, val id: Int)

class Forest(inputs: List<String>) {

    private val map: List<List<Tree>> = parseInput(inputs)

    private fun parseInput(inputs: List<String>): List<MutableList<Tree>> {
        var id = 0

        val lines = mutableListOf<MutableList<Tree>>()
        inputs.forEach { originalLine ->
            run {
                val line: MutableList<Tree> = mutableListOf()
                originalLine.forEach { letter ->
                    line.add(Tree(letter.toString().toInt(), id++))
                }
                lines.add(line)
            }
        }
        return lines
    }

    private fun getRows(): List<List<Tree>> {
        return map
    }

    private fun getColumns(): List<List<Tree>> {
        return map[0].indices.map { getColumn(it) }
    }

    private fun getRow(row: Int): List<Tree> {
        return map[row]
    }

    private fun getColumn(column: Int): List<Tree> {
        return map.map { line -> line[column] }
    }

    private fun visibleTreesInLine(line: List<Tree>): Set<Tree> {
        val coordinatesOfVisibleTrees = mutableSetOf<Tree>()
        var largest = -1
        line.forEach { tree ->
            run {
                if (tree.height > largest) {
                    largest = tree.height
                    coordinatesOfVisibleTrees.add(tree)
                }
            }
        }
        return coordinatesOfVisibleTrees;
    }

    /**
     * Returns the amount of trees that are visible from any direction
     */
    fun visibleTrees(): Int {
        val visibleTrees = mutableSetOf<Tree>()
        getRows().forEach { row ->
            run {
                visibleTrees.addAll(visibleTreesInLine(row))
                // get all trees visible from front
                visibleTrees.addAll(visibleTreesInLine(row.reversed()))
            }
            getColumns().forEach { column ->
                run {
                    visibleTrees.addAll(visibleTreesInLine(column))
                    // get all trees visible from front
                    visibleTrees.addAll(visibleTreesInLine(column.reversed()))
                }
            }
        }
        return visibleTrees.size
    }

    fun viewingDistance(tree: Tree, trees: List<Tree>): Int {
        var sum = 0
        trees.forEach { otherTree ->
            run {
                sum += 1
                if (otherTree.height >= tree.height) {
                    return sum
                }
            }
        }
        return sum
    }

    private fun getScenicScoreOfTree(x: Int, y: Int): Int {
        // all trees left

        val tree = map[x][y]
        val row = getRow(x)
        val column = getColumn(y)

        val a = viewingDistance(tree, row.subList(0, y).reversed())
        val b = viewingDistance(tree, row.subList(y+1, row.size))
        val c = viewingDistance(tree, column.subList(0, x).reversed())
        val d = viewingDistance(tree, column.subList(x+1, column.size))

        return a*b*c*d
    }

    fun getScenicScoreMap(): Map<Tree, Int> {
        val scores = mutableMapOf<Tree, Int>()
        map.indices.forEach { rowIndex ->
            run {
                map[rowIndex].indices.forEach { columnIndex ->
                    run {
                        scores[map[rowIndex][columnIndex]] = getScenicScoreOfTree(rowIndex, columnIndex)
                    }
                }
            }
        }
        return scores
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        map.forEach { line ->
            run {
                line.forEach { tree ->
                    run {
                        stringBuilder.append(tree.height)
                    }
                }
                stringBuilder.append("\n")
            }
        }
        return stringBuilder.toString()
    }
}

fun main() {

    val day = 8
    val testOutput1 = "21"
    val testOutput2 = "8"

    // parseInput as List of Lists


    fun task1(inputs: List<String>): String {
        return Forest(inputs).visibleTrees().toString()

    }

    fun task2(inputs: List<String>): String {
        return Forest(inputs).getScenicScoreMap().values.max().toString()
    }

    util.run(Type.TEST, day, ::task1, testOutput1)
    util.run(Type.TASK, day, ::task1)
    util.run(Type.TEST, day, ::task2, testOutput2)
    util.run(Type.TASK, day, ::task2)
}