package com.zavanton123.model.video

import com.zavanton123.model.general.TerminalCommandRunner
import java.io.File

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

//        val commands = arrayOf("melt",
//                kdenliveFilePath,
//                targetFilePath,
//                "-consumer",
//                targetFilePath,
//                "vcodec=libx264",
//                "b=5000k",
//                "acodec=aac",
//                "ab=128k"
//                )

//        val commands = arrayOf("melt", kdenliveFilePath,
//                "-consumer", "/home/zavanton/great.mp4")

        val commandsOne = arrayOf("chmod", "+x", "/home/zavanton/Desktop/script.sh")

        val commandsTwo = arrayOf("/home/zavanton/Desktop/script.sh")

        printCommand(commandsOne)

        val runner = TerminalCommandRunner()
        val callback = object : TerminalCommandRunner.Callback {
            override fun onSuccess() {
                println("Success!")
            }

            override fun onFailure() {
                println("Failed")
            }
        }

        runner.runCommand(commandsOne, callback)
        Thread(Runnable {
            Thread.sleep(2000)
            runner.runCommand(commandsTwo, callback)
        }).run()
    }

    private fun printCommand(commands: Array<String>) {
        val builder = StringBuilder()
        for (command in commands) {
            builder.append("$command ")
        }
        println(builder.toString() + "\n")
    }
}