import util.Type
import kotlin.math.absoluteValue

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    UL,
    UR,
    DL,
    DR,
    None;

    companion object {
        fun fromString(s: String): Direction {
            return when (s) {
                "U" -> UP
                "D" -> DOWN
                "R" -> RIGHT
                "L" -> LEFT
                else -> {
                    //println("Could not parse inputs $s to direction, defaulting to UP")
                    UP
                }
            }
        }
    }
}

enum class Diagonals {
}

class Location(var x: Int = 0, var y: Int = 0) {
    fun touches(other: Location): Boolean {
        return (x - other.x).absoluteValue <= 1 && (y - other.y).absoluteValue <= 1
    }

    fun applyDirection(direction: Direction) {
        when (direction) {
            Direction.None -> {}
            Direction.UP -> y += 1
            Direction.DOWN -> y -= 1
            Direction.LEFT -> x -= 1
            Direction.RIGHT -> x += 1
            Direction.UL -> {
                x -= 1
                y += 1
            }
            Direction.UR -> {
                y += 1
                x += 1
            }
            Direction.DL -> {
                y -= 1
                x -= 1
            }
            Direction.DR -> {
                y -= 1
                x += 1
            }
        }
    }

    fun getDirection(other: Location): Direction {
        if (other.x < x) {
            return if (other.y < y) {
                Direction.UR
            } else if (other.y > y) {
                Direction.DR
            } else {
                Direction.RIGHT
            }
        }
        if (other.x > x) {
            return if (other.y < y) {
                Direction.UL
            } else if (other.y > y) {
                Direction.DL
            } else {
                Direction.LEFT
            }
        }
        else {
            return if (other.y < y) {
                Direction.UP
            } else if (other.y > y) {
                Direction.DOWN
            } else {
                Direction.None
            }
        }
    }
}

class Rope(private var locations: List<Location> = listOf(Location(), Location())) {

    // saves visited coordinates as set of string x:y
    val tailsVisit = mutableSetOf<String>()
    private var head = locations.first()
    private var tail = locations.last()

    constructor(knots: Int) : this() {
        locations = (0 until knots).map { Location() }
        head = locations.first()
        tail = locations.last()
    }

    fun move(direction: Direction) {
        // move head
        head.applyDirection(direction)
        var previous = head

        // adjust all following nodes
        locations.slice(1 until locations.size).forEach { location ->
            run {
                // if the previous one still touches we don't have to do anything
                if (!location.touches(previous)) {
                    location.applyDirection(previous.getDirection(location))
                }
                previous = location
            }
        }
        tailsVisit.add("${tail.x}:${tail.y}")
    }

    fun applyInputs(inputs: List<String>): String {
        inputs.forEach { line ->
            run {
                val (directionString, movements) = line.split(" ")
                (1..movements.toInt()).forEach { _ ->
                    run {
                        move(Direction.fromString(directionString))
                    }
                }
            }
        }
        return tailsVisit.size.toString()
    }
}

fun main() {

    val day = 9
    // val testOutput1 = "13"
    val testOutput2 = "36"


    fun task1(inputs: List<String>): String {

        return Rope(2).applyInputs(inputs)
    }

    fun task2(inputs: List<String>): String {
        return Rope(10).applyInputs(inputs)
    }

    // need different input file, too lazy to make testFile submittable here
    // util.run(Type.TEST, day, ::task1, testOutput1)
    util.run(Type.TASK, day, ::task1)
    util.run(Type.TEST, day, ::task2, testOutput2)
    util.run(Type.TASK, day, ::task2)
}