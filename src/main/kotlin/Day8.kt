object Day8 {
    fun <T> List<T>.allPairings(): List<Pair<T, T>> {
        val pairings = mutableListOf<Pair<T, T>>()
        for (i in 0 until size - 1) {
            for (j in i+1 until size) {
                pairings.add(this[i] to this[j])
            }
        }
        return pairings
    }

    data class Antenna(val pos: Pair<Int, Int>, val c: Char)

    data class Map(val coords: Array<CharArray>) {
        fun inBounds(pos: Pair<Int, Int>): Boolean {
            return pos.first >= 0 && pos.second >= 0 && pos.second < coords.size && pos.first < coords.first().size
        }

        fun withAntinodes(antinodes: Set<Pair<Int, Int>>): Map {
            val copy = coords.map { it.copyOf() }.toTypedArray()
            for (antinode in antinodes) {
                copy[antinode.second][antinode.first] = '#'
            }
            return Map(copy)
        }

        override fun toString(): String {
            return coords.map { String(it) }.joinToString(separator = "\n")
        }

        fun antinodeLocations(): Set<Pair<Int, Int>> {
            val uniqueChars = coords.mapIndexed { rowIdx, row ->
                row.mapIndexed { colIdx, char ->
                    char to (colIdx to rowIdx) }
                    .filter { it.first != '.' }
            }.flatten().groupBy({ it.first }, { it.second })

            return uniqueChars.entries.map { (c, list) ->
                c to list.allPairings()
            }.flatMap { (c, pairings) ->
                pairings.flatMap { pairing ->
                    val pos1 = pairing.first
                    val pos2 = pairing.second

                    val dx = pos2.first - pos1.first
                    val dy = pos2.second - pos1.second

                    val antinode1 = (pos1.first - dx) to (pos1.second - dy)
                    val antinode2 = (pos2.first + dx) to (pos2.second + dy)

                    listOf(
                        antinode1,
                        antinode2,
                    )
                }.filter { inBounds(it) }
            }.toSet()
        }

        fun antinodeLocationsPart2(): Set<Pair<Int, Int>> {
            val uniqueChars = coords.mapIndexed { rowIdx, row ->
                row.mapIndexed { colIdx, char ->
                    char to (colIdx to rowIdx) }
                    .filter { it.first != '.' }
            }.flatten().groupBy({ it.first }, { it.second })

            return uniqueChars.entries.map { (c, list) ->
                c to list.allPairings()
            }.flatMap { (c, pairings) ->
                pairings.flatMap { pairing ->
                    val pos1 = pairing.first
                    val pos2 = pairing.second

                    val dx = pos2.first - pos1.first
                    val dy = pos2.second - pos1.second

                    val result = mutableListOf<Pair<Int, Int>>()
                    var antinode = (pos1.first - dx) to (pos1.second - dy)
                    while (inBounds(antinode)) {
                        result.add(antinode)
                        antinode = (antinode.first - dx) to (antinode.second - dy)
                    }

                    antinode = (pos1.first + dx) to (pos1.second + dy)
                    while (inBounds(antinode)) {
                        result.add(antinode)
                        antinode = (antinode.first + dx) to (antinode.second + dy)
                    }

                    antinode = (pos2.first - dx) to (pos2.second - dy)
                    while (inBounds(antinode)) {
                        result.add(antinode)
                        antinode = (antinode.first - dx) to (antinode.second - dy)
                    }

                    antinode = (pos2.first + dx) to (pos2.second + dy)
                    while (inBounds(antinode)) {
                        result.add(antinode)
                        antinode = (antinode.first + dx) to (antinode.second + dy)
                    }

                    result
                }
            }.toSet()
        }

    }

    fun part1(input: String): Set<Pair<Int, Int>> {
        val board = Map(input.lines().map(String::trim).map(String::toCharArray).toTypedArray())

        val locations = board.antinodeLocations()

        return locations
    }

    fun part2(input: String): Set<Pair<Int, Int>> {
        val board = Map(input.lines().map(String::trim).map(String::toCharArray).toTypedArray())

        val locations = board.antinodeLocationsPart2()

        return locations
    }

    val INPUT = """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.trimIndent()

}

fun main() {
    println("Part 1: ${Day8.part1(Utils.load("Day8.txt")).size}")
    println("Part 2: ${Day8.part2(Utils.load("Day8.txt")).size}")

}