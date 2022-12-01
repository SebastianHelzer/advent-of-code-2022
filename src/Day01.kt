fun main() {
    fun getTotalCalories(input: List<String>): List<Int> {
        val indicesOfSpaces = input.mapIndexedNotNull { index, value ->
            if (value.isBlank()) index else null
        } + input.lastIndex

        val calories = indicesOfSpaces.windowed(2, 1) {
            input
                .subList(it.first(), it.last() + 1)
                .mapNotNull { it.toIntOrNull() }
        }

        return calories.map { it.sum() }
    }

    fun part1(input: List<String>): Int = getTotalCalories(input).max()

    fun part2(input: List<String>): Int = getTotalCalories(input)
        .sorted()
        .takeLast(3)
        .sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    println("expected=45000 actual=${part2(testInput)}")
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
