package com.zavanton123.model.pdf

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

class PdfConverter {

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

        Thread(Runnable {

            val process = Runtime.getRuntime().exec(commands)

            // Show what the pdftoppm app outputs to the console
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
        }).start()
    }
}