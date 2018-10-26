package com.zavanton123.model.folder

import java.io.File

class LessonWalker(private val converter: FolderConverter = FolderConverter()) {

    fun walkTree(lessonsFolder: File,
                 action: (lessonFolder: LessonFolder) -> Unit) {

        if (!lessonsFolder.isDirectory) return

        val folders = lessonsFolder.listFiles()

        val sectionFolders = converter.toSectionFolders(folders)

        val lessonFolders = arrayListOf<LessonFolder>()

        for (sectionFolder in sectionFolders) {

            lessonFolders.addAll(converter.toLessonFolders(sectionFolder))
        }

        lessonFolders.sort()

        for (lessonFolder in lessonFolders) {

            action(lessonFolder)
        }
    }
}

