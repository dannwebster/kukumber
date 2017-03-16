package org.kukumber

import java.util.*


class BowlingGame() {
    val scores = ArrayList<Int>()

    fun roll(score: Int) {
        scores += score
    }

    fun score(): Int {
        var isFirst = true
        var frame = 0
        return scores.mapIndexed { i, score ->
            if (frame > 9) {
                0
            } else if (isStrike(i)) {
                frame++
                isFirst = true
                strikeScore(i)
            } else if (isSpare(i)) {
                frame++
                isFirst = true
                spareScore(i)
            } else {
                if (!isFirst)
                    frame++
                isFirst = !isFirst
                score
            }
        }.sum()
    }

    fun getScore(i: Int) = scores.getOrElse(i) {0}

    fun isStrike(i: Int) = getScore(i) == 10
    fun strikeScore(i: Int) = getScore(i) + getScore(i+1) + getScore(i+2)

    fun isSpare(i: Int) = getScore(i) + getScore(i-1) == 10
    fun spareScore(i: Int) = getScore(i) + getScore(i+1)


    fun rollMany(pair: Pair<Int, Int>)  { this.rollMany(pair.first, pair.second) }

    fun rollMany(count: Int, score: Int) {
        (1..count).forEach {
            this.roll(score)
        }
    }

    operator fun plus(score: Int) { this.roll(score) }
    operator fun plus(pair: Pair<Int, Int>) { this.rollMany(pair) }
}

infix fun Int.rollsOf(score: Int) = Pair<Int, Int>(this, score)
