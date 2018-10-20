package com.zavanton123.model.courseCreator

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.StringBuilder

class CourseCreator {

    fun createFoldersFromFile(courseStructureFile: File) {

        val courseStructure = getCourseStructure(courseStructureFile)
        println(courseStructure)

    }

    private fun getCourseStructure(courseStructureFile: File): String {
        val reader = FileReader(courseStructureFile)

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