package com.san4o.just4fun.selftrainingdictionary.data.base

import android.content.Context
import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets

fun Context.readAssetsLines(fileName: String): List<String> {
    val inputStream = assets.open(fileName)
    return IOUtils.readLines(inputStream, StandardCharsets.UTF_8)
}
