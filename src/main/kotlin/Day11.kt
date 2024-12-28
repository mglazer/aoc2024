object Day11 {
    /*
    If the stone is engraved with the number 0, it is replaced by a stone engraved with the number 1.
If the stone is engraved with a number that has an even number of digits, it is replaced by two stones. The left half of the digits are engraved on the new left stone, and the right half of the digits are engraved on the new right stone. (The new numbers don't keep extra leading zeroes: 1000 would become stones 10 and 0.)
If none of the other rules apply, the stone is replaced by a new stone; the old stone's number multiplied by 2024 is engraved on the new stone.
     */

    fun split(digit: Long): List<Long> {
        val strDigit = digit.toString()
        val left = strDigit.substring(0, strDigit.length / 2).toLong()
        val right = strDigit.substring(strDigit.length / 2).toLong()
        return listOf(left, right)
    }


    fun part1(input: String, rounds: Int): Int {
        val initial = input.split(" ").map { it.toLong() }

        fun play(digits: List<Long>): List<Long> {
            return digits.flatMap { digit ->
                when {
                    digit == 0L -> listOf(1)
                    (digit.toString().length % 2) == 0 -> split(digit)
                    else -> listOf(2024 * digit)
                }
            }
        }

        var retVal = initial
        for (i in 1..rounds) {
            retVal = play(retVal)
        }

        return retVal.size
    }

    data class Stones(val zeroCount: Long, val stones: Map<Long, Long>) {
        fun play(): Stones {
            var zeroCount = 0L
            val stones = mutableMapOf<Long, Long>()

            if (this.zeroCount > 0L) {
                stones[1L] = this.zeroCount.toLong()
            }
            this.stones.forEach { (k, v) ->
                when {
                    (k.toString().length % 2) == 0 -> {
                        val parts = split(k)
                        parts.forEach { part ->
                            if (part == 0L) {
                                zeroCount += v
                            } else {
                                stones[part] = stones.getOrDefault(part, 0L) + v
                            }
                        }
                    }
                    else -> stones[k * 2024] = stones.getOrDefault(k * 2024, 0L) + v
                }
            }

            return Stones(zeroCount, stones)
        }

        fun count(): Long {
            return zeroCount + stones.map { it.value }.sum()
        }
    }

    fun part2(input: String, rounds: Int): Long {
        val initial = input.split(" ").map { it.toLong() }

        var stones = Stones(initial.count { it == 0L }.toLong(), initial.filter { it != 0L }.associateWith { 1L })

        for (i in 1..rounds) {
            stones = stones.play()
        }

        return stones.count()
    }

    val INPUT = "125 17"
    val REAL_INPUT = "872027 227 18 9760 0 4 67716 9245696"
}

fun main() {
    println("Part 1: ${Day11.part1(Day11.REAL_INPUT, 25)}")
    println("Part 2: ${Day11.part2(Day11.REAL_INPUT, 75)}")

}