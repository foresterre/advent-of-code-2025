import java.io.File

val exampleInput = """L68
L30
R48
L5
R60
L55
L1
L99
R14
L82"""

val actualInput = File("../input/01.txt").bufferedReader().readText().trim()


fun solution(start: Int = 50, size: Int = 100): Int {
    return actualInput.lineSequence()
        .map { it[0] to it.substring(1).toInt() }
        .map { if (it.first == 'L') -it.second else it.second }
        .scan(start) { pos, steps -> (pos + steps).mod(size) }
        .count { it == 0 }
}

println(solution())