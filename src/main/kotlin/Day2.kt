import kotlin.math.absoluteValue
import kotlin.math.sign

object Day2 {

    fun parse(input: String): List<List<Int>> {
        return input.lines().filterNot(String::isBlank)
            .map { it.split("\\s+".toRegex() ).map(String::toInt) }
    }

    fun isSafe(input: List<Int>): Int {
        val hasChangedSigns =
            input.zipWithNext().map { (it.first - it.second).sign }.zipWithNext().any { it.first == 0 || it.second == 0 || it.first != it.second }
        val hasLargeDiffs = input.zipWithNext().any { (it.first - it.second).absoluteValue > 3 }
        if (hasChangedSigns || hasLargeDiffs) {
            return 0
        }
        return 1
    }

    fun isPart2Safe(input: List<Int>): Boolean {
        val allInputs = buildList {
            add(input)
            addAll(removeOne(input))
        }

        for (i in allInputs) {
            val hasChangedSigns =
                i.zipWithNext().map { (it.first - it.second).sign }.zipWithNext().any { it.first == 0 || it.second == 0 || it.first != it.second }
            val hasLargeDiffsInRange = i.zipWithNext().all { (it.first - it.second).absoluteValue in 1..3 }

//            println("${i} -> ${hasChangedSigns} -> ${hasLargeDiffsInRange}")

            if (!hasChangedSigns && hasLargeDiffsInRange) {
                return true
            }
        }
        return false
    }

    fun removeOne(input: List<Int>): List<List<Int>> {
        return (0 until input.size).map { input.slice(0 until it) + input.slice(it+1 until input.size) }
    }

    fun part1(input: String): Int {
        val parsed = parse(input)

        return parsed.map(this::isSafe).sum()
    }

    fun part2(input: String): Int {
        val parsed = parse(input)
        return parsed.map(this::isPart2Safe).count { it }
    }



    val INPUT = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent()
}

fun main() {
    println("Part 1: ${Day2.part1(Utils.load("Day2.txt"))}")
    println("Part 2: ${Day2.part2(Utils.load("Day2.txt"))}")

}