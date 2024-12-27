object Day10 {

    fun part1(input: String): Long {
        val topoMap = input.lines().map { line -> line.toCharArray().map { it.digitToInt() } }

        var trailCount = 0
        topoMap.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, point ->
                if (point == 0) {
                    trailCount += collectTrails(colIdx to rowIdx, topoMap).map { it.last() }.toSet().size
                }
            }
        }

        return trailCount.toLong()
    }

    fun part2(input: String): Long {
        val topoMap = input.lines().map { line -> line.toCharArray().map { it.digitToInt() } }

        val allTrails = mutableListOf<List<Pair<Int, Int>>>()
        topoMap.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, point ->
                if (point == 0) {
                    allTrails.addAll(collectTrails(colIdx to rowIdx, topoMap))
                }
            }
        }

        return allTrails.size.toLong()
    }

    private fun collectTrails(pos: Pair<Int, Int>, topoMap: List<List<Int>>): Collection<List<Pair<Int, Int>>> {
        val directions = listOf(
            Pair(-1, 0),
            Pair(1, 0),
            Pair(0, -1),
            Pair(0, 1),
        )

        fun inBounds(pos: Pair<Int, Int>): Boolean {
            return pos.first >= 0 && pos.second >= 0 && pos.first < topoMap.first().size && pos.second < topoMap.size
        }

        fun collectTrailsInner(pos: Pair<Int, Int>, path: List<Pair<Int, Int>>): Collection<List<Pair<Int, Int>>> {
            if (topoMap[pos.second][pos.first] == 9) {
                return listOf(path)
            }

            val allTrails = mutableListOf<List<Pair<Int, Int>>>()
            for (direction in directions) {
                val nextPos = pos.first + direction.first to pos.second + direction.second
                if (inBounds(nextPos) && topoMap[nextPos.second][nextPos.first] - topoMap[pos.second][pos.first] == 1) {
                    allTrails.addAll(collectTrailsInner(nextPos, path + nextPos))
                }
            }

            return allTrails
        }

        return collectTrailsInner(pos, listOf(pos))
    }

    val INPUT = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent()
}

fun main() {
    println("Part 1: ${Day10.part1(Utils.load("Day10.txt"))}")
    println("Part 2: ${Day10.part2(Utils.load("Day10.txt"))}")

}