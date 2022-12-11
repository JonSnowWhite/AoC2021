import util.Type

class FileSystem {
    private var rootNode: Node = Directory(null, "/")
    private var pointer = rootNode

    fun cd(location: String) {
        val oldPointer = pointer
        pointer = when (location) {
            "/" -> {
                rootNode
            }

            ".." -> {
                pointer.parent!!
            }

            else -> {
                (pointer as Directory).children.find { child -> child.name == location }!!
            }
        }
    }

    fun addDir(name: String) {
        (pointer as Directory).addChild(Directory(pointer, name))
    }

    fun addFile(size: Int, name: String) {
        (pointer as Directory).addChild(Leaf(pointer, name, size))
    }

    override fun toString(): String {
        return rootNode.toString(0)
    }

    fun task1(): String {
        return rootNode.getSumOfDirsUnderValueAndOwnSize(100000)[0].toString()
    }

    /**
     * basically task 2
     */
    fun getSizesOfDirs(): MutableList<Int> {
        val sizes = mutableListOf<Int>()
        (rootNode as Directory).addDirSizes(sizes)
        return sizes
    }

    fun getTotalSize(): Int {
        return rootNode.getSize()
    }
}

abstract class Node(val parent: Node?, val name: String) {
    abstract fun toString(depth: Int): String

    abstract fun getSize(): Int

    // task 1
    abstract fun getSumOfDirsUnderValueAndOwnSize(value: Int): List<Int>
}

class Leaf(parent: Node, name: String, private val size: Int) : Node(parent, name) {
    override fun toString(depth: Int): String {
        return "${"  ".repeat(depth)}- $name (file, size=$size)\n"
    }

    override fun getSize(): Int {
        return size
    }

    override fun getSumOfDirsUnderValueAndOwnSize(value: Int): List<Int> {
        return listOf(0, size)
    }
}

class Directory(parent: Node?, name: String) : Node(parent, name) {

    val children: MutableList<Node> = mutableListOf()

    constructor(parent: Node?, name: String, children: List<Node>) : this(parent, name) {
        this.children.addAll(children)
    }

    fun addChild(child: Node) {
        this.children.add(child)
    }

    override fun toString(depth: Int): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("${"  ".repeat(depth)}- $name (dir)\n")
        children.forEach { child ->
            run {
                stringBuilder.append(child.toString(depth + 1))
            }
        }
        return stringBuilder.toString()
    }

    override fun getSize(): Int {
        return children.sumOf { child -> child.getSize() }
    }

    /**
     * Ugly DFS that returns a pair of values. First value is sum of all (sub)-dirs whose size is under given value, second
     * value is total size of this dir.
     */
    override fun getSumOfDirsUnderValueAndOwnSize(value: Int): List<Int> {
        var sum = 0
        var ownSize = 0
        children.forEach { child ->
            run {
                val (childSum, childOwnSize) = child.getSumOfDirsUnderValueAndOwnSize(value)
                sum += childSum
                ownSize += childOwnSize
            }
        }
        if (ownSize < value) {
            // println("Added dir $name with size $ownSize")
            sum += ownSize
        }
        return listOf(sum, ownSize)
    }

    /**
     * Adds directory sizes of this and all subdirectories to given list. Inefficient, whoopsie
     */
    fun addDirSizes(sizes: MutableList<Int>) {
        var size = 0
        children.forEach { child ->
            run {
                size += if (child is Directory) {
                    child.addDirSizes(sizes)
                    child.getSize()
                } else {
                    // leaf
                    child.getSize()
                }
            }
        }
        sizes.add(size)
    }
}

fun buildFileSystem(inputs: List<String>): FileSystem {
    val fileSystem = FileSystem()
    val mutableInputs = inputs.toMutableList()

    while (mutableInputs.isNotEmpty()) {
        // always read a line from the inputs and interpret it
        val commands = mutableInputs.removeFirst().split(" ")

        when (commands[1]) {

            // simple cd to different directory in filesystem
            "cd" -> fileSystem.cd(commands[2])

            // parse all following lines until new command comes
            "ls" -> {
                var lastIndex = mutableInputs.indexOfFirst { x -> x.contains("\$") }
                if (lastIndex == -1) {
                    lastIndex = mutableInputs.lastIndex + 1
                }
                (0 until lastIndex).forEach { leaf ->
                    run {
                        val (line1, line2) = mutableInputs[leaf].split(" ")
                        if (line1 == "dir") {
                            fileSystem.addDir(line2)
                        } else {
                            // file
                            fileSystem.addFile(line1.toInt(), line2)
                        }
                    }
                }
            }
        }
    }
    return fileSystem
}

fun main() {

    val day = 7
    val testOutput1 = "95437"
    val testOutput2 = "24933642"


    fun task1(inputs: List<String>): String {
        return buildFileSystem(inputs).task1()
    }

    fun task2(inputs: List<String>): String {
        val fileSystem = buildFileSystem(inputs)
        val sizes = fileSystem.getSizesOfDirs()
        val neededSpace = 30000000 - (70000000 - fileSystem.getTotalSize())
        return sizes.filter { size -> size >= neededSpace }.minOf { it }.toString()
    }

    util.run(Type.TEST, day, ::task1, testOutput1)
    util.run(Type.TASK, day, ::task1)
    util.run(Type.TEST, day, ::task2, testOutput2)
    util.run(Type.TASK, day, ::task2)
}