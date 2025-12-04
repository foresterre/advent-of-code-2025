import java.io.File

val exampleInput =
    """987654321111111
811111111111119
234234234234278
818181911112111
"""

val actualInput = File("../input/03.txt").bufferedReader().readText().trim()

fun solution(input: String = exampleInput): Int {
    return input.trim().lineSequence()
        .map { line ->
            val digits = line.toCharArray().map { value -> value.digitToInt() }
            val first = digits.dropLast(1).withIndex().maxBy { it.value }
            val second = digits.drop(first.index + 1).max()

            first.value * 10 + second
        }.sum()
}

println(solution(actualInput))
