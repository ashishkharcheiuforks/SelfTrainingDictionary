package com.san4o.just4fun.selftrainingdictionary.presentation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UniqueRandomFromListTest {

    @Test
    fun test() {
        val income = listOf(1, 2, 3, 4, 5)
        val randomizerType = UniqueRandomFromList(income)

        val returned = ArrayList<Int>()
        returned.add(randomizerType.next())
        returned.add(randomizerType.next())
        returned.add(randomizerType.next())
        returned.add(randomizerType.next())
        returned.add(randomizerType.next())

        assertEquals(returned.size, income.size)
        assertTrue(returned.containsAll(income))

        var atLeast = 3
        for ((index, value) in income.withIndex()) {
            val returnedValue = returned[index]

            if (returnedValue != value) {
                atLeast--
            }
        }
        assertTrue(atLeast <= 0)
    }

    @Test(expected = RuntimeException::class)
    fun testOverflow(){
        val randomizerType = UniqueRandomFromList(listOf(1))
        val next = randomizerType.next()
        randomizerType.next()    }
}