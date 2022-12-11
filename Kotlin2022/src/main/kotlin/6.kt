import util.Type
import kotlin.math.min

fun main() {

    val day = 6
    val testOutput1 = "7"
    val testOutput2 = "19"

    fun <T> List<T>.containsDuplicates(): Boolean {
        (this.indices).forEach {
            (it + 1 until this.size).forEach { it2 ->
                if (this[it] == this[it2]) {
                    return true
                }
            }
        }
        return false
    }

    fun getFirstSequenceWithoutDifference(sequence: MutableList<Char>, sequenceLength: Int): String {
        val buffer = mutableListOf<Char>()

        // add first 4 characters to buffer. if any are the same we return 4
        (0 until sequenceLength).forEach { _ ->
            buffer.add(sequence.removeFirst())
        }
        if (!buffer.containsDuplicates()) {
            return sequenceLength.toString()
        }

        // for each remaining character put it into buffer and check if any letters are the same
        var index = 0
        (sequence.indices).forEach { _ ->
            run {
                index += 1
                buffer.removeFirst()
                buffer.add(sequence.removeFirst())
                if (!buffer.containsDuplicates()) {
                    return (sequenceLength+index).toString()
                }
            }
        }
        return "only duplicates"
    }

    fun task1(inputs: List<String>): String {
        val input = inputs[0].toMutableList()
        return getFirstSequenceWithoutDifference(input, 4)
    }

    fun task2(inputs: List<String>): String {
        val input = inputs[0].toMutableList()
        return getFirstSequenceWithoutDifference(input, 14)
    }

    util.run(Type.TEST, day, ::task1, testOutput1)
    util.run(Type.TASK, day, ::task1)
    util.run(Type.TEST, day, ::task2, testOutput2)
    util.run(Type.TASK, day, ::task2)
}