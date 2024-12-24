import java.nio.file.Files
import java.nio.file.Paths

object Day9 {

    fun MutableList<Int>.defragment(): MutableList<Int> {
        fun getEnd(e: Int): Int {
            for (i in (e downTo 0)) {
                if (this[i] != -1) {
                    return i
                }
            }
            return -1
        }
        var end = getEnd(this.size - 1)

        for (i in 0..<this.size) {
            if (this[i] == -1) {
                this[i] = this[end]
                this[end] = -1
                end = getEnd(end)
            }

            if (i >= end) {
                break
            }
        }
        return this
    }

    fun MutableList<Int>.defragment2(): MutableList<Int> {
        fun findNextBlock(start: Int): IntProgression? {
            var i = start
            while (i >= 0) {
                if (this[i] != -1) {
                    val pinnedVal = this[i]
                    var j = i
                    while (j >= 0 && this[j] == pinnedVal) {
                        j--
                    }
                    if (j < 0) {
                        return null
                    }
                    return (j+1..i)
                } else {
                    i--
                }
            }
            return null
        }
        var nextStart = this.size - 1
        while (true) {
            val block = findNextBlock(nextStart) ?: break
            nextStart = block.first - 1

            var contiguousBlock: IntProgression? = null
            var blockSearchStart = 0
            while (true) {
                if (blockSearchStart >= block.first) {
                    break
                }
                var startBlock = this.subList(blockSearchStart, block.first).indexOfFirst { it == -1 }
                if (startBlock == -1) {
                    break
                }
                startBlock += blockSearchStart

                var endBlock = this.subList(startBlock, block.last).indexOfFirst { it != -1 }
                if (endBlock == -1) {
                    break
                }
                endBlock += startBlock

                if (endBlock - startBlock >= block.count()) {
                    contiguousBlock = (startBlock..endBlock)
                    break
                }

                blockSearchStart = endBlock + 1
            }

            if (contiguousBlock != null && contiguousBlock.first >= block.first) {
                break
            }

            if (contiguousBlock != null) {
                block.zip(contiguousBlock).forEach { (bIdx, cIdx) ->
                    this[cIdx] = this[bIdx]
                    this[bIdx] = -1
                }
            }
        }

        return this
    }

    fun decompress(input: String): MutableList<Int> {
        val decompressed = mutableListOf<Int>()
        var counter = 0
        val line = input.lines().first()
        for (i in (0 ..< input.length step 2)) {
            decompressed.addAll(generateSequence{ counter }.take(line[i].digitToInt()))
            if (i+1 < line.length) {
                decompressed.addAll(generateSequence { -1 }.take(line[i+1].digitToInt()))
            }
            counter++
        }

        return decompressed
    }

    fun part1(input: String): Long {
        val decompressed = decompress(input)
        val defragment = decompressed.defragment()

        return defragment.filterNot { it == -1 }.mapIndexed { idx, v -> (v * idx).toLong() }.sum()
    }

    fun part2(input: String): Long {
        val decompressed = decompress(input)
        val defragment = decompressed.defragment2()

        return defragment.mapIndexed { idx, v ->
            if (v == -1) {
                0
            } else {
                (v * idx).toLong()
            }
        }.sum()
    }

    val INPUT = "2333133121414131402"

}

fun main() {
    println("Part 1: ${Day9.part1(Utils.load("Day9.txt"))}")

    // yes: 6467290479134
    // no:  6467290913294
    // no:  6227018846942
    println("Part 2: ${Day9.part2(Utils.load("Day9.txt"))}")

}