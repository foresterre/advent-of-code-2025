import java.io.File

val exampleInput =
    """..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@."""

val actualInput = File("../input/04.txt").bufferedReader().readText().trim()

val directions = arrayOf(
    0 to 1 /* top */, 1 to 1 /* right-top */, 1 to 0 /* right */, 1 to -1 /* right-bottom */,
    0 to -1 /* bottom */, -1 to -1 /* left-bottom */, -1 to 0 /* left */, -1 to 1 /* left-top */
)

fun isAccessible(grid: Set<Pair<Int, Int>>, pos: Pair<Int, Int>): Boolean {
    return directions.count { grid.contains(pos.first + it.first to pos.second + it.second) } < 4
}

// In 4a I just counted over the List<CharArray>, but since we need to remove items here, using a Set will actively
// reduce the amount of items for which we need to check the neighbours (or make it simpler to do so)
fun solution(input: String = exampleInput): Int {
    val grid = input.trim().lineSequence().withIndex().flatMap { (j, value) ->
        value.toCharArray().withIndex().filter { (_, value) -> value == '@' }.map { (i, _) -> (j to i) }
    }
        .toMutableSet()

    // cry, cry, cry a lot :D
    val sizeStart = grid.size
    while (true) {
        val sizeBefore = grid.size
        grid.removeIf { isAccessible(grid, it) } // Thank you, Java stdlib

        if (sizeBefore == grid.size) break
    }

    return sizeStart - grid.size
}

println(solution(actualInput))
