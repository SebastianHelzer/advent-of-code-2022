
data class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: (Long) -> Boolean,
    val testDivisor: Int,
    val onTrueMonkeyIndex: Int,
    val onFalseMonkeyIndex: Int,
)

object OperationFunctionParser {
    fun parse(operation: String): Function1<Long, Long> {
        val (p0, command, p1) = operation.trim().split(' ')

        return { old ->
            val start = if(p0 == "old") {
                old
            } else {
                p0.toLong()
            }

            val end = if(p1 == "old") {
                old
            } else {
                p1.toLong()
            }

            when(command) {
                "*" -> start * end
                "+" -> start + end
                else -> error("unknown command: $command")
            }
        }
    }
}

object TestFunctionParser {
    fun parse(input: String): Pair<Int,Function1<Long, Boolean>> {
        val divisor = input.takeLastWhile { it.isDigit() }.toInt()
        return divisor to { value -> value % divisor == 0L }
    }
}

fun main() {


    fun parseInput(input: List<String>): List<Monkey> {
        return input.chunked(7).map {
            val items = it[1].filter { it.isWhitespace() || it.isDigit() }.split(" ").mapNotNull { it.toLongOrNull() }
            val operation = it[2].substringAfter('=')
            val test = it[3].substringAfter(':')
            val onTrueIndex = it[4].takeLastWhile { it.isDigit() }.toInt()
            val onFalseIndex = it[5].takeLastWhile { it.isDigit() }.toInt()

            val (divisor, testFunction) = TestFunctionParser.parse(test)

            Monkey(
                items.toMutableList(),
                OperationFunctionParser.parse(operation),
                testFunction,
                divisor,
                onTrueIndex,
                onFalseIndex,
            ).also {
                println(it)
                println(operation)
                println(test)
            }
        }
    }

    fun simulateRound(input: List<Monkey>, onInspect: (Int, Int, Long) -> Long) {
        input.forEachIndexed { index, monkey ->
            monkey.items.replaceAll {
                val newValue = monkey.operation(it)
                val storedWorry = onInspect(index, monkey.testDivisor, newValue)
                storedWorry
            }
            monkey.items.forEach {
                if(monkey.test(it)) {
                    input[monkey.onTrueMonkeyIndex].items.add(it)
                } else {
                    input[monkey.onFalseMonkeyIndex].items.add(it)
                }
            }
            monkey.items.clear()
        }
    }

    fun part1(input: List<String>): Int {
        val monkeys = parseInput(input)
        val inspectionCount = MutableList(monkeys.size) { 0 }

        repeat(20) {
            simulateRound(monkeys) { value, _, newValue -> inspectionCount[value]++; newValue / 3 }
        }
        val greatestTwo = inspectionCount.sorted().takeLast(2)
        val (nextToTop, top) = greatestTwo
        return top * nextToTop
    }

    fun part2(input: List<String>): Long {
        val monkeys = parseInput(input)
        val inspectionCount = MutableList(monkeys.size) { 0 }

        val roundsToPrint = (listOf(1,20) + (1000 until 10001 step 1000))

        val gcm = monkeys.map { it.testDivisor }.fold(1) { acc, i -> acc * i }
        println("gcm=$gcm")


        repeat(10000) {
            simulateRound(monkeys) { monkeyIndex, divisor, value ->
                inspectionCount[monkeyIndex]++
                if(value > gcm) { value % gcm } else value
            }

            if((it + 1) in roundsToPrint) {
                println("After round ${it + 1}")
                inspectionCount.forEachIndexed { index, i ->
                    println("Monkey $index inspected items $i times")
                }
            }

        }
        val greatestTwo = inspectionCount.sorted().takeLast(2)
        val (nextToTop, top) = greatestTwo
        println("top=$top nextToTop=$nextToTop")
        return top.toLong() * nextToTop.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    checkEquals(10605, part1(testInput))
    checkEquals(2713310158, part2(testInput))

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
