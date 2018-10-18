package com.zavanton123.presenter;


import com.zavanton123.view.MvpView;

import java.io.File;

public interface MvpPresenter {
    void setView(MvpView mvpView);

    void handlePrintLessonList(File projectFolder);

    void handlePrintNumberedLessonList(File projectFolder);

    void handleExportVideos(File projectFolder);

    void handleAudioCutOff(File lessonFolder);

    void handleJoinAudioFiles(File lessonFolder);

    void handleCutoffAndJoinFiles(File soundFolder);
}
