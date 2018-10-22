package com.zavanton123.model.pdf

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

class PdfProcessor {

    interface Callback {

        fun onSuccess()
        fun onFailure()
    }

    fun convert(pdfFile: File, targetDir: String, targetName: String) {

        val source = pdfFile.absolutePath

        val target = "${pdfFile.parent}/$targetDir/$targetName"

        val imagesDir = File(pdfFile.parent, targetDir)
        if(!imagesDir.exists())
            imagesDir.mkdirs()

        val commands = arrayOf("pdftoppm",
                source,
                target,
                "-png",
                "-r",
                "300")

        runCommand(commands, null)
    }

    fun createPdf(slidesFile: File, callback: Callback?) {

        val source = slidesFile.absolutePath

        val commands = arrayOf("unoconv", "-f", "pdf", source)

        runCommand(commands, callback)
    }

    private fun runCommand(commands: Array<String>, callback: Callback?) {
        Thread(Runnable {

            val process = Runtime.getRuntime().exec(commands)
            val status = process.waitFor()

            // Show what the pdftoppm app outputs to the console
            showAppConsoleOutput(process)

            callback?.let{

                if (status == 0) {
                    it.onSuccess()
                } else {
                    it.onFailure()
                }
            }

        }).start()
    }

    private fun showAppConsoleOutput(process: Process) {
        val input = BufferedReader(InputStreamReader(process.inputStream))
        var line: String? = input.readLine()

        try {
            while (line != null) {
                println(line)
                line = input.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}