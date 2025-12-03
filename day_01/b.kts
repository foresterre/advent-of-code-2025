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

data class Dial(val position: Int, val clicks: Int)

fun solution(start: Int = 50, size: Int = 100): Int {
    return actualInput.lineSequence()
        .map { it[0] to it.substring(1).toInt() }
        .map { if (it.first == 'L') -it.second else it.second }
        .scan(Dial(start, 0)) { device, steps ->
            val r = (device.position + steps).mod(size)

            // . . . . . 0 1 2 3 4 5 . . . . .
            //               ^
            //               -3
            //          ^
            //            --    = 2 - 3       afstand tot 0?

            // Hmmm how to do it shorter
            // val clicks = (device.position + steps).floorDiv(size) is not enough... grrr

            val newPos = device.position + steps
            val clicks = if (steps < 0) {
                // rip
                (device.position - 1).floorDiv(size) - (newPos - 1).floorDiv(size)
            } else {
                (device.position + steps).floorDiv(size)
            }

            println(
                "${device.position} + $steps // $size = ${device.position + steps} // $size = ${
                    (device.position + steps).floorDiv(
                        size
                    )
                }"
            )
            Dial(r, clicks)
        }
        .sumOf { it.clicks }
}


println("\n${solution(50, 100)}")