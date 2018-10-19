package com.zavanton123.model.lesson_initializer;

import java.io.File;

public class LessonInitializer {

    public void setupLesson(File lessonFolder) {

        File wip = createWipFolder(lessonFolder, "WIP");



    }

    private File createWipFolder(File lessonFolder, String folderName) {

        File wip = new File(lessonFolder, folderName);
        if (!wip.exists()) {
            wip.mkdirs();
        }
        return wip;
    }
}
