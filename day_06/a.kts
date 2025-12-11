import java.io.File
import java.util.*

val exampleInput =
    """123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +"""

val actualInput = File("../input/06.txt").bufferedReader().readText().trim()

fun String.transposed(): List<List<String>> {
    return this.trim().lines()
        .map { it.trim().split("\\s+".toPattern()) }
        .let { line -> line[0].indices.map { col -> line.map { it[col] } } }
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

// NB: Day 6a is just reverse polish notation with any length of operands. * and + are commutative, so order doesn't matter.
fun solution(input: String = exampleInput): Long {
    return input.transposed().sumOf { computeRpn(it) }
}

println(solution(actualInput))


