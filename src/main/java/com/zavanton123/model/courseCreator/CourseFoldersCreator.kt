package com.zavanton123.model.courseCreator

import com.zavanton123.model.general.TextFileToStringConverter
import java.io.File

class CourseFoldersCreator {

    var sectionNumber = 0
    var lessonNumber = 0

    private lateinit var sectionTitle: String
    private lateinit var parentFolder: File

    fun createFoldersFromFile(courseStructureFile: File) {

        val converter = TextFileToStringConverter()
        val courseStructure = converter.convertToString(courseStructureFile)

        parentFolder = courseStructureFile.parentFile

        val lines = courseStructure.split("\n")
        for (line in lines) {

            if (line.startsWith("- ")) {
                processLesson(line)
            } else {
                processSection(line)
            }
        }
    }

    private fun processSection(line: String) {

        if(line.isBlank())
            return

        lessonNumber = 0
        sectionNumber++
        sectionTitle = sectionNumber.toString() + ". " + line

        val sectionFolder = File(parentFolder, sectionTitle)
        if (!sectionFolder.exists()) {
            sectionFolder.mkdirs()
        }
    }

    private fun processLesson(line: String) {

        if(line.isBlank())
            return

        lessonNumber++
        val lessonName = line.substring(2)
        val lessonTitle = lessonNumber.toString() + ". " + lessonName

        val sectionFolder = File(parentFolder, sectionTitle)

        val lessonFolder = File(sectionFolder, lessonTitle)
        if (!lessonFolder.exists()) {
            lessonFolder.mkdirs()
        }
    }
}