object Day1 {

    private fun parse(input: String): Pair<List<Int>, List<Int>> {
        return input.lines().filter(String::isNotBlank).map {
            val parts = it.split("\\s+".toRegex())
            Pair(parts[0].toInt(), parts[1].toInt())
        }.fold(Pair(emptyList(), emptyList()), { acc, pair ->
            Pair(acc.first + pair.first, acc.second + pair.second)
        })
    }

    fun part1(input: String): Int {
        val parsed = parse(input)
        val left = parsed.first.sorted()
        val right = parsed.second.sorted()
        return left.zip(right).map { (l, r) -> Math.abs(l-r) }.sum()
    }

    fun count(input: List<Int>): Map<Int, Int> {
        return input.groupingBy { it }.eachCount()
    }

    fun part2(input: String): Int {
        val parsed = parse(input)
        val rightCount = count(parsed.second)
        var acc = 0
        for (v in parsed.first) {
            acc += v * rightCount.getOrDefault(v, 0)
        }

        return acc
    }

    val TEST_INPUT = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.trimIndent()

}

fun main() {

    println("Part 1: ${Day1.part1(Utils.load("Day1.txt"))}")
    println("Part 2: ${Day1.part2(Utils.load("Day1.txt"))}")

}