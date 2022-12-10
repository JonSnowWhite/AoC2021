import java.util.*
import kotlin.math.min

const val DAY = 5
const val TEST_OUTPUT_1 = "CMZ"
const val TEST_OUTPUT_2 = "MCD"

/**
 * Returns a list of stacks as described in the input file
 */
fun getStacksFromInput(inputs: List<String>): List<MutableList<Char>> {
    val indexOfStackNumbers = inputs.indexOf(inputs.find { !it.contains("[") })
    val stackNumber = inputs[indexOfStackNumbers].split(" ").last().toInt()

    // create list of stacks
    val stacks: MutableList<MutableList<Char>> = mutableListOf()
    (1..stackNumber).forEach { _ -> stacks.add(mutableListOf())}

    // parse all previous lines
    (0 until indexOfStackNumbers).reversed().forEach {
        run {
            // always parse by block of 3
            var line = inputs[it]
            var stackIndex = 0
            while (line.isNotEmpty()) {
                // parse important letter and add to correct stack
                val letter = line[1]
                if (letter != ' ') {
                    stacks[stackIndex].add(letter)
                }
                line = line.substring(min(line.length, 4))
                stackIndex += 1
            }
        }
    }
    return stacks
}

/**
 * Moves the crates one-by-one as indicated by the command
 */
fun applyCommandToStacksTask1(command: String, stacks: List<MutableList<Char>>) {
    val splitCommand = command.split(" ")
    val amountToMove = splitCommand[1].toInt()
    val moveFrom = splitCommand[3].toInt()-1
    val moveTo = splitCommand[5].toInt()-1
    (1..amountToMove).forEach { _ -> stacks[moveTo].add(stacks[moveFrom].removeLast()) }
}

/**
 * Moves the crates as a bunch as indicated by the command
 */
fun applyCommandToStacksTask2(command: String, stacks: List<MutableList<Char>>) {
    val splitCommand = command.split(" ")
    val amountToMove = splitCommand[1].toInt()
    val moveFrom = splitCommand[3].toInt()-1
    val moveTo = splitCommand[5].toInt()-1
    // probably inefficient but the buffer does the job of reversing the order
    val buffer = mutableListOf<Char>()
    (1..amountToMove).forEach { _ -> buffer.add(stacks[moveFrom].removeLast()) }
    (1..amountToMove).forEach { _ -> stacks[moveTo].add(buffer.removeLast()) }
}

fun getStackTops(stacks: List<MutableList<Char>>): String {
    return stacks.map { it.last() }.joinToString("")
}

fun task1(inputs: List<String>): String {
    val stacks = getStacksFromInput(inputs)
    val firstCommandLineIndex = inputs.indexOfFirst { it.isEmpty() } + 1
    for (i in firstCommandLineIndex until inputs.size) {
        applyCommandToStacksTask1(inputs[i], stacks)
    }
    return getStackTops(stacks)
}

fun task2(inputs: List<String>): String {
    val stacks = getStacksFromInput(inputs)
    val firstCommandLineIndex = inputs.indexOfFirst { it.isEmpty() } + 1
    for (i in firstCommandLineIndex until inputs.size) {
        applyCommandToStacksTask2(inputs[i], stacks)
    }
    return getStackTops(stacks)
}


fun main() {
    run(Type.TEST, DAY, ::task1, TEST_OUTPUT_1)
    run(Type.TASK, DAY, ::task1)
    run(Type.TEST, DAY, ::task2, TEST_OUTPUT_2)
    run(Type.TASK, DAY, ::task2)
}