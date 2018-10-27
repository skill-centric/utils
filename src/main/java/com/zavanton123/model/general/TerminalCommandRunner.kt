package com.zavanton123.model.general

import java.io.*
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

class TerminalCommandRunner {

    private val log = Logger.getLogger(TerminalCommandRunner::class.java.name)

    interface Callback {

        fun onSuccess()
        fun onFailure()
    }

    fun runCommand(command: String, callback: Callback) {

        val scriptFile = createScriptFile(command)
        log.info("scriptFile absolute path: ${scriptFile.absolutePath}")

        Thread(Runnable {

            val process = Runtime.getRuntime().exec(scriptFile.absolutePath)
            log.info("calling waitFor()...")

            val isSuccess = process.waitFor(10, TimeUnit.MINUTES)

            scriptFile.delete()

            if (isSuccess) {
                callback.onSuccess()
            } else {
                callback.onFailure()
            }


//            val status = process.waitFor()
//            log.info("Process status: $status")
//
//            // Show what the process outputs to the console
//            showProcessConsoleOutput(process)
//
//            when (status) {
//                0 -> {
//                    callback.onSuccess()
//                    scriptFile.delete()
//                }
//                else -> callback.onFailure()
//            }

        }).start()
    }

    private fun createScriptFile(contents: String): File {

        val random = Random().nextLong()

        // todo replace from Desktop to other place
        val file = File("script-$random.sh")

        PrintWriter(file).use { it.println(contents) }
        file.setExecutable(true)

        return file
    }

    private fun showProcessConsoleOutput(process: Process) {

        val input = BufferedReader(InputStreamReader(process.inputStream))
        var line: String? = input.readLine()

        val builder = StringBuilder()

        try {
            while (line != null) {
                builder.append(line)
                line = input.readLine()
            }

            log.info(builder.toString())

        } catch (e: IOException) {

            log.throwing("TerminalCommandRunner",
                    "showProcessConsoleOutput", e)
        }
    }
}