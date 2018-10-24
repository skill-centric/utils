package com.zavanton123.model.folder

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class FileCloner {

    fun clone(sourceFile: File, targetFile: File) {

        val source = FileInputStream(sourceFile).channel
        val destination = FileOutputStream(targetFile).channel
        destination.transferFrom(source, 0, source.size())
    }
}