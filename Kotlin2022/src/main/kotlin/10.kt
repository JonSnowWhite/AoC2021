import util.Type
import kotlin.math.absoluteValue

/**
 * Abstracts the CPU with its register and two operations
 */
class CPU {

    // the value of the CPU's register
    private var register: Int = 1

    // holds all values of the register
    // buffer one noop operation before the first execution
    private val registerValues: MutableList<Int> = mutableListOf(1)

    private fun noop() {
        registerValues.add(register)
    }

    private fun addX(value: Int) {
        // save value of register DURING/BEFORE this execution cycle
        registerValues.add(register)
        register += value
        registerValues.add(register)
    }

    /**
     * Apply all operations from input file
     */
    fun applyOperations(inputs: List<String>): CPU {
        inputs.forEach { line ->
            run {
                if (line.startsWith("noop")) {
                    noop()
                } else {
                    addX(line.split(" ")[1].toInt())
                }
            }
        }
        return this
    }

    /**
     * Returns the signal strength of a first clock cycle
     */
    private fun signalStrength(cycle: Int): Int {
        // register during 1st execution is contained at [0]
        return registerValues[cycle - 1]
    }

    /**
     * Returns signal strength multiplied with their cycle number
     */
    fun signalStrengthsTask1(cycles: List<Int>): List<Int> {
        return cycles.map { cycle -> cycle * signalStrength(cycle) }
    }

    /**
     * Draws CPU to screen by checking whether registerValue and its correspondingClockCycle are max off by one.
     * Considers the line length of 40 both in distance checking and line breaks in output
     */
    fun drawToScreen(): String {
        return "\n${
            registerValues.zip(registerValues.indices).joinToString("") { (registerValue, index) ->
                run {
                    val optionalLinebreak = if (index % 40 == 39) "\n" else ""
                    if (((index % 40) - registerValue).absoluteValue <= 1) {
                        "#$optionalLinebreak"
                    } else {
                        ".$optionalLinebreak"
                    }
                }
            }
        }"
    }
}

fun main() {

    val day = 10
    val testOutput1 = "13140"
    // yikes
    val testOutput2 =
            "\n##..##..##..##..##..##..##..##..##..##..\n" +
            "###...###...###...###...###...###...###.\n" +
            "####....####....####....####....####....\n" +
            "#####.....#####.....#####.....#####.....\n" +
            "######......######......######......####\n" +
            "#######.......#######.......#######.....\n."


    fun task1(inputs: List<String>): String {
        return CPU().applyOperations(inputs).signalStrengthsTask1(listOf(20, 60, 100, 140, 180, 220)).sum().toString()
    }

    fun task2(inputs: List<String>): String {
        return CPU().applyOperations(inputs).drawToScreen()
    }

    util.run(Type.TEST, day, ::task1, testOutput1)
    util.run(Type.TASK, day, ::task1)
    // skip test for this one as no text is rendered
    util.run(Type.TEST, day, ::task2, testOutput2)
    util.run(Type.TASK, day, ::task2)
}