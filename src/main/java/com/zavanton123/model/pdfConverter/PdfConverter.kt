package com.zavanton123.model.pdfConverter

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

class PdfConverter {

    fun convert(pdfFile: File) {

        println("Method is called")

        val runtime = Runtime.getRuntime()

        println(pdfFile.name)

        val commands = arrayOf("pdftoppm",
                "/home/zavanton/demo.pdf",
                "/home/zavanton/img",
                "-png",
                "-r",
                "300")

        val process = runtime.exec(commands)
        process.waitFor()

        Thread(Runnable {

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