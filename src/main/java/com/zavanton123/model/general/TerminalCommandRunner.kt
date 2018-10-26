package com.zavanton123.model.general

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class TerminalCommandRunner {

    interface Callback {

        fun onSuccess()
        fun onFailure()
    }

    fun runCommand(commands: Array<String>, callback: Callback) {
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

}