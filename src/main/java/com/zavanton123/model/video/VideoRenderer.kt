package com.zavanton123.model.video

import com.zavanton123.model.general.TerminalCommandRunner
import java.io.File
import java.util.logging.Logger

class VideoRenderer(private val commandRunner: TerminalCommandRunner = TerminalCommandRunner()) {

    private val log: Logger = Logger.getLogger(VideoRenderer::class.java.name)

    fun render(kdenliveFile: File) {

        val command = setupCommand(kdenliveFile)

        commandRunner.runCommand(command, object : TerminalCommandRunner.Callback {
            override fun onSuccess() {
                log.info("Success!")
            }

            override fun onFailure() {
                log.info("Failed")
            }
        })
    }

    fun renderTar(tarFile: File){

        val parentFile = tarFile.parentFile
        val tmpFolder = File(parentFile, "tmp")
        if(!tmpFolder.exists())
            tmpFolder.mkdirs()

        val extractCommand = "tar xvzf \"${tarFile.absolutePath}\" -C \"${tmpFolder.absolutePath}\""
        log.info("extractCommand: $extractCommand")

        commandRunner.runCommand(extractCommand, object : TerminalCommandRunner.Callback {

            override fun onSuccess() {
                log.info("Successfully extracted the tar archive to tmp folder!")

                // todo render video file
            }

            override fun onFailure() {
                log.severe("Failed to extract the tar archive to tmp folder!")
            }
        })


        // todo delete tmp folder
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