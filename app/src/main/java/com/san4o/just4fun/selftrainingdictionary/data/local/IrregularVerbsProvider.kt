package com.san4o.just4fun.selftrainingdictionary.data.local

import android.content.Context
import com.san4o.just4fun.selftrainingdictionary.data.base.readAssetsLines
import com.san4o.just4fun.selftrainingdictionary.data.local.dao.IrregularVerbsDao
import com.san4o.just4fun.selftrainingdictionary.data.local.entities.IrregularVerbWordEntity
import timber.log.Timber


object IrregularVerbsProvider {
    suspend fun insertFirstValues(dao: IrregularVerbsDao, context: Context) {
        val readLines = context.readAssetsLines("irregular_verbs.txt")
        insertFirstValues(dao, readLines)

    }

    internal suspend fun insertFirstValues(dao: IrregularVerbsDao, lines: List<String>) {
        dao.removeAll()
        for ((index, line) in lines.withIndex()) {
            val entity: IrregularVerbWordEntity = parse(index + 1, line)
            Timber.d("insert $entity")
            dao.insertOrSkip(entity)
        }

    }

    private fun parse(count: Int, line: String): IrregularVerbWordEntity {
        val tokens = line
            .replace("\t", " ")
            .replace("  ", " ")
            .split(" ")
        if (tokens.size == 4) {
            return defaultEntity(count, tokens)
        }
        val newTokens = ArrayList<String>()
        var newTokenBuilder = StringBuilder()
        var count = 0
        for (token in tokens) {
            if (token.endsWith(",")) {
                newTokenBuilder.append(token)
            } else {
                newTokenBuilder.append(token)
                count++
                if (count < 4) {
                    newTokens.add(newTokenBuilder.toString())
                    newTokenBuilder = StringBuilder()
                }
            }
        }
        if (newTokenBuilder.isNotEmpty()) {
            newTokens.add(newTokenBuilder.toString())
        }

        if (newTokens.size != 4) {
            throw RuntimeException("Error format of string : " + tokens.joinToString(" "))
        }
        return defaultEntity(count, newTokens)
    }

    private fun defaultEntity(count: Int, tokens: List<String>): IrregularVerbWordEntity {
        return IrregularVerbWordEntity(
            id = count.toLong(),
            present = tokens[0],
            past = tokens[1],
            perfect = tokens[2],
            translation = tokens[3]
        )
    }


}