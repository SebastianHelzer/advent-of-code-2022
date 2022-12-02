enum class HandShape {
    Rock, Paper, Scissors;
    fun next(): HandShape = when(this) {
        Rock -> Paper
        Paper -> Scissors
        Scissors -> Rock
    }
}

fun main() {

    fun Char.toHandShape(): HandShape {
        return when(this) {
            'A', 'X' -> HandShape.Rock
            'B', 'Y' -> HandShape.Paper
            'C', 'Z' -> HandShape.Scissors
            else -> throw Exception("unable to parse hand shape")
        }
    }

    fun getHandShapePair(input: String): Pair<HandShape, HandShape> = input.filterNot { it.isWhitespace() }.let {
        it.first().toHandShape() to it.last().toHandShape()
    }

    fun List<Pair<HandShape, HandShape>>.score(): List<Int> {
        return map { (opponent, player) ->
            val outcome = if (opponent == player) 3 else if (opponent.next() == player) 6 else 0
            val shapeScore = player.ordinal + 1
            outcome + shapeScore
        }
    }

    fun List<Pair<HandShape, HandShape>>.cheat(): List<Pair<HandShape, HandShape>> {
        return map { (opponent, outcome) ->
            val player = when(outcome) {
                HandShape.Rock -> opponent.next().next()
                HandShape.Paper -> opponent
                HandShape.Scissors -> opponent.next()
            }
            opponent to player
        }
    }

    fun part1(input: List<String>): Int {
        val handShapes = input.map { getHandShapePair(it) }
        val scores = handShapes.score()
        return scores.sum()
    }

    fun part2(input: List<String>): Int {
        val handShapes = input.map { getHandShapePair(it) }
        val scores = handShapes.cheat().score()
        return scores.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    check(part1(input) == 11475)
    println(part2(input))
}
