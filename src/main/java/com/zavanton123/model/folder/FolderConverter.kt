package com.zavanton123.model.folder

import java.io.File

class FolderConverter {

    fun toSectionFolders(folders: Array<File>): List<SectionFolder> {

        val numberedFolders = arrayListOf<SectionFolder>()

        for (folder in folders) {
            if(folder.isDirectory)
                numberedFolders.add(toSectionFolder(folder))
        }

        return numberedFolders
    }

    private fun toSectionFolder(lessonFolder: File): SectionFolder {

        var orderedNumber: Int = getOrderedNumber(lessonFolder)

        return SectionFolder(orderedNumber, lessonFolder.absoluteFile)
    }

    fun toLessonFolders(sectionFolder: SectionFolder): List<LessonFolder> {

        val parentOrderNumber = sectionFolder.orderNumber
        val lessonFolders = arrayListOf<LessonFolder>()

        val folder = sectionFolder.folder
        for (file in folder.listFiles()) {

            val orderedNumber = getOrderedNumber(file)

            lessonFolders.add(LessonFolder(parentOrderNumber,
                    orderedNumber,
                    file.absoluteFile))
        }

        return lessonFolders
    }

    private fun getOrderedNumber(lessonFolder: File): Int {
        val splitFolderName = lessonFolder.name.split(". ")

        val nameFirstPart = splitFolderName[0]

        var orderedNumber: Int = 0

        try {
            orderedNumber = nameFirstPart.toInt()
        } catch (e: NumberFormatException) {

            // todo
            println(e.message)
        }
        return orderedNumber
    }
}