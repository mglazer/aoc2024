object Day7 {

    data class InputLine(val result: Long, val inputs: List<Long>) {
        companion object {
            operator fun invoke(line: String): InputLine {
                val leftRight = line.split(":")
                val result = leftRight[0].toLong()
                val rightNums = leftRight[1].trim().split(" ").map { it.toLong() }

                return InputLine(result, rightNums)
            }
        }

        fun isValid(): Boolean {
            return combinations(inputs).any { it == result }
        }

        fun isValidConcat(): Boolean {
            return combinationsConcat(inputs).any { it == result }
        }

        fun combinations(input: List<Long>): List<Long> {
            if (input.isEmpty()) {
                return emptyList()
            }
            val left = input.first()
            val rest = input.drop(1)

            if (rest.isEmpty()) {
                return listOf(left)
            }

            val right = rest.first()
            val moreRest = rest.drop(1)

            val addCombinations = buildList {
                add(left + right)
                addAll(moreRest)
            }
            val mulCombinations = buildList {
                add(left * right)
                addAll(moreRest)
            }

            return buildList {
                addAll(combinations(addCombinations))
                addAll(combinations(mulCombinations))
            }
        }

        fun combinationsConcat(input: List<Long>): List<Long> {
            if (input.isEmpty()) {
                return emptyList()
            }
            val left = input.first()
            val rest = input.drop(1)

            if (rest.isEmpty()) {
                return listOf(left)
            }

            val right = rest.first()
            val moreRest = rest.drop(1)

            val addCombinations = buildList {
                add(left + right)
                addAll(moreRest)
            }
            val mulCombinations = buildList {
                add(left * right)
                addAll(moreRest)
            }
            val concatCombinations = buildList {
                add("${left}${right}".toLong())
                addAll(moreRest)
            }

            return buildList {
                addAll(combinationsConcat(addCombinations))
                addAll(combinationsConcat(mulCombinations))
                addAll(combinationsConcat(concatCombinations))
            }
        }
    }

    fun part1(input: String): Long {
        val lines = input.lines().map(InputLine::invoke)

        val validLines = lines.filter { it.isValid() }

        return validLines.sumOf { line -> line.result }
    }

    fun part2(input: String): Long {
        val lines = input.lines().map(InputLine::invoke)

        val validLines = lines.filter { it.isValidConcat() }

        return validLines.sumOf { line -> line.result }
    }

    val INPUT = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.trimIndent()

    val SMALLINPUT = """
        7290: 6 8 6 15
    """.trimIndent()

}

fun main() {
    println(Day7.InputLine(7290, listOf(6, 86, 15)).isValid())
    println("Part 1: ${Day7.part1(Utils.load("Day7.txt"))}")
    println("Part 2: ${Day7.part2(Utils.load("Day7.txt"))}")

}