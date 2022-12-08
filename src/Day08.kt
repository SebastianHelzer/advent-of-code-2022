
fun main() {
    fun getTreeMap(input: List<String>): Map<Pair<Int, Int>, Int> {
        val heights = input.map { line -> line.toCharArray().map { it.digitToInt() } }

        return heights.flatMapIndexed { row, line ->
            line.mapIndexed { col, value ->
                (col to row) to value
            }
        }.toMap()
    }

    fun part1(input: List<String>): Int {
        val rows = input.size
        val cols = input.first().length
        val treeMap: Map<Pair<Int, Int>, Int> = getTreeMap(input)

        return treeMap.size - treeMap.count {
            val (x, y) = it.key
            val height = it.value
            val upIndices = 0 until y
            val leftIndices = 0 until x
            val rightIndices = (x + 1) until cols
            val downIndices = (y + 1) until rows

            upIndices.any { (treeMap[x to it] ?: 0) >= height } &&
            leftIndices.any { (treeMap[it to y] ?: 0) >= height } &&
            rightIndices.any { (treeMap[it to y] ?: 0) >= height } &&
            downIndices.any { (treeMap[x to it] ?: 0) >= height }
        }
    }

    fun part2(input: List<String>): Int {
        val rows = input.size
        val cols = input.first().length
        val treeMap: Map<Pair<Int, Int>, Int> = getTreeMap(input)

        return treeMap.maxOf {
            val (x, y) = it.key
            val height = it.value
            val upIndices = 0 until y
            val leftIndices = 0 until x
            val rightIndices = (x + 1) until cols
            val downIndices = (y + 1) until rows

            val up = upIndices.indexOfLast { (treeMap[x to it] ?: 0) >= height }.let { if (it == -1) { y } else { y - it } }
            val left = leftIndices.indexOfLast { (treeMap[it to y] ?: 0) >= height }.let { if (it == -1) { x } else { x - it } }
            val right = rightIndices.indexOfFirst { (treeMap[it to y] ?: 0) >= height }.let { if(it == -1) { (cols - 1) - x } else {it + 1} }
            val down = downIndices.indexOfFirst { (treeMap[x to it] ?: 0) >= height }.let { if(it == -1) { (rows - 1) - y } else {it + 1}}
            up * left * right * down
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    checkEquals(21, part1(testInput))
    checkEquals(8, part2(testInput))

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
