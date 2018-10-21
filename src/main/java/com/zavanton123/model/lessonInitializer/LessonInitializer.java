package com.zavanton123.model.lessonInitializer;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class LessonInitializer {

    public void setupLesson(File lessonFolder) {

        new Thread(() -> createFoldersAndFiles(lessonFolder)).start();
    }

    private void createFoldersAndFiles(File lessonFolder) {
        File wip = createWipFolder(lessonFolder, "WIP");

        File noiseFile = copyResourceToFolder(wip,
                "noise_5_min.wav",
                "noise_5_min.wav");

        String lessonName = getLessonName(lessonFolder);

        String kdenliveTemplate = readKdenliveTemplate();

        String lessonTemplate = getLessonTemplate(kdenliveTemplate,
                "/home/zavanton/Desktop",
                lessonFolder,
                "WIP/noise_5_min.wav",
                noiseFile
        );

        storeLessonTemplate(lessonName, lessonFolder, lessonTemplate);

        File slidesTemplateFile = copyResourceToFolder(lessonFolder,
                "slide-template.pptx",
                lessonName + " - Slides.pptx");
    }

    private void storeLessonTemplate(String lessonName,
                                     File lessonFolder,
                                     String lessonTemplate) {

        File targetFile = new File(lessonFolder, lessonName + " - kdenlive.kdenlive");

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(targetFile);
            printWriter.write(lessonTemplate);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
    }

    private String getLessonTemplate(String kdenliveTemplate,
                                     String oldRootPath,
                                     File lessonFolder,
                                     String oldAudioPath,
                                     File noiseFile) {

        String oldRoot = "root=\"" + oldRootPath + "\"";
        String targetRoot = "root=\"" + lessonFolder.getAbsolutePath() + "\"";

        String oldAudio = "<property name=\"resource\">" + oldAudioPath + "</property>";
        String targetAudio = "<property name=\"resource\">" + noiseFile.getPath() + "</property>";

        String result = kdenliveTemplate.replace(oldRoot, targetRoot);
        result = result.replace(oldAudio, targetAudio);

        return result;
    }

    private String readKdenliveTemplate() {
        InputStream is = getClass().getClassLoader()
                .getResourceAsStream("template.kdenlive");

        BufferedInputStream bis = new BufferedInputStream(is);

        StringWriter stringWriter = new StringWriter();
        String encoding = null;
        String result = null;
        try {
            IOUtils.copy(bis, stringWriter, encoding);
            result = stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private File createWipFolder(File lessonFolder, String folderName) {

        File wip = new File(lessonFolder, folderName);
        if (!wip.exists()) {
            wip.mkdirs();
        }
        return wip;
    }

    private File copyResourceToFolder(File targetFolder,
                                      String resourceName,
                                      String targetFileName) {

        File noiseFile = new File(targetFolder, targetFileName);

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
