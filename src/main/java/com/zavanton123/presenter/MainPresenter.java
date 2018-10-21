package com.zavanton123.presenter;

import com.zavanton123.model.audio_joiner.AudioJoiner;
import com.zavanton123.model.audio_splitter.AudioCutOffProcessor;
import com.zavanton123.model.courseCreator.CourseFoldersCreator;
import com.zavanton123.model.lesson_initializer.LessonInitializer;
import com.zavanton123.model.lesson_list.LessonListMaker;
import com.zavanton123.model.lesson_list.NumberedLessonMaker;
import com.zavanton123.model.pdfConverter.PdfConverter;
import com.zavanton123.model.video.VideoExporter;
import com.zavanton123.utils.NoLessonsFolderException;
import com.zavanton123.view.MvpView;

import java.io.File;
import java.io.IOException;

public class MainPresenter implements MvpPresenter {

    private MvpView mvpView;

    public interface ExportFileCallback {

        void onExportVideoSuccess();

        void onExportVideoFail();
    }

    @Override
    public void setView(MvpView mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void handlePrintLessonList(File projectFolder) {

        if (!isFolderValid(projectFolder))
            return;

        createLessonList(projectFolder);
    }

    @Override
    public void handlePrintNumberedLessonList(File projectFolder) {

        if (!isFolderValid(projectFolder))
            return;

        createNumberedLessonList(projectFolder);
    }

    @Override
    public void handleExportVideos(File projectFolder) {

        if (!isFolderValid(projectFolder))
            return;

        exportVideos(projectFolder);
    }

    @Override
    public void handleAudioCutOff(File soundFolder) {

        if (!isFolderValid(soundFolder))
            return;

        AudioCutOffProcessor audioCutOffProcessor = new AudioCutOffProcessor();
        audioCutOffProcessor.processAllAudioFiles(soundFolder);
    }

    @Override
    public void handleJoinAudioFiles(File soundsFolder) {

        if (!isFolderValid(soundsFolder))
            return;

        AudioJoiner audioJoiner = new AudioJoiner();
        audioJoiner.process(soundsFolder);

        mvpView.showJoinAudioFilesSuccess();
    }

    @Override
    public void handleCutoffAndJoinFiles(File soundFolder) {

        if (!isFolderValid(soundFolder))
            return;

        handleAudioCutOff(soundFolder);
        File cutoffFolder = new File(soundFolder + "-cutoff");
        handleJoinAudioFiles(cutoffFolder);
    }

    @Override
    public void handleSetupLesson(File lessonFolder) {

        if (!isFolderValid(lessonFolder))
            return;

        LessonInitializer lessonInitializer = new LessonInitializer();
        lessonInitializer.setupLesson(lessonFolder);
    }

    @Override
    public void handleCreateFoldersFromFile(File courseStructureFile) {

        CourseFoldersCreator courseFoldersCreator = new CourseFoldersCreator();
        courseFoldersCreator.createFoldersFromFile(courseStructureFile);
    }

    @Override
    public void handleConvertPdfToPng(File pdfFile) {

        // todo validate pdf file

        PdfConverter pdfConverter = new PdfConverter();
        pdfConverter.convert(pdfFile);

        // todo return pdf conversion success or fail
    }

    private void exportVideos(File projectFolder) {

        if(!isFolderValid(projectFolder))
            return;

        try {
            VideoExporter videoExporter = new VideoExporter();
            videoExporter.exportFiles(projectFolder, new ExportFileCallback() {
                @Override
                public void onExportVideoSuccess() {
                    mvpView.showExportVideoSuccess();
                }

                @Override
                public void onExportVideoFail() {
                    mvpView.showExportVideoFail();
                }
            });

        } catch (NoLessonsFolderException ex) {

            mvpView.showNotLessonsFolder();
        }
    }

    private void createNumberedLessonList(File projectFolder) {

        if(!isFolderValid(projectFolder))
            return;

        try {
            NumberedLessonMaker numberedLessonMaker = new NumberedLessonMaker();
            numberedLessonMaker.printNumberedLessons(projectFolder);

            mvpView.showPrintLessonListSuccess();

        } catch (NoLessonsFolderException ex) {

            mvpView.showNotLessonsFolder();
        }
    }

    private void createLessonList(File projectFolder) {

        if(!isFolderValid(projectFolder))
            return;

        try {
            LessonListMaker lessonListMaker = new LessonListMaker();
            lessonListMaker.printContents(projectFolder);

            mvpView.showPrintLessonListSuccess();

        } catch (NoLessonsFolderException ex) {

            mvpView.showNotLessonsFolder();
        }
    }

    private boolean isFolderValid(File folder) {

        if (folder == null) {
            mvpView.showNoFolderSelected();
            return false;
        }

        return true;
    }
}
