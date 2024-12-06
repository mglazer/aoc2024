object Day5 {
    data class PageOrdering(val ordering: Map<Int, Set<Int>>) {
        val inverse: Map<Int, Set<Int>>
        init {
            val tempInverse = mutableMapOf<Int, MutableSet<Int>>()
            for ((ordering, value) in ordering) {
                for (v in value) {
                    tempInverse.getOrPut(v) { mutableSetOf() }.add(ordering)
                }
            }

            inverse = tempInverse.toMap()
        }

        fun isCorrect(pages: List<Int>): Boolean {
            return pages == sort(pages)
        }

        fun sort(pages: List<Int>): List<Int> {
            return pages.sortedWith { a, b ->
                if (ordering[a]?.contains(b) == true) {
                    -1
                } else if (inverse[a]?.contains(b) == true) {
                    1
                } else {
                    0
                }
            }
        }
    }

    fun getPageOrdering(input: String): Pair<PageOrdering, List<List<Int>>> {
        val inputLines = input.lines()
        var isPages = false
        val pages = mutableListOf<List<Int>>()
        val ordering = mutableMapOf<Int, MutableSet<Int>>()
        for (line in inputLines) {
            if (line.isBlank()) {
                isPages = true
                continue
            }
            if (isPages) {
                pages.add(line.split(",").map { it.toInt() })
            } else {
                val parts = line.split("|").map { it.toInt() }
                ordering.getOrPut(parts[0]) { mutableSetOf() }.add(parts[1])
            }
        }

        return PageOrdering(ordering) to pages
    }

    fun part1(input: String): Int {
        val (pageOrdering, pages) = getPageOrdering(input)
        val correctlyOrdered = pages.filter { pageOrdering.isCorrect(it) }
        return correctlyOrdered.map { it[it.size / 2] }.sum()
    }

    fun part2(input: String): Int {
        val (pageOrdering, pages) = getPageOrdering(input)
        val inCorrectlyOrdered = pages.filterNot { pageOrdering.isCorrect(it) }
        return inCorrectlyOrdered.map { pageOrdering.sort(it)[it.size / 2] }.sum()
    }
    val INPUT = """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13

        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
    """.trimIndent()
}

fun main() {
    println("Part 1: ${Day5.part1(Utils.load("Day5.txt"))}")
    println("Part 2: ${Day5.part2(Utils.load("Day5.txt"))}")


}