
fun main() {

    fun getCharPriority(char: Char): Int {
        return if(char in 'A'..'Z') char - 'A' + 27
        else char - 'a' + 1
    }

    fun part1(input: List<String>): Int {
        fun getCharOfLine(line: String): Char {
            require(line.length % 2 == 0) { "line length must be even to split in half was ${line.length} for $line" }
            val (first, second) = line.chunked(line.length/2)
            val char = first.toCharArray().toSet().intersect(second.toCharArray().toSet())
            check(char.size == 1) { "Intersection should be size 1. Is size ${char.size}" }
            return char.first()
        }

        return input
            .map { getCharOfLine(it) }
            .sumOf { getCharPriority(it) }
    }

    fun part2(input: List<String>): Int {
        fun getCharOfLines(lines: List<String>): Char {
            require(lines.size == 3) { "lines size must be 3 was ${lines.size}" }
            val (first, second, third) = lines.map { it.toCharArray().toSet() }
            val char = first.intersect(second).intersect(third)
            check(char.size == 1) { "Intersection should be size 1. Is size ${char.size}" }
            return char.first()
        }

        return input
            .chunked(3)
            .map { getCharOfLines(it) }
            .sumOf { getCharPriority(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    checkEquals(157, part1(testInput))
    checkEquals(70, part2(testInput))

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
