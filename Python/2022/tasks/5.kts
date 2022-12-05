import java.io.File;

fun getLines (file: String): List<String>
    return File(file).readLines()

println(getLines("../data/task5.txt"))