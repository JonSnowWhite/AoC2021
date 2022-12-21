import util.Type

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