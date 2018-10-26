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

        val commands = arrayOf("pdftoppm",
                source,
                target,
                "-png",
                "-r",
                "300")

        runner.runCommand(commands, callback)
    }

    fun createPdf(slidesFile: File, callback: TerminalCommandRunner.Callback) {

        val source = slidesFile.absolutePath

        val commands = arrayOf("unoconv", "-f", "pdf", source)

        runner.runCommand(commands, callback)
    }
}