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

        }).start()
    }

    private fun createScriptFile(contents: String): File {

        val random = Random().nextLong()

        val file = File("script-$random.sh")

        PrintWriter(file).use { it.println(contents) }
        file.setExecutable(true)

        return file
    }
}