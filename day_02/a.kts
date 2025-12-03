import java.io.File

val exampleInput =
    """11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"""

val actualInput = File("../input/02.txt").bufferedReader().readText().trim()

fun invalid(id: Long): Boolean {
    val s = id.toString()
    if (s.length % 2 != 0) return false
    val half = s.length / 2
    return s.take(half) == s.substring(half)
}

fun solution(input: String = exampleInput): Long {
    return input.trimEnd().splitToSequence(',')
        .map { it.split("-").let { it[0].toLong()..it[1].toLong() } }
        .flatMap { range -> range.filter { invalid(it) } }
        .sum()
}

println(solution(actualInput))
