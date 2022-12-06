
fun main() {
    fun getSecretIndex(size: Int, input: String) : Int {
        return input.windowed(size, 1, false).indexOfFirst { it.toSet().size == size } + size
    }

    fun part1(input: String): Int = getSecretIndex(4, input)

    fun part2(input: String): Int = getSecretIndex(14, input)


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val (test1, test2, test3, test4, test5) = testInput
    checkEquals(7, part1(test1))
    checkEquals(5, part1(test2))
    checkEquals(6, part1(test3))
    checkEquals(10, part1(test4))
    checkEquals(11, part1(test5))
    checkEquals(19, part2(test1))
    checkEquals(23, part2(test2))
    checkEquals(23, part2(test3))
    checkEquals(29, part2(test4))
    checkEquals(26, part2(test5))

    val input = readInput("Day06")
    println(part1(input.first()))
    println(part2(input.first()))
}
