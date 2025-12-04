import java.io.File

val exampleInput =
    """987654321111111
811111111111119
234234234234278
818181911112111
"""

val actualInput = File("../input/03.txt").bufferedReader().readText().trim()


fun solution(input: String = exampleInput): Long {
    return input.trim().lineSequence()
        .map { line ->
            val digits = line.toCharArray().map { value -> value.digitToInt() }

            // This is obviously not so efficient. Probably a nice recursive solution would have been better; but
            // this idea is what I had lying around from the first part, and it works just fine =).
            val indices = (11 downTo 0).fold(mutableListOf<Int>(), { acc, j ->
                // get the maximum value of the sublist (i.e. the bank) between the last "maximum index" and increasingly
                // accept more of the last 12 digits as acceptable values (i.e. the first value must be between index
                // 0 and n-11, the second between the index of the first value and n-10 and so forth.
                val i = if (acc.isEmpty()) 0 else acc.last() + 1
                val max = digits.dropLast(j).drop(i).withIndex().maxBy { it.value }

                acc.addLast(i + max.index)
                acc
            })

            indices.map { digits[it].toLong() }.reduce { acc, i -> acc * 10 + i }
        }.sum()
}

println(solution(actualInput))
