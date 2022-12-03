
fun main() {

    fun Char.getCharPriority(): Int = when (this) {
        in 'A'..'Z' -> this - 'A' + 27
        in 'a'..'z' -> this - 'a' + 1
        else -> error("Input $this must be a letter")
    }

    fun part1(input: List<String>): Int = input
        .map { line -> line.chunked(line.length/2).map { it.toSet() } }
        .map { (first, second) -> (first intersect second).single() }
        .sumOf { it.getCharPriority() }

    fun part2(input: List<String>): Int = input
        .map { it.toSet() }
        .chunked(3) { (first, second, third) -> (first intersect second intersect third).single() }
        .sumOf { it.getCharPriority() }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    checkEquals(157, part1(testInput))
    checkEquals(70, part2(testInput))

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
