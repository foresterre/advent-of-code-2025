import java.io.File
import java.util.*

val exampleInput =
    """123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +"""

val actualInput = File("../input/06.txt").bufferedReader().readText().trim()

fun String.transposed(): List<List<String>> {
    val lines = this.lines().dropLast(1)
    val len = lines.maxOf { it.length }

    // ugh...
    val cols = (len - 1 downTo 0).map { col -> lines.map { it[col] } } // map to chars
        .map { it.joinToString("").trim() }
        .fold(mutableListOf(mutableListOf<String>())) { acc, num ->
            if (num.isEmpty()) {
                acc.add(mutableListOf())
            } else {
                acc.last().add(num)
            }
            acc
        }

    this.lines().last().trim().split("\\s+".toPattern())
        .reversed()
        .withIndex()
        .forEach { (i, operator) -> cols[i].add(operator) }

    return cols
}

fun parse(str: String): (Stack<Long>) -> Unit {
    return when (str) {
        "+" -> compose(::add)
        "*" -> compose(::multiply)
        else -> compose { str.toLong() }
    }
}

fun compose(f: (Stack<Long>) -> Long): (Stack<Long>) -> Unit = { it.push(f.invoke(it)) }
fun op(stack: Stack<Long>, f: (Long, Long) -> Long): Long =
    generateSequence { if (stack.isNotEmpty()) stack.pop() else null }.reduce(f)

fun add(s: Stack<Long>) = op(s, Long::plus)
fun multiply(s: Stack<Long>) = op(s, Long::times)

fun computeRpn(exprs: List<String>): Long {
    val result = Stack<Long>()
    exprs.map(::parse).forEach { it.invoke(result) }
    return result.peek()
}

fun solution(input: String = exampleInput): Long {
    return input.transposed().sumOf { computeRpn(it) }
}


println(solution(actualInput))


