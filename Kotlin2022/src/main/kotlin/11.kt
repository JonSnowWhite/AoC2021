import util.Type

/**
 * Gathers all monkeys in a single object, so monkeys can find each other. Also provides score calculation function
 */
class MonkeyBusiness(private val monkeys: MutableList<Monkey> = mutableListOf()) {

    /**
     * Kinda wonky but eh
     */
    fun getMonkeyFromIndex(index: Int): Monkey {
        return monkeys[index]
    }

    fun addMonkey(monkey: Monkey) {
        monkeys.add(monkey)
    }

    fun turn() {
        monkeys.forEach {
            monkey -> monkey.turn()
        }
    }

    /**
     * Product of the inspection count of two monkeys with highest inspection count
     */
    fun score(): Long {
        return monkeys.map { monkey -> monkey.inspects }.sortedDescending().subList(0, 2).reduce { x, y -> x*y }
    }

    override fun toString(): String {
        return monkeys.joinToString("\n")
    }
}

/**
 * A monkey has items, inspects them with an operation, tests whether some condition is true, divides item value by
 * three (reduce with sensible lcm (check comments in task1/2 below) and throws to either of two monkeys if the test is
 * true or false. Reduces item values based on lcm of
 */
class Monkey(private val monkeyBusiness: MonkeyBusiness,
             private val items: MutableList<Long>,
             private val operation: (Long) -> Long,
             private val test: (Long) -> Boolean,
             private val throwIfTrue: Int,
             private val throwIfFalse: Int,
             private val lcm: Int,
             private val droppingWorryLevel: Boolean = true,
             var inspects: Long=0) {

    init {
        monkeyBusiness.addMonkey(this)
    }

    /**
     * receive item from other monkey... yeet
     */
    private fun addItem(item: Long) {
        items.add(item)
    }

    /**
     * Simulates a turn as described in task description
     */
    fun turn() {
        while(items.isNotEmpty()) {
            // each item is thrown somewhere
            var item = items.removeFirst()
            // monkey inspects this item
            inspects++
            // do inspect operation (increases value) and reduce by lcm
            item = operation(item) % lcm
            // only drop worryLevel/itemvalue in task 1
            if (droppingWorryLevel) {
                item /= 3
            }
            // throw to either monkey based on test value
            if (test(item)) {
                monkeyBusiness.getMonkeyFromIndex(throwIfTrue).addItem(item)
            } else {
                monkeyBusiness.getMonkeyFromIndex(throwIfFalse).addItem(item)
            }
        }
    }

    override fun toString(): String {
        return items.joinToString(", ", "[", "]")
    }
}

fun main() {

    val day = 11
    val testOutput1 = "10605"
    val testOutput2 = "2713310158"


    fun task1(inputs: List<String>): String {
        val monkeyBusiness = MonkeyBusiness()

        // what is input parsing
        if (inputs.size > 30) {
            // task
            val cm = 3 * 13 * 2 * 11 * 19 * 17 * 5 * 7
            val monkey0 = Monkey(monkeyBusiness, mutableListOf(59, 65, 86, 56, 74, 57, 56), { it * 17}, {it % 3 == 0L}, 3, 6, cm)
            val monkey1 = Monkey(monkeyBusiness, mutableListOf(63, 83, 50, 63, 56), { it + 2}, {it % 13 == 0L}, 3, 0, cm)
            val monkey2 = Monkey(monkeyBusiness, mutableListOf(93, 79, 74, 55), { it + 1}, {it % 2 == 0L}, 0, 1, cm)
            val monkey3 = Monkey(monkeyBusiness, mutableListOf(86, 61, 67, 88, 94, 69, 56, 91), { it + 7}, {it % 11 == 0L}, 6, 7, cm)
            val monkey4 = Monkey(monkeyBusiness, mutableListOf(76, 50, 51), { it * it}, {it % 19 == 0L}, 2, 5, cm)
            val monkey5 = Monkey(monkeyBusiness, mutableListOf(77, 76), { it + 8}, {it % 17 == 0L}, 2, 1, cm)
            val monkey6 = Monkey(monkeyBusiness, mutableListOf(74), { it * 2}, {it % 5 == 0L}, 4, 7, cm)
            val monkey7 = Monkey(monkeyBusiness, mutableListOf(86, 85, 52, 86, 91, 95), { it + 6}, {it % 7 == 0L}, 4, 5, cm)
        } else {
            // test
            val cm = 23 * 19 * 13 * 17
            val monkey0 = Monkey(monkeyBusiness, mutableListOf(79, 98), { it * 19}, {it % 23 == 0L}, 2, 3, cm)
            val monkey1 = Monkey(monkeyBusiness, mutableListOf(54, 65, 75, 74), { it + 6}, {it % 19 == 0L}, 2, 0, cm)
            val monkey2 = Monkey(monkeyBusiness, mutableListOf(79, 60, 97), { it * it}, {it % 13 == 0L}, 1, 3, cm)
            val monkey3 = Monkey(monkeyBusiness, mutableListOf(74), { it + 3}, {it % 17 == 0L}, 0, 1, cm)
        }

        (0 until 20).forEach { _ -> monkeyBusiness.turn()
        }

        return monkeyBusiness.score().toString()
    }

    fun task2(inputs: List<String>): String {
        val monkeyBusiness = MonkeyBusiness()
        val monkeys: MutableList<Monkey>

        // what is input parsing
        if (inputs.size > 30) {
            // task
            val cm = 3 * 13 * 2 * 11 * 19 * 17 * 5 * 7
            val monkey0 = Monkey(monkeyBusiness, mutableListOf(59, 65, 86, 56, 74, 57, 56), { it * 17}, {it % 3 == 0L}, 3, 6, cm, false)
            val monkey1 = Monkey(monkeyBusiness, mutableListOf(63, 83, 50, 63, 56), { it + 2}, {it % 13 == 0L}, 3, 0, cm, false)
            val monkey2 = Monkey(monkeyBusiness, mutableListOf(93, 79, 74, 55), { it + 1}, {it % 2 == 0L}, 0, 1, cm, false)
            val monkey3 = Monkey(monkeyBusiness, mutableListOf(86, 61, 67, 88, 94, 69, 56, 91), { it + 7}, {it % 11 == 0L}, 6, 7, cm, false)
            val monkey4 = Monkey(monkeyBusiness, mutableListOf(76, 50, 51), { it * it}, {it % 19 == 0L}, 2, 5, cm, false)
            val monkey5 = Monkey(monkeyBusiness, mutableListOf(77, 76), { it + 8}, {it % 17 == 0L}, 2, 1, cm, false)
            val monkey6 = Monkey(monkeyBusiness, mutableListOf(74), { it * 2}, {it % 5 == 0L}, 4, 7, cm, false)
            val monkey7 = Monkey(monkeyBusiness, mutableListOf(86, 85, 52, 86, 91, 95), { it + 6}, {it % 7 == 0L}, 4, 5, cm, false)
        } else {
            // test
            val cm = 23 * 19 * 13 * 17
            val monkey0 = Monkey(monkeyBusiness, mutableListOf(79, 98), { it * 19}, {it % 23 == 0L}, 2, 3, cm, false)
            val monkey1 = Monkey(monkeyBusiness, mutableListOf(54, 65, 75, 74), { it + 6}, {it % 19 == 0L}, 2, 0, cm, false)
            val monkey2 = Monkey(monkeyBusiness, mutableListOf(79, 60, 97), { it * it}, {it % 13 == 0L}, 1, 3, cm, false)
            val monkey3 = Monkey(monkeyBusiness, mutableListOf(74), { it + 3}, {it % 17 == 0L}, 0, 1, cm, false)
        }

        (0 until 10000).forEach { _ -> monkeyBusiness.turn()
        }

        return monkeyBusiness.score().toString()
    }

    util.run(Type.TEST, day, ::task1, testOutput1)
    util.run(Type.TASK, day, ::task1)
    // skip test for this one as no text is rendered
    util.run(Type.TEST, day, ::task2, testOutput2)
    util.run(Type.TASK, day, ::task2)
}