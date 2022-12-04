
fun main() {


    fun getRangePairsFromInput(input: List<String>): List<Pair<IntRange, IntRange>> {
        return input.map {
            val (first, second) = it.split(',').map {
                val (start, end) = it.split('-').map { it.toInt() }
                start..end
            }
            first to second
        }
    }

    fun part1(input: List<String>): Int {
        val rangePairs: List<Pair<IntRange, IntRange>> = getRangePairsFromInput(input)

        return rangePairs.count { (first, second) ->
            val combined = first union second
            combined == first.toSet() || combined == second.toSet()
        }
    }

    fun part2(input: List<String>): Int {
        val rangePairs: List<Pair<IntRange, IntRange>> = getRangePairsFromInput(input)

        return rangePairs.count { (first, second) ->
            (first intersect second).isNotEmpty()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    checkEquals(2, part1(testInput))
    checkEquals(4, part2(testInput))

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
