package com.zavanton123.model.video;


import com.zavanton123.presenter.MainPresenter;
import com.zavanton123.utils.NoLessonsFolderException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class VideoExporter {

    public void exportFiles(File projectFolder,
                            MainPresenter.ExportFileCallback exportFileCallback) {

        try {
            exportVideosConcurrently(projectFolder, exportFileCallback);

        } catch (Exception e) {
            exportFileCallback.onExportVideoFail();
        }
    }

    private void exportVideosConcurrently(File projectFolder,
                                          MainPresenter.ExportFileCallback exportFileCallback) {

        Thread thread = new Thread(() -> {

            exportVideos(projectFolder);
            exportFileCallback.onExportVideoSuccess();

        });

        thread.start();
    }

    private void exportVideos(File projectFolder) {

        boolean hasLessonsFolder = checkContainsLessons(projectFolder);
        if (!hasLessonsFolder) {
            throw new NoLessonsFolderException();
        }

        File videoFolder = setupVideoFolder(projectFolder);
        File lessonsFolder = new File(projectFolder.getAbsolutePath() + "/Lessons");
        File[] sectionFolders = lessonsFolder.listFiles();
        Arrays.sort(sectionFolders);

        int sectionCount = 1;

        for (File sectionFolder : sectionFolders) {

            if (sectionFolder.isDirectory())
                processSectionFolder(sectionFolder, sectionCount, videoFolder);

            sectionCount++;
        }
    }

    private File setupVideoFolder(File projectFolder) {
        File videoFolder = new File(projectFolder.getAbsolutePath() + "/Video");
        if (!videoFolder.exists()) {
            videoFolder.mkdirs();
        }
        return videoFolder;
    }

    private boolean checkContainsLessons(File projectFolder) {
        File[] files = projectFolder.listFiles();
        for (File file : files) {
            if (file.isDirectory() && file.getName().equals("Lessons")) {
                return true;
            }
        }
        return false;
    }

    private static void processSectionFolder(File sectionFolder,
                                             int sectionCount,
                                             File videoFolder) {

        File[] lessonFolders = sectionFolder.listFiles();
        Arrays.sort(lessonFolders);

        int lessonCount = 1;

        for (File lessonFolder : lessonFolders) {

            if (lessonFolder.isDirectory()) {

                processLessonFolder(lessonFolder, sectionCount, lessonCount, videoFolder);
            }

            lessonCount++;
        }
    }

    private static void processLessonFolder(File lessonFolder,
                                            int sectionCount,
                                            int lessonCount,
                                            File videoFolder) {

        File[] files = lessonFolder.listFiles();

        for (File file : files) {

            if (file.getName().contains(".mp4")) {

                try {

                    String fileName = videoFolder.getAbsolutePath() + "/"
                            + sectionCount
                            + "." + lessonCount
                            + ". " + file.getName();

                    File targetFile = new File(fileName);

                    Files.copy(file.toPath(),
                            targetFile.toPath(),
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
