package com.zavanton123.model.general

import java.io.*
import java.util.*

class TerminalCommandRunner {

    interface Callback {

        fun onSuccess()
        fun onFailure()
    }

    fun runCommand(command: String, callback: Callback) {

        val scriptFile = createScriptFile(command)
        println("scriptFile absolute path: ${scriptFile.absolutePath}")

        Thread(Runnable {

            val process = Runtime.getRuntime().exec(scriptFile.absolutePath)
            val status = process.waitFor()

            // Show what the process outputs to the console
            showProcessConsoleOutput(process)

            when (status) {
                0 -> {
                    callback.onSuccess()
                    scriptFile.delete()
                }
                else -> callback.onFailure()
            }

        }).start()
    }

    private fun createScriptFile(contents: String): File {

        val random = Random().nextLong()
        val file = File("script-$random.sh")

        PrintWriter(file).use { it.println(contents) }
        file.setExecutable(true)

        return file
    }

    private fun showProcessConsoleOutput(process: Process) {
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

    fun runCommands(commands: Array<String>, callback: Callback) {
        Thread(Runnable {

            val process = Runtime.getRuntime().exec(commands)
            val status = process.waitFor()

            // Show what the process outputs to the console
            showProcessConsoleOutput(process)

            when (status) {
                0 -> callback.onSuccess()
                else -> callback.onFailure()
            }

        }).start()
    }
}