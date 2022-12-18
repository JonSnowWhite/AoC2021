import util.Type

enum class Sorted {
    TRUE,
    FALSE,
    UNSURE
}

/**
 * Can be either a list or an int, one should be set otherwise this makes no sense
 */
class ListOrInt(var int: Int? = null, var list: MutableList<ListOrInt>? = null) {
    fun isInt(): Boolean {
        return (int != null)
    }

    fun isList(): Boolean {
        return (list != null)
    }

    constructor(input: String) : this() {
        int = input.toIntOrNull();
        if (int == null) {
            list = mutableListOf()
            // counts depth of current list, if zero and we see a comma we found a top level element
            var depthCounter = 0
            val inputWithoutOuterParantheses = input.substring(1, input.length - 1)
            var currentVal = ""
            // is list
            // remove [], split at <,> , and map every element to its listOrInt object
            for (letter in inputWithoutOuterParantheses) {
                if (letter == '[') {
                    depthCounter++
                    currentVal += letter
                } else if (letter == ']') {
                    depthCounter--
                    currentVal += letter
                } else if (letter == ',' && depthCounter == 0) {
                    list!!.add(ListOrInt(currentVal))
                    currentVal = ""
                } else {
                    currentVal += letter
                }
            }
            if (currentVal.isNotEmpty()) {
                list!!.add(ListOrInt(currentVal))
            }
            // list = input.substring(1, input.length - 1).split(",").map { ListOrInt(it) }
        }
    }

    /**
     * Inefficient but eh
     */
    override fun toString(): String {
        if (isInt()) {
            return int!!.toString()
        }
        if (isList()) {
            return list!!.joinToString(",", "[", "]") { it.toString() }
        }
        return "none"
    }
}

fun compare(left: ListOrInt, right: ListOrInt, indent: Int=0): Sorted {
    //println("${" ".repeat(indent)}- Compare $left || $right")
    if (left.isInt() && right.isInt()) {
        // simple integer comparison
        return if (left.int!! < right.int!!) {
            //println("${" ".repeat(indent+1)}- Left side smaller than right so ordered correctly")
            Sorted.TRUE
        } else if (right.int!! < left.int!!) {
            //println("${" ".repeat(indent+1)}- Left side larger than right so ordered incorrectly")
            Sorted.FALSE
        } else {
            Sorted.UNSURE
        }
    } else if (left.isList() && right.isList()) {
        val leftList = left.list!!
        val rightList = right.list!!
        // compare each element in list, left list can be shorter than right
        for (index in leftList.indices) {
            if (rightList.size <= index) {
                // right list runs out of items first so not sorted correctly
                //println("${" ".repeat(indent+1)}- Right side ran out of items so ordered incorrectly")
                return Sorted.FALSE
            } else {
                // return false if sub-comparison fails
                val subCompare = compare(leftList[index], rightList[index], indent+1)
                // propagate false and true sorting
                if (subCompare == Sorted.TRUE) {
                    return Sorted.TRUE
                } else if (subCompare == Sorted.FALSE) {
                    return Sorted.FALSE
                }
                // continue when subCompare is indecisive
            }
        }
        // left list ran out of items
        return if (rightList.size > leftList.size) {
            //println("${" ".repeat(indent+1)}- Left side ran out of items so ordered correctly")
            Sorted.TRUE
        } else {
            Sorted.UNSURE
        }
    } else {
        // one of both is integer, and we do the comparison by casting it to a list
        if (left.isInt()) {
            //println("${" ".repeat(indent)}- Parsing left to list")
            return compare(ListOrInt(int = null, list = mutableListOf(ListOrInt(int = left.int))), right, indent+1)
        }
        if (right.isInt()) {
            //println("${" ".repeat(indent)}- Parsing right to list")
            return compare(left, ListOrInt(int = null, list = mutableListOf(ListOrInt(int = right.int))), indent+1)
        }
    }
    // unreachable
    //println("REACHED UNREACHABLE STATEMENT")
    return Sorted.UNSURE
}

fun getNumberOfOrderedPairs(inputs: List<String>): Int {
    var sum = 0
    var pair = 0
    for (index in inputs.indices step 3) {
        pair++
        //println("\n=== Pair $pair ===")
        val left = inputs[index]
        val right = inputs[index + 1]
        val compareValue = compare(ListOrInt(left), ListOrInt(right))
        if (compareValue == Sorted.TRUE) {
            sum += pair
        }
    }
    return sum
}

fun main() {

    val day = 13
    val testOutput1 = "13"
    val testOutput2 = "140"


    fun task1(inputs: List<String>): String {
        return getNumberOfOrderedPairs(inputs).toString()
    }

    fun task2(inputs: List<String>): String {
        // TODO: sort this
        val lists: MutableList<ListOrInt> = inputs.map { x -> ListOrInt(x) }.toMutableList()
        lists.add(ListOrInt("[[2]"))
        lists.add(ListOrInt("[[6]"))


    }

    // need different input file, too lazy to make testFile submittable here
    util.run(Type.TEST, day, ::task1, testOutput1)
    util.run(Type.TASK, day, ::task1)
    util.run(Type.TEST, day, ::task2, testOutput2)
    util.run(Type.TASK, day, ::task2)
}