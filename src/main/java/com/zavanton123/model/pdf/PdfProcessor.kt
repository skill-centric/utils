package com.zavanton123.model.pdf

import com.zavanton123.model.general.TerminalCommandRunner
import java.io.File

class PdfProcessor(private val runner: TerminalCommandRunner = TerminalCommandRunner()) {

    fun convert(pdfFile: File,
                targetDir: String,
                targetName: String,
                callback: TerminalCommandRunner.Callback) {

        val source = pdfFile.absolutePath

        val target = "${pdfFile.parent}/$targetDir/$targetName"

        val imagesDir = File(pdfFile.parent, targetDir)
        if (!imagesDir.exists())
            imagesDir.mkdirs()

        val command = "pdftoppm \"$source\" \"$target\" -png -r 300"

        runner.runCommand(command, callback)
    }

    fun createPdf(slidesFile: File, callback: TerminalCommandRunner.Callback) {

        val source = slidesFile.absolutePath

        val command = "unoconv -f pdf \"$source\""

        runner.runCommand(command, callback)
    }
}