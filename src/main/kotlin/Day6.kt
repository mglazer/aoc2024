object Day6 {
    data class Board(val grid: Array<CharArray>, val overlay: Set<Pair<Int, Int>> = setOf()) {
        companion object {
            operator fun invoke(input: String): Board {
                return Board(input.lines().map(String::trim).map(String::toCharArray).toTypedArray())
            }
        }

        fun getCursor(): Cursor {
            for (row in grid.indices) {
                for (col in grid[row].indices) {
                    if (grid[row][col] == '^') {
                        return Cursor(row, col, -1 to 0)
                    }
                }
            }
            throw IllegalArgumentException("Invalid input")
        }

        fun move(cursor: Cursor): Cursor {
            val next = cursor.move()
            if (!inbounds(next)) {
                return next
            }
            if (grid[next.x][next.y] == '#') {
                return move(cursor.rotate())
            }
            if (overlay.contains(next.x to next.y)) {
                return move(cursor.rotate())
            }
            return next
        }

        fun inbounds(cursor: Cursor): Boolean {
            return cursor.x >= 0 && cursor.y >= 0 && cursor.x < grid.size && cursor.y < grid[0].size
        }

        fun boundaries(): Set<Pair<Int, Int>> {
            return grid.mapIndexed { y, row -> row.mapIndexed { x, char -> Pair(char, Pair(x, y)) } }.flatten().filter { it.first == '#' }.map { it.second }.toSet()
        }
    }

    data class Cursor(val x: Int, val y: Int, val movement: Pair<Int, Int>) {
        private val directions = listOf(
            -1 to 0,
            0 to 1,
            1 to 0,
            0 to -1,
        )
        fun move(): Cursor {
            return Cursor(x + movement.first, y + movement.second, movement)
        }

        fun rotate(): Cursor {
            return Cursor(x, y, directions[(directions.indexOf(movement) + 1) % directions.size])
        }

        fun pos(): Pair<Int, Int> {
            return x to y
        }
    }


    fun part1(input: String): Int {
        val board = Board(input)


        var cursor = board.getCursor()
        val positions = mutableSetOf<Pair<Int, Int>>()
        positions.add(cursor.x to cursor.y)
        while (board.inbounds(cursor)) {
            positions.add(cursor.x to cursor.y)
            cursor = board.move(cursor)
        }

        return positions.size
    }

    private fun isLoop(board: Board, cursor: Cursor): Boolean {
        val visited = mutableSetOf<Cursor>()
        var movingCursor = cursor
        while (board.inbounds(movingCursor)) {
            if (!visited.add(movingCursor)) {
                return true
            }
            movingCursor = board.move(movingCursor)
        }
        return false
    }

    fun part2(input: String): Int {
        val board = Board(input)

        val startCursor = board.getCursor()
        var cursor = board.getCursor()
        val positions = mutableSetOf<Pair<Int, Int>>()
        val possibleObstructions = mutableSetOf<Pair<Int, Int>>()
        positions.add(cursor.x to cursor.y)
        while (board.inbounds(cursor)) {
            positions.add(cursor.x to cursor.y)
            cursor = board.move(cursor)
        }

        positions.remove(startCursor.move().pos())
        positions.forEach { pos ->
            if (isLoop(board.copy(overlay = setOf(pos)), startCursor)) {
                possibleObstructions.add(pos)
            }
        }

        return possibleObstructions.size
    }

    val INPUT = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent()

}

fun main() {
    println("Part 1: ${Day6.part1(Utils.load("Day6.txt"))}")
    println("Part 2: ${Day6.part2(Utils.load("Day6.txt"))}")
}