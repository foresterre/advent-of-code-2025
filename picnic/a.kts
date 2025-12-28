#!/usr/bin/env kotlin

import java.io.File

val exampleInput = """F45 C20 F60 C45 A20 A15 A07"""
val actualInput = File("../input/picnic.txt").bufferedReader().readText().trim()

data class Tote(val type: Char, val weight: Int) {
    /**
     * Weights to test the "Thermodynamic Integrity" rule.
     * Derived from this invariant given by the assignment:
     *  `Temp(A) > Temp(C) > Temp(F)`
     */
    fun temp(): Int = when(type) {
        'F' -> 1
        'C' -> 2
        'A' -> 3
        else -> 0
    }
}


/**
 * A stack is valid for this function iff the following invariants hold:
 * - Warmer totes must be placed on top of colder totes, i.e. temp(bottom) <= temp(top)
 * - Lighter totes must be placed on heavier totes, i.e. weight(bottom) >= weight(bottom)
 * - ... NB the validity of stack size is not checked here.
 *
 * Tests the invariants for the list of totes for the given indices (`end` is exclusive).
 */
fun List<Tote>.isValidStack(start: Int, end: Int): Boolean {
    for (i in start until end) {
        val bottom = this[i]
        val top = this[i + 1]

        if (bottom.temp() > top.temp()) return false
        if (bottom.weight < top.weight) return false
    }
    return true
}

/**
 * Recursively determine the optimal places to "cut" the stream into stacks
 * such that the "Total Handling Cost" is minimized.
 */
fun solution(input: String = exampleInput): Int {
    val totes = input.trim().split(" ").map {
        Tote(it[0], it.substring(1).toInt())
    }
    val n = totes.size
    val costs = mapOf(1 to 50, 2 to 25, 3 to 10, 4 to 0)
    val memo = mutableMapOf<Int, Int>()

    /**
     * Find minimum cost for a given subset
     */
    fun minCost(pos: Int): Int {
        if (pos == n) return 0
        if (pos in memo) return memo.getValue(pos)

        val bestCost = (1..4)
            .filter { size -> pos + size <= n }
            .filter { size -> totes.isValidStack(pos, pos + size - 1) }
            .minOfOrNull { size -> costs.getValue(size) + minCost(pos + size) }
            ?: Int.MAX_VALUE

        memo[pos] = bestCost
        return bestCost
    }

    return minCost(0)
}

println(solution())