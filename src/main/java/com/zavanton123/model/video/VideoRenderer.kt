package com.zavanton123.model.video

import com.zavanton123.model.general.TerminalCommandRunner
import java.io.File
import java.util.logging.Logger

class VideoRenderer(private val commandRunner:
                    TerminalCommandRunner = TerminalCommandRunner()) {

    private val log: Logger = Logger.getLogger(VideoRenderer::class.java.name)

    fun renderFromArchive(tarFile: File) {

        val parentFile = tarFile.parentFile
        val wipFolder = createFolder(parentFile, "WIP")

        val extractCommand = "tar xvzf \"${tarFile.absolutePath}\" " +
                "-C \"${wipFolder.absolutePath}\""
        log.info("extractCommand: $extractCommand")

        // todo remove callback hell
        commandRunner.runCommand(extractCommand,
                object : TerminalCommandRunner.Callback {

                    override fun onSuccess() {
                        log.info("Successfully extracted " +
                                "the tar archive to tmp folder!")

                        renderFromWipFolder(wipFolder)
                    }

                    override fun onFailure() {
                        log.severe("An error occurred while " +
                                "extracting the tar archive to tmp folder!")
                    }
                })
    }

    private fun createFolder(parentFile: File?, folderName: String): File {
        val tmpFolder = File(parentFile, folderName)
        if (!tmpFolder.exists())
            tmpFolder.mkdirs()
        return tmpFolder
    }

    private fun renderFromWipFolder(wipFolder: File) {

        for (file in wipFolder.listFiles()) {

            if (file.name.endsWith(".kdenlive")) {

                val targetFolder = File(file.parent).parent
                renderFromKdenliveFile(file, targetFolder)
            }
        }
    }

    private fun renderFromKdenliveFile(kdenliveFile: File, targetFolder: String) {

        val renderCommand = setupCommand(kdenliveFile, targetFolder)

        commandRunner.runCommand(renderCommand, object : TerminalCommandRunner.Callback {

            override fun onSuccess() {
                log.info("Successfully rendered kdenlive project!")
            }

            override fun onFailure() {
                log.info("An error occurred while rendering kdenlive project!")
            }
        })
    }

    private fun setupCommandOriginal(kdenliveFile: File, targetFolder: String): String {

        val kdenliveFilePath = "\"${kdenliveFile.absolutePath}\""
        log.info("kdenliveFilePath: $kdenliveFilePath")

        val videoFilePath = getVideoFilePath(kdenliveFile, targetFolder)
        log.info("videoFilePath: $videoFilePath")

        val command = "melt $kdenliveFilePath " +
                "-consumer avformat:$videoFilePath " +
                "vcodec=libx264 b=5000k acodec=aac ab=128k"
        log.info("command: $command")

        return command
    }

    private fun setupCommand(kdenliveFile: File, targetFolder: String): String {

        val kdenliveFilePath = "\"${kdenliveFile.absolutePath}\""
        log.info("kdenliveFilePath: $kdenliveFilePath")

        val videoFilePath = getVideoFilePath(kdenliveFile, targetFolder)
        log.info("videoFilePath: $videoFilePath")

        val commandOriginal = "melt $kdenliveFilePath " +
                "-consumer avformat:$videoFilePath " +
                "vcodec=libx264 b=5000k acodec=aac ab=128k"

        val command = "melt $kdenliveFilePath " +
                "-consumer avformat:$videoFilePath " +
                "properties=x264-medium f=mp4 vcodec=libx264 " +
                "acodec=aac g=120 crf=23 ab=160k preset=faster threads=1 real_time=-1"

        log.info("command: $command")

        return command
    }

    private fun getVideoFilePath(kdenliveFile: File, targetFolder: String): String {

        val videoFileName = getVideoFileName(kdenliveFile,
                "mp4", " - kdenlive.kdenlive")

        val videoFile = File(targetFolder, videoFileName)

        return "\"${videoFile.absolutePath}\""
    }

    private fun getVideoFileName(kdenliveFile: File,
                                 extension: String,
                                 nameCutOff: String): String {

        val videoName = kdenliveFile.name.substringBefore(nameCutOff)

        return "$videoName.$extension"
    }
}