package com.zavanton123.model.lesson_initializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LessonInitializer {

    public void setupLesson(File lessonFolder) {

        File wip = createWipFolder(lessonFolder, "WIP");
        File noiseFile = createNoiseFile(wip,
                "noise_5_min.wav",
                "noise.wav");

    }

    private File createWipFolder(File lessonFolder, String folderName) {

        File wip = new File(lessonFolder, folderName);
        if (!wip.exists()) {
            wip.mkdirs();
        }
        return wip;
    }

    private File createNoiseFile(File wip, String resourceName, String fileName) {
        File noiseFile = new File(wip, fileName);

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            inputStream = getClass().getClassLoader()
                    .getResourceAsStream(resourceName);

            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            fileOutputStream = new FileOutputStream(noiseFile);
            fileOutputStream.write(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                inputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return noiseFile;
    }
}
