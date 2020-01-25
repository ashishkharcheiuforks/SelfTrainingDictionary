package com.san4o.just4fun.selftrainingdictionary.data.local

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsDao
import kotlinx.coroutines.runBlocking
import org.apache.commons.io.FileUtils
import org.junit.Before
import org.junit.Test
import timber.log.Timber
import java.io.File
import java.nio.charset.StandardCharsets

class IrregularVerbsProviderTest {
    @Before
    fun setup(){
        Timber.plant(object : Timber.Tree(){
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                println(message)
            }
        })
    }

    @Test
    fun testInsert() = runBlocking() {
        val dao = mock<IrregularVerbsDao>()

        IrregularVerbsProvider.insertFirstValues(dao, readLines("irregular_verbs.txt"))

        verify(dao, atLeastOnce()).insertOrSkip(any())
    }

    private fun readLines(name: String): List<String> {
        val file = File("src\\main\\assets\\$name")
        return FileUtils.readLines(file, StandardCharsets.UTF_8)
    }
}