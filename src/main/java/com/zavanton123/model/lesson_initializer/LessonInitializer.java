package com.zavanton123.model.lesson_initializer;

import java.io.*;

public class LessonInitializer {

    public void setupLesson(File lessonFolder) {

        File wip = createWipFolder(lessonFolder, "WIP");
        File noiseFile = createNoiseFile(wip,
                "noise_5_min.wav",
                "noise.wav");

        String lessonName = getLessonName(lessonFolder);
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
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            inputStream = getClass().getClassLoader()
                    .getResourceAsStream(resourceName);

            bufferedInputStream = new BufferedInputStream(inputStream);

            byte[] buffer = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(buffer);

            fileOutputStream = new FileOutputStream(noiseFile);
            fileOutputStream.write(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                bufferedInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return noiseFile;
    }

    private String getLessonName(File lessonFolder) {
        String lessonFolderName = lessonFolder.getName();
        String[] splitName = lessonFolderName.split(". ", 2);
        return splitName[1];
    }
}
