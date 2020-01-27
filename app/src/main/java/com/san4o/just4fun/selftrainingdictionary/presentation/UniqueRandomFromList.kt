package com.san4o.just4fun.selftrainingdictionary.presentation

import kotlin.random.Random

class UniqueRandomFromList<T>(
    private val income: List<T>
) {
    private val max = income.size
    private val random = Random(1)
    private val returned = ArrayList<T>()

    fun next(): T {
        var item: T = income.random()
        while (returned.contains(item)) {
            if (returned.size == income.size) {
                throw IllegalStateException("No randoms. All values returned : $returned")
            }
            item = income.random()
        }
        returned.add(item)
        return item
    }

    private fun random(): Int {
        income.random()
        return Random(1).nextInt(max)
    }
}