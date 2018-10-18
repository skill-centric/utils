package com.zavanton123.presenter;


import com.zavanton123.model.audio_joiner.AudioJoiner;
import com.zavanton123.model.audio_splitter.AudioCutOffProcessor;
import com.zavanton123.model.lesson_list.LessonListMaker;
import com.zavanton123.model.lesson_list.NumberedLessonMaker;
import com.zavanton123.model.video.VideoExporter;
import com.zavanton123.utils.NoLessonsFolderException;
import com.zavanton123.view.MvpView;

import java.io.File;

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

        validateFolder(projectFolder);

        if (projectFolder != null)
            createLessonList(projectFolder);
    }

    @Override
    public void handlePrintNumberedLessonList(File projectFolder) {

        validateFolder(projectFolder);

        if (projectFolder != null)
            createNumberedLessonList(projectFolder);
    }

    @Override
    public void handleExportVideos(File projectFolder) {

        validateFolder(projectFolder);

        if (projectFolder != null) {
            exportVideos(projectFolder);
        }
    }

    @Override
    public void handleAudioCutOff(File soundFolder) {

        // TODO process null folder

        AudioCutOffProcessor audioCutOffProcessor = new AudioCutOffProcessor();
        audioCutOffProcessor.processAllAudioFiles(soundFolder);
    }

    @Override
    public void handleJoinAudioFiles(File lessonFolder) {

        // TODO process null folder

        AudioJoiner audioJoiner = new AudioJoiner();
        audioJoiner.process(lessonFolder);

        mvpView.showJoinAudioFilesSuccess();
    }

    private void exportVideos(File projectFolder) {

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

        try {
            NumberedLessonMaker numberedLessonMaker = new NumberedLessonMaker();
            numberedLessonMaker.printNumberedLessons(projectFolder);

            mvpView.showPrintLessonListSuccess();

        } catch (NoLessonsFolderException ex) {

            mvpView.showNotLessonsFolder();
        }
    }

    private void createLessonList(File projectFolder) {

        try {
            LessonListMaker lessonListMaker = new LessonListMaker();
            lessonListMaker.printContents(projectFolder);

            mvpView.showPrintLessonListSuccess();

        } catch (NoLessonsFolderException ex) {

            mvpView.showNotLessonsFolder();
        }
    }

    private void validateFolder(File projectFolder) {
        if (projectFolder == null)
            mvpView.showNoFolderSelected();
    }
}
