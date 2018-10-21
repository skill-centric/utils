package com.zavanton123.model.pdf

import java.io.File

class PdfValidator {

    fun isValid(pdfFile: File): Boolean {

        val name = pdfFile.name

        return name.endsWith(".pdf")
    }
}
