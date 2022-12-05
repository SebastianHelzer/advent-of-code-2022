
fun main() {
    val day = "05"

    fun List<String>.toState(): List<String> {
        val numCols = last().split(" ").filterNot { it.isBlank() }.size
        val mutableList = MutableList(numCols) { "" }
        dropLast(1).forEach {
            it.chunked(4)
                .mapIndexed { index, s -> index to s[1] }
                .forEach { (i, v) -> if (v.isLetter()) mutableList[i] = v + mutableList[i] }
        }
        return mutableList
    }

    fun List<String>.toSteps(): List<Triple<Int, Int, Int>> {
        return map {
            val (a, b, c) = it.split(' ').mapNotNull { it.toIntOrNull() }
            Triple(a, b - 1, c - 1)
        }
    }

    fun parseInput(input: List<String>): Pair<List<String>, List<Triple<Int, Int, Int>>> {
        val blankIndex = input.indexOfFirst { it.isBlank() }
        val state = input.subList(0, blankIndex).toState()
        val steps = input.subList(blankIndex + 1, input.size).toSteps()
        return state to steps
    }

    fun List<String>.applySteps(steps: List<Triple<Int, Int, Int>>, withReversal: Boolean = true): List<String> {
        val mutableList = this.toMutableList()
        steps.forEach { (count, fromIndex, toIndex) ->
            val (moveable, leftover) = mutableList[fromIndex].let { it.takeLast(count) to it.take(it.length - count) }
            mutableList[fromIndex] = leftover
            mutableList[toIndex] += if(withReversal) moveable.reversed() else moveable
        }
        return mutableList
    }

    fun part1(input: List<String>): String {
        val (initialState, steps) = parseInput(input)
        val endState = initialState.applySteps(steps)
        return endState.map { it.last() }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val (initialState, steps) = parseInput(input)
        val endState = initialState.applySteps(steps, false)
        return endState.map { it.last() }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    checkEquals("CMZ", part1(testInput))
    checkEquals("MCD", part2(testInput))

    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}
