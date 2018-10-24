package com.zavanton123.model.treewalker

import com.zavanton123.model.folder.NumberedFolderConverter
import java.io.File
import java.util.*

class TreeWalker(val converter: NumberedFolderConverter = NumberedFolderConverter()) {

    fun walkTree(lessonFolder: File, action: () -> Unit) {

        if (!lessonFolder.isDirectory) return

        val listFiles = lessonFolder.listFiles()

        val numberedFolders = converter.convertFolders(listFiles)

        Collections.sort(numberedFolders)

        for (folder in numberedFolders) {
            println(folder)
        }
    }
}

