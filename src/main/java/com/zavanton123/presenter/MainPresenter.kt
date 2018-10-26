package com.zavanton123.presenter

import com.zavanton123.model.audioJoiner.AudioJoiner
import com.zavanton123.model.audioSplitter.AudioCutOffProcessor
import com.zavanton123.model.courseCreator.CourseFoldersCreator
import com.zavanton123.model.folder.AssetsExporter
import com.zavanton123.model.lessonInitializer.LessonInitializer
import com.zavanton123.model.lessonList.LessonListMaker
import com.zavanton123.model.lessonList.NumberedLessonMaker
import com.zavanton123.model.folder.LessonWalker
import com.zavanton123.model.pdf.PdfFileValidator
import com.zavanton123.model.pdf.PdfProcessor
import com.zavanton123.model.pdf.SlidesFileValidator
import com.zavanton123.model.video.VideoExporter
import com.zavanton123.utils.NoLessonsFolderException
import com.zavanton123.view.MvpView

import java.io.File
import java.util.Arrays

class MainPresenter : MvpPresenter {

    private var mvpView: MvpView? = null

    interface ExportFileCallback {

        fun onExportVideoSuccess()

        fun onExportVideoFail()
    }

    override fun setView(mvpView: MvpView) {
        this.mvpView = mvpView
    }

    override fun handlePrintLessonList(projectFolder: File) {

        if (!isFolderValid(projectFolder))
            return

        createLessonList(projectFolder)
    }

    override fun handlePrintNumberedLessonList(projectFolder: File) {

        if (!isFolderValid(projectFolder))
            return

        createNumberedLessonList(projectFolder)
    }

    override fun handleExportVideos(projectFolder: File) {

        if (!isFolderValid(projectFolder))
            return

        exportVideos(projectFolder)
    }

    override fun handleAudioCutOff(soundFolder: File) {

        if (!isFolderValid(soundFolder))
            return

        val audioCutOffProcessor = AudioCutOffProcessor()
        audioCutOffProcessor.processAllAudioFiles(soundFolder)
    }

    override fun handleJoinAudioFiles(soundsFolder: File) {

        if (!isFolderValid(soundsFolder))
            return

        val audioJoiner = AudioJoiner()
        audioJoiner.process(soundsFolder)

        mvpView!!.showJoinAudioFilesSuccess()
    }

    override fun handleCutoffAndJoinFiles(soundFolder: File) {

        if (!isFolderValid(soundFolder))
            return

        handleAudioCutOff(soundFolder)
        val cutoffFolder = File(soundFolder.toString() + "-cutoff")
        handleJoinAudioFiles(cutoffFolder)
    }

    override fun handleSetupLesson(lessonFolder: File) {

        if (!isFolderValid(lessonFolder))
            return

        val lessonInitializer = LessonInitializer()
        lessonInitializer.setupLesson(lessonFolder)
    }

    override fun handleCreateFoldersFromFile(courseStructureFile: File) {

        val courseFoldersCreator = CourseFoldersCreator()
        courseFoldersCreator.createFoldersFromFile(courseStructureFile)
    }

    override fun handleConvertPdfToPng(pdfFile: File) {

        val pdfFileValidator = PdfFileValidator()
        if (!pdfFileValidator.isValid(pdfFile)) {
            mvpView!!.showNotValidPdfFile()
            return
        }

        val pdfProcessor = PdfProcessor()
        pdfProcessor.convert(pdfFile, "WIP/images", "image",
                object : PdfProcessor.Callback {
                    override fun onSuccess() {

                        mvpView!!.showPdfToPngConversionSuccess()
                    }

                    override fun onFailure() {

                        mvpView!!.showPdfToPngConversionFail()
                    }
                })
    }

    override fun handleMakePdfAndPng(slidesFile: File) {

        val slidesFileValidator = SlidesFileValidator()
        if (!slidesFileValidator.isValid(slidesFile)) {
            mvpView!!.showNotSlidesFile()
            return
        }

        val pdfProcessor = PdfProcessor()
        pdfProcessor.createPdf(slidesFile, object : PdfProcessor.Callback {
            override fun onSuccess() {

                mvpView!!.showCreatePdfSuccess()

                val pdfFile = getPdfFileFromSlidesFileName(slidesFile)
                handleConvertPdfToPng(pdfFile)
            }

            override fun onFailure() {

                mvpView!!.showCreatePdfFail()
            }
        })
    }

    override fun handleExportSlidesButton(lessonsFolder: File) {

        val assetsExporter = AssetsExporter()
        assetsExporter.export(lessonsFolder, ".pdf", "SlidesPdf")
        assetsExporter.export(lessonsFolder, ".pptx", "SlidesPPt")
        assetsExporter.export(lessonsFolder, ".odp", "SlidesPPt")
        assetsExporter.export(lessonsFolder, ".mp4", "Video")
    }

    private fun getPdfFileFromSlidesFileName(slidesFile: File): File {
        val slidesFileName = slidesFile.name
        val split = slidesFileName.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val fileName = split[0]
        val fileNameWithExtension = "$fileName.pdf"
        return File(slidesFile.parent, fileNameWithExtension)
    }

    private fun exportVideos(projectFolder: File) {

        if (!isFolderValid(projectFolder))
            return

        try {
            val videoExporter = VideoExporter()
            videoExporter.exportFiles(projectFolder, object : ExportFileCallback {
                override fun onExportVideoSuccess() {
                    mvpView!!.showExportVideoSuccess()
                }

                override fun onExportVideoFail() {
                    mvpView!!.showExportVideoFail()
                }
            })

        } catch (ex: NoLessonsFolderException) {

            mvpView!!.showNotLessonsFolder()
        }

    }

    private fun createNumberedLessonList(projectFolder: File) {

        if (!isFolderValid(projectFolder))
            return

        try {
            val numberedLessonMaker = NumberedLessonMaker()
            numberedLessonMaker.printNumberedLessons(projectFolder)

            mvpView!!.showPrintLessonListSuccess()

        } catch (ex: NoLessonsFolderException) {

            mvpView!!.showNotLessonsFolder()
        }

    }

    private fun createLessonList(projectFolder: File) {

        if (!isFolderValid(projectFolder))
            return

        try {
            val lessonListMaker = LessonListMaker()
            lessonListMaker.printContents(projectFolder)

            mvpView!!.showPrintLessonListSuccess()

        } catch (ex: NoLessonsFolderException) {

            mvpView!!.showNotLessonsFolder()
        }

    }

    private fun isFolderValid(folder: File?): Boolean {

        if (folder == null) {
            mvpView!!.showNoFolderSelected()
            return false
        }

        return true
    }
}