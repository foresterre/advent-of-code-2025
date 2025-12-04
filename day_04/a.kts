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

fun isAccessible(grid: List<CharArray>, pos: Pair<Int, Int>): Boolean {
    if (grid[pos.second][pos.first] != '@') return false

    return directions
        .map { grid.getOrNull(pos.second + it.first)?.getOrNull(pos.first + it.second) }
        .count { it != null && it == '@' } < 4
}

fun solution(input: String = exampleInput): Int {
    val grid = input.trim().lineSequence().map { it.toCharArray() }.toList()
    val width = grid[0].size
    val height = grid.size

    return (0..<height)
        .flatMap { j ->
            (0..<width)
                .filter { i -> isAccessible(grid, j to i) }
        }
        .count()
}

println(solution(actualInput))


