package com.zavanton123.model.pdf

import java.io.File

class PdfFileValidator {

    fun isValid(pdfFile: File): Boolean {

        val name = pdfFile.name

        return name.endsWith(".pdf")
    }
}
