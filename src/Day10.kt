
fun main() {

    fun processOps(ops: List<Int?>, cycle: (Int, Int) -> Unit) {
        var cycleNumber = 1
        var x = 1

        ops.forEach {
            if(it == null) {
                cycle(cycleNumber++, x)
            } else {
                cycle(cycleNumber++, x)
                cycle(cycleNumber++, x)
                x += it
            }
        }
    }

    fun part1(input: List<String>): Int {
        val ops = input.map { ("$it noop").split(" ")[1].toIntOrNull()}

        var accumulatedSignalStrength = 0

        fun checkAndAccumulate(cycleNumber: Int, x: Int) {
            if ((cycleNumber + 20) % 40 == 0) {
                accumulatedSignalStrength += cycleNumber * x
            }
        }

        processOps(ops, ::checkAndAccumulate)

        return accumulatedSignalStrength
    }

    fun part2(input: List<String>) {
        val ops = input.map { it.split(" ").getOrNull(1)?.toIntOrNull()}

        fun printPixel(cycleNumber: Int, x: Int) {
            val pixelNumber = ((cycleNumber - 1) % 40)
            print(if(pixelNumber in (x-1)..(x+1)) '#' else '.')
            if (pixelNumber == 39) { println() }
        }

        processOps(ops, ::printPixel)
    }

    val testInput = readInput("Day10_test")
    checkEquals(13140, part1(testInput))
    part2(testInput)

    val input = readInput("Day10")
    println(part1(input))
    part2(input)
}
