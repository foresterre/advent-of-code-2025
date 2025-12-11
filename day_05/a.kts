import java.io.File

val exampleInput =
    """3-5
10-14
16-20
12-18

1
5
8
11
17
32"""

val actualInput = File("../input/05.txt").bufferedReader().readText().trim()

fun List<LongRange>.mergeRanges(): List<LongRange> {
    val sorted: List<LongRange> = this.sortedWith(compareBy({ it.first }, { it.last }))
    val merged = mutableListOf<LongRange>()
    var current = sorted[0]

    // todo: why didn't using fold and List<LongRange> typecheck properly...?
    for (i in 1 until sorted.size) {
        val next = sorted[i]

        if (next.first <= current.last + 1) {
            val end = maxOf(current.last, next.last)
            current = current.first..end
        } else {
            merged.add(current)
            current = next
        }
    }

    merged.add(current)
    return merged
}

fun solution(input: String = exampleInput): Int {
    val (lhs, rhs) = input.split("\n\n")

    val ranges = lhs.lines().map { line -> line.split("-").map { it.toLong() } }
        .map { it[0]..it[1] }
        .mergeRanges()


    val nums = rhs.lines().map { it.toLong() }
    return nums.count { n -> ranges.any { range -> n in range } }
}

println(solution(actualInput))


