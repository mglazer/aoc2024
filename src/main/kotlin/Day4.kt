object Day4 {
    val DIRECTIONS = listOf(
        listOf(Pair(0,0), Pair(1, 0), Pair(2, 0), Pair(3, 0)),
        listOf(Pair(0,0), Pair(-1, 0), Pair(-2, 0), Pair(-3, 0)),
        listOf(Pair(0,0), Pair(0, 1), Pair(0, 2), Pair(0, 3)),
        listOf(Pair(0,0), Pair(0, -1), Pair(0, -2), Pair(0, -3)),
        listOf(Pair(0,0), Pair(1, 1), Pair(2, 2), Pair(3, 3)),
        listOf(Pair(0,0), Pair(1, -1), Pair(2, -2), Pair(3, -3)),
        listOf(Pair(0,0), Pair(-1, 1), Pair(-2, 2), Pair(-3, 3)),
        listOf(Pair(0,0), Pair(-1, -1), Pair(-2, -2), Pair(-3, -3)),
    )

    fun extract(grid: Array<CharArray>, x: Int, y: Int, directions: List<Pair<Int, Int>>): String? {
        val str = StringBuilder()
        for (direction in directions) {
            if (x + direction.first >= 0 && y + direction.second >= 0 && x + direction.first < grid.size && y + direction.second < grid.size) {
                str.append(grid[x + direction.first][y + direction.second])
            } else {
                return null
            }
        }

        return str.toString()
    }


    fun isXmas(grid: Array<CharArray>, x: Int, y: Int): Int {
        return DIRECTIONS.mapNotNull { extract(grid, x, y, it) }.count { it == "XMAS" }
    }

    fun isMas(input: String): Boolean {
        return input == "MAS" || input == "SAM"
    }

    fun isCrossMas(grid: Array<CharArray>, x: Int, y: Int): Int {
        val crossOne = extract(grid, x, y, listOf(Pair(-1, -1), Pair(0, 0), Pair(1, 1)))
        val crossTwo = extract(grid, x, y, listOf(Pair(-1, 1), Pair(0, 0), Pair(1, -1)))

        if (crossOne != null && crossTwo != null && isMas(crossOne) && isMas(crossTwo)) {
            return 1
        }
        return 0
    }

    fun part1(input: String): Int {
        val grid = input.lines().map(String::trim).map { it.toCharArray() }.toTypedArray()

        return grid.mapIndexed { y, chars -> chars.mapIndexed { x, c -> isXmas(grid, x, y) }.sum() }.sum()
    }

    fun part2(input: String): Int {
        val grid = input.lines().map(kotlin.String::trim).map { it.toCharArray() }.toTypedArray()


        return grid.mapIndexed { y, chars -> chars.mapIndexed { x, c -> isCrossMas(grid, x, y) }.sum() }.sum()
    }

    val INPUT = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent()
}

fun main() {
    println("Part 1: ${Day4.part1(Utils.load("Day4.txt"))}")
    println("Part 2: ${Day4.part2(Utils.load("Day4.txt"))}")

}