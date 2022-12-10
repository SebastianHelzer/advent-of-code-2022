
fun main() {

    fun getMoves(input: List<String>): List<Pair<Char, Int>> {
        return input.map {
            val (dir, length) = it.split(" ")
            dir.first() to length.toInt()
        }
    }

    fun calculateTailPositions(
        moves: List<Pair<Char, Int>>,
        ropeLength: Int = 1,
    ): Set<Pair<Int, Int>> {

        val s = 0 to 0
        var h = s
        val knots: MutableList<Pair<Int, Int>> = (0 until ropeLength).map { s }.toMutableList()
        val lastKnotSet = mutableSetOf(knots.last())
        moves.forEach { (dir, length) ->
            repeat(length) {
                h = when (dir) {
                    'R' -> h.copy(first = h.first + 1)
                    'L' -> h.copy(first = h.first - 1)
                    'D' -> h.copy(second = h.second - 1)
                    'U' -> h.copy(second = h.second + 1)
                    else -> error("unknown direction")
                }

                for ((index, t) in knots.withIndex()) {
                    val prev = knots.getOrNull(index - 1) ?: h

                    val newT = when(prev) {
                        t.copy(first = t.first + 2) -> t.copy(first = t.first + 1)
                        t.copy(first = t.first - 2) -> t.copy(first = t.first - 1)
                        t.copy(second = t.second + 2) -> t.copy(second = t.second + 1)
                        t.copy(second = t.second - 2) -> t.copy(second = t.second - 1)
                        t.copy(first = t.first + 2, second = t.second + 1) -> t.copy(first = t.first + 1, t.second + 1)
                        t.copy(first = t.first + 2, second = t.second - 1) -> t.copy(first = t.first + 1, t.second - 1)
                        t.copy(first = t.first - 2, second = t.second + 1) -> t.copy(first = t.first - 1, t.second + 1)
                        t.copy(first = t.first - 2, second = t.second - 1) -> t.copy(first = t.first - 1, t.second - 1)
                        t.copy(first = t.first + 1, second = t.second + 2) -> t.copy(first = t.first + 1, t.second + 1)
                        t.copy(first = t.first + 1, second = t.second - 2) -> t.copy(first = t.first + 1, t.second - 1)
                        t.copy(first = t.first - 1, second = t.second + 2) -> t.copy(first = t.first - 1, t.second + 1)
                        t.copy(first = t.first - 1, second = t.second - 2) -> t.copy(first = t.first - 1, t.second - 1)
                        t.copy(first = t.first + 2, second = t.second + 2) -> t.copy(first = t.first + 1, t.second + 1)
                        t.copy(first = t.first + 2, second = t.second - 2) -> t.copy(first = t.first + 1, t.second - 1)
                        t.copy(first = t.first - 2, second = t.second + 2) -> t.copy(first = t.first - 1, t.second + 1)
                        t.copy(first = t.first - 2, second = t.second - 2) -> t.copy(first = t.first - 1, t.second - 1)
                        t.copy(first = t.first + 1) -> t
                        t.copy(first = t.first - 1) -> t
                        t.copy(second = t.second + 1) -> t
                        t.copy(second = t.second - 1) -> t
                        t.copy(first = t.first + 1, second = t.second + 1) -> t
                        t.copy(first = t.first + 1, second = t.second - 1) -> t
                        t.copy(first = t.first - 1, second = t.second + 1) -> t
                        t.copy(first = t.first - 1, second = t.second - 1) -> t
                        t -> t
                        else -> {
                            println("why: $index $t prev=$prev")
                            t
                        }
                    }
//                    println("$index t=$t newT=$newT prev=$prev")

                    knots[index] = newT
                }
                lastKnotSet.add(knots.last())
            }
        }
        return lastKnotSet
    }

    fun part1(input: List<String>): Int {
        return calculateTailPositions(getMoves(input)).count()
    }

    fun part2(input: List<String>): Int {
        return calculateTailPositions(getMoves(input), 9).count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    checkEquals(13, part1(testInput))
    checkEquals(1, part2(testInput))

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
