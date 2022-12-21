import util.Type

enum class Sorted {
    TRUE,
    FALSE,
    UNSURE
}

/**
 * Can be either a list or an int, one should be set otherwise this makes no sense
 */
class ListOrInt(var int: Int? = null, var list: MutableList<ListOrInt>? = null) : Comparable<ListOrInt> {
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

    override fun compareTo(other: ListOrInt): Int {
        val left = this
        val right = other
        //println("- Compare $left || $right")
        if (left.isInt() && right.isInt()) {
            // simple integer comparison
            return if (left.int!! < right.int!!) {
                //println("- Left side smaller than right so ordered correctly")
                -1
            } else if (right.int!! < left.int!!) {
                //println("- Left side larger than right so ordered incorrectly")
                return 1
            } else {
                return 0
            }
        } else if (left.isList() && right.isList()) {
            val leftList = left.list!!
            val rightList = right.list!!
            // compare each element in list, left list can be shorter than right
            for (index in leftList.indices) {
                if (rightList.size <= index) {
                    // right list runs out of items first so not sorted correctly
                    //println("- Right side ran out of items so ordered incorrectly")
                    return 1
                } else {
                    // return false if sub-comparison fails
                    // propagate false and true sorting
                    val subSorting = leftList[index].compareTo(rightList[index])
                    if (subSorting < 0) {
                        return -1
                    } else if (subSorting > 0) {
                        return 1
                    }
                    // continue when subCompare is indecisive
                }
            }
            // left list ran out of items
            return if (rightList.size > leftList.size) {
                //println("- Left side ran out of items so ordered correctly")
                -1
            } else {
                0
            }
        } else {
            // one of both is integer, and we do the comparison by casting it to a list
            if (left.isInt()) {
                //println("- Parsing left to list")
                return ListOrInt(int = null, list = mutableListOf(ListOrInt(int = left.int))).compareTo(right)
            }
            if (right.isInt()) {
                //println("- Parsing right to list")
                return left.compareTo(ListOrInt(int = null, list = mutableListOf(ListOrInt(int = right.int))))
            }
        }
        // unreachable
        //println("REACHED UNREACHABLE STATEMENT")
        return 0
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

fun getNumberOfOrderedPairs(inputs: List<String>): Int {
    var sum = 0
    var pair = 0
    for (index in inputs.indices step 3) {
        pair++
        //println("\n=== Pair $pair ===")
        val left = inputs[index]
        val right = inputs[index + 1]
        if (ListOrInt(left) < ListOrInt(right)) {
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
        val lists: MutableList<ListOrInt> = inputs.filter { x -> x != "" }.map { x -> ListOrInt(x) }.toMutableList()
        val divider1 = ListOrInt("[[2]]")
        val divider2 = ListOrInt("[[6]]")
        lists.add(divider1)
        lists.add(divider2)
        lists.sort()
        return ((lists.indexOf(divider1)+1) * (lists.indexOf(divider2)+1)).toString()
    }

    // need different input file, too lazy to make testFile submittable here
    util.run(Type.TEST, day, ::task1, testOutput1)
    util.run(Type.TASK, day, ::task1)
    util.run(Type.TEST, day, ::task2, testOutput2)
    util.run(Type.TASK, day, ::task2)
}