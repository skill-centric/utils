package com.zavanton123.model.video

import com.zavanton123.model.general.TerminalCommandRunner
import java.io.File
import java.util.logging.Logger

class VideoRenderer {

    private val log: Logger = Logger.getLogger(VideoRenderer::class.java.name)

    fun render(kdenliveFile: File) {

        val command = setupCommand(kdenliveFile)

        val runner = TerminalCommandRunner()

        runner.runCommand(command, object : TerminalCommandRunner.Callback {
            override fun onSuccess() {
                log.info("Success!")
            }

            override fun onFailure() {
                log.info("Failed")
            }
        })
    }

    private fun setupCommand(kdenliveFile: File): String {

        val kdenliveFilePath = "\"${kdenliveFile.absolutePath}\""
        log.info("kdenliveFilePath: $kdenliveFilePath")

        val videoFilePath = getVideoFilePath(kdenliveFile)
        log.info("videoFilePath: $videoFilePath")

        val command = "melt $kdenliveFilePath " +
                "-consumer avformat:$videoFilePath " +
                "vcodec=libx264 b=5000k acodec=aac ab=128k"
        log.info("command: $command")

        return command
    }

    private fun getVideoFilePath(kdenliveFile: File): String {

        val videoFileName = getVideoFileName(kdenliveFile,
                "mp4", " - kdenlive.kdenlive")

        val videoFile = File(kdenliveFile.parent, videoFileName)

        return "\"${videoFile.absolutePath}\""
    }

    private fun getVideoFileName(kdenliveFile: File,
                                 extension: String,
                                 nameCutOff: String): String {

        val videoName = kdenliveFile.name.substringBefore(nameCutOff)

        return "$videoName.$extension"
    }
}