object Day3 {

    fun part1(input: String): Int {
        val mulRegex = Regex("mul\\((?<num1>\\d{1,3}),(?<num2>\\d{1,3})\\)")

        return mulRegex
            .findAll(input)
            .map { it.groups["num1"]!!.value.toInt() * it.groups["num2"]!!.value.toInt() }
            .sum()
    }

    fun part2(input: String): Int {
        val mulRegex = Regex("mul\\((?<num1>\\d{1,3}),(?<num2>\\d{1,3})\\)|do\\(\\)|don't\\(\\)")

        var include = true
        return mulRegex
            .findAll(input)
            .map {
                if (it.value == "do()") {
                    include = true
                    0
                } else if (it.value == "don't()" ) {
                    include = false
                    0
                } else if (include) {
                    it.groups["num1"]!!.value.toInt() * it.groups["num2"]!!.value.toInt()
                } else {
                    0
                }
            }
            .sum()
    }

    val INPUT = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
    val INPUT2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
}

fun main() {
    println("Part 1: ${Day3.part1(Utils.load("day3.txt"))}")
    println("Part 2: ${Day3.part2(Utils.load("day3.txt"))}")

}