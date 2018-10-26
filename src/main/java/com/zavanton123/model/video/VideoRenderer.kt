package com.zavanton123.model.video

import com.zavanton123.model.general.TerminalCommandRunner
import java.io.File

class VideoRenderer {

    fun render(kdenliveFile: File) {

        val videoFileName = getVideoFileName(kdenliveFile,
                "mp4",
                " - kdenlive.kdenlive")

        val videoFile = File(kdenliveFile.parent, videoFileName)

        val kdenliveFilePath = "\"${kdenliveFile.absolutePath}\""
        println("kdenliveFilePath: $kdenliveFilePath")

        val videoFilePath = "\"${videoFile.absolutePath}\""
        println("videoFilePath: $videoFilePath")

        val targetFilePath = "avformat:$videoFilePath"

        val commands = "melt \"/home/zavanton/Desktop/2. Singleton Pattern Example/Singleton Pattern Example - kdenlive/Singleton Pattern Example - kdenlive.kdenlive\" -consumer avformat:\"/home/zavanton/Desktop/2. Singleton Pattern Example/Singleton Pattern Example - kdenlive/Result.mp4\" vcodec=libx264 b=5000k acodec=aac ab=128k"

        val runner = TerminalCommandRunner()

        runner.runCommand(commands, object : TerminalCommandRunner.Callback {
            override fun onSuccess() {
                println("Success!")

            }

            override fun onFailure() {
                println("Failed")
            }
        })
    }

    private fun getVideoFileName(kdenliveFile: File,
                                 extension: String,
                                 nameCutOff: String): String {

        val videoName = kdenliveFile.name.substringBefore(nameCutOff)

        return "$videoName.$extension"
    }
}