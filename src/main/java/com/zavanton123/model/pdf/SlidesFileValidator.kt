package com.zavanton123.model.pdf

import java.io.File

class SlidesFileValidator {

    fun isValid(slidesFile: File): Boolean {

        val fileName = slidesFile.name
        val allowedExtensions = listOf("ppt", "pptx", "odt", "odp", "otp")

        for (extension in allowedExtensions) {
            if(fileName.endsWith(extension))
                return true
        }
        return false
    }
}