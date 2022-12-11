package util

import java.io.File
import java.lang.NullPointerException

/**
 * Runs the provided task function with the input of the provided day
 */
fun run(type: Type, day: Int, function: (inputs: List<String>) -> String, output: String?=null) {

    // try to get input
    val inputs = when (type) {
        Type.TASK -> {
            getTaskInput(day)
        }
        Type.TEST -> {
            getTestInput(day)
        }
    }

    if (inputs == null) {
        println("Could not open ${type.typeName} file of day ${day}.")
        return
    }

    val startTime = System.currentTimeMillis()

    // execute
    val result = function(inputs)

    val endTime = System.currentTimeMillis()

    val expected: String = if (output != null && type== Type.TEST) {
        " Expected: $output"
    } else {
        ""
    }

    println("Executed $type of Day $day in ${endTime-startTime} ms with result: $result $expected")


}

fun getTestInput(task: Int): List<String>? {
    return getLines("data/task${task}_test.txt")
}

fun getTaskInput(task: Int): List<String>? {
    return getLines("data/task${task}.txt")
}

fun getLines (file: String): List<String>? {
    return try {
        File("src/main/resources/$file").readLines()
    } catch (e: NullPointerException) {
        null
    }

}
