
fun main() {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("DayXX_test")
    checkEquals(3, part1(testInput))
    checkEquals(3, part2(testInput))

    val input = readInput("DayXX")
    println(part1(input))
    println(part2(input))
}
