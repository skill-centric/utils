package com.zavanton123.model.courseCreator

import com.zavanton123.model.general.TextFileToStringConverter
import java.io.File

class CourseCreator {

    fun createFoldersFromFile(courseStructureFile: File) {

        val converter = TextFileToStringConverter()
        val courseStructure = converter.convertToString(courseStructureFile)


        val lines = courseStructure.split("\n")
        for (line in lines) {
            println(line)
        }


    }


}