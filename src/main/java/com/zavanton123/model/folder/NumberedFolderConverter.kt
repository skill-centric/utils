package com.zavanton123.model.folder

import java.io.File
import java.lang.NumberFormatException

class NumberedFolderConverter {

    fun convertFolders(folders: Array<File>): List<NumberedFolder> {

        val numberedFolders = arrayListOf<NumberedFolder>()

        for (folder in folders) {
            numberedFolders.add(convertFolderToNumberedFolder(folder))
        }

        return numberedFolders
    }

    fun convertFolderToNumberedFolder(folder: File): NumberedFolder {

        val splitFolderName = folder.name.split(". ")

        val nameFirstPart = splitFolderName[0]

        var orderedNumber: Int = 0

        try {
            orderedNumber = nameFirstPart.toInt()
        } catch (e: NumberFormatException) {

            // todo
            println(e.message)
        }

        return NumberedFolder(orderedNumber, folder.absoluteFile)
    }

}