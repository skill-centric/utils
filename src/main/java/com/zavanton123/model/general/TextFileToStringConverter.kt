package com.zavanton123.model.general

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.StringBuilder

class TextFileToStringConverter {

    fun convertToString(file: File): String {
        val reader = FileReader(file)

        val bufferedReader = BufferedReader(reader)
        var line: String? = bufferedReader.readLine()
        val builder = StringBuilder()

        while (line != null) {

            builder.append(line)
            builder.append("\n")

            line = bufferedReader.readLine()
        }

        return builder.toString()
    }
}