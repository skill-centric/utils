package com.zavanton123.model.video

import com.zavanton123.model.general.TerminalCommandRunner
import java.io.File
import java.io.PrintWriter

class VideoRenderer {

    fun render(kdenliveFile: File) {

        val videoName = kdenliveFile.name
                .substringBefore(" - kdenlive.kdenlive")

        val videoFileName = "$videoName.mp4"
        println("videoFileName: $videoFileName")

        val target = File(kdenliveFile.parent, videoFileName)

        val kdenliveFilePath = "\"${kdenliveFile.absolutePath}\""
        println("kdenliveFilePath: $kdenliveFilePath")

        val targetFilePath = "avformat:\"${target.absolutePath}\""
        println("targetFilePath: $targetFilePath")

        val commandFile = createCommandFile()

        val commands = arrayOf("${commandFile.absolutePath}")

        val runner = TerminalCommandRunner()

        runner.runCommand(commands, object : TerminalCommandRunner.Callback {
            override fun onSuccess() {
                println("Success!")

                commandFile.delete()
            }

            override fun onFailure() {
                println("Failed")
            }
        })
    }

    private fun createCommandFile(): File {

        val contents = "melt \"/home/zavanton/Desktop/2. Singleton Pattern Example/Singleton Pattern Example - kdenlive/Singleton Pattern Example - kdenlive.kdenlive\" -consumer avformat:\"/home/zavanton/Desktop/2. Singleton Pattern Example/Singleton Pattern Example - kdenlive/Result.mp4\" vcodec=libx264 b=5000k acodec=aac ab=128k"

        val name = "/home/zavanton/Desktop/script.sh"

        val file = File(name)

        PrintWriter(file).use { it.println(contents) }

        file.setExecutable(true)

        return file
    }
}