import util.Type

class WrapList(private val values: MutableList<Int>) {

    // holds the original index for every current index
    // for example when the first two values get swapped in the first step we change map[0] = 0 to map[0] = 1
    private val originalIndex = MutableList<Int>(values.size) { it }

    /**
     * Swaps values at specified indices a and b
     */
    private fun swapValues(a: Int, b: Int) {
        val tmp = values[a]
        val tmpOriginalIndex = originalIndex[a]
        values[a] = values[b]
        originalIndex[a] = b

        values[b] = tmp
        originalIndex[b] = tmpOriginalIndex

    }
    private fun moveRight(fromIndex: Int, steps: Int) {
        (0 until steps).forEach {
            swapValues((fromIndex + it) % values.size, (fromIndex + it + 1) % values.size)
        }
    }

    private fun moveLeft(fromIndex: Int, steps: Int) {
        (0 until steps).forEach {
            swapValues((fromIndex - it) % values.size, (fromIndex - it - 1) % values.size)
        }
    }

    fun sortAllValues() {
        values.indices.forEach {
            val indexToMoveFrom = originalIndex.indexOf(it)
            val steps = values[indexToMoveFrom]
            if (steps < 0) {
                moveLeft(indexToMoveFrom, -steps)
            } else {
                moveRight(indexToMoveFrom, steps)
            }
        }
    }

    /**
     * Returns value at index n after value 0
     */
    private fun getValue(n: Int): Int {
        return values[(values.indexOf(0)+n) % values.size]
    }

    fun getValues(valuesToExtract: List<Int>): Int {
        return valuesToExtract.sumOf { getValue(it) }
    }
}

fun main() {

    val day = 20
    val testOutput1 = "3"
    val testOutput2 = "?"


    fun task1(inputs: List<String>): String {
        return WrapList(inputs.map { it.toInt() }.toMutableList()).getValues(listOf(1000,2000,3000)).toString()
    }

    fun task2(inputs: List<String>): String {
        return ""
    }

    // need different input file, too lazy to make testFile submittable here
    util.run(Type.TEST, day, ::task1, testOutput1)
    util.run(Type.TASK, day, ::task1)
    util.run(Type.TEST, day, ::task2, testOutput2)
    util.run(Type.TASK, day, ::task2)
}