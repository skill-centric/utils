package com.zavanton123.model.folder

import java.io.File
import java.util.*

class AssetsExporter(private val fileCloner: FileCloner = FileCloner()) {

    fun export(lessonsFolder: File, extension: String, targetFolderName: String) {

        val targetFolder = setupTargetFolder(lessonsFolder, targetFolderName)

        val lessonWalker = LessonWalker()

        lessonWalker.walkTree(lessonsFolder) {
            processLessonFolder(it, extension, targetFolder)
        }
    }

    private fun processLessonFolder(lessonFolder: LessonFolder,
                                    extension: String,
                                    targetFolder: File) {

        println("Called processLessonFolder")

        if(lessonFolder.folder.isDirectory){

            val lessonFiles = Arrays.asList(*lessonFolder.folder.listFiles()!!)
            for (lessonFile in lessonFiles) {

                processLessonFile(lessonFile, extension, lessonFolder, targetFolder)
            }
        }
    }

    private fun processLessonFile(lessonFile: File,
                                  extension: String,
                                  lessonFolder: LessonFolder,
                                  targetFolder: File) {

        if (lessonFile.name.endsWith(extension)) {

            val targetName = composeFileName(lessonFolder, lessonFile)

            val targetFile = File(targetFolder, targetName)

            println("Copy file " + lessonFile.name
                    + " to " + targetFile.absolutePath)

            fileCloner.clone(lessonFile, targetFile)
        }
    }

    private fun composeFileName(lessonFolder: LessonFolder, lessonFile: File): String {

        return (lessonFolder.parentOrderNumber.toString() + "."
                + lessonFolder.orderNumber + " "
                + lessonFile.name)
    }

    private fun setupTargetFolder(lessonsFolder: File, targetFolderName: String): File {

        val courseFolder = lessonsFolder.parentFile
        val targetFolder = File(courseFolder, targetFolderName)
        if (!targetFolder.exists()) {
            targetFolder.mkdirs()
        }
        return targetFolder
    }
}