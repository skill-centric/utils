package com.zavanton123.model.lessonList;


import com.zavanton123.utils.NoLessonsFolderException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class LessonListMaker {

    public void printContents(File projectFolder) {

        StringBuilder builder = new StringBuilder();

        boolean hasLessonsFolder = checkContainsLessons(projectFolder);
        if (!hasLessonsFolder) {
            throw new NoLessonsFolderException();
        }

        File folder = new File(projectFolder.getAbsolutePath() + "/Lessons");
        File[] files = folder.listFiles();

        Arrays.sort(files);

        for (File file : files) {

            if (file.isDirectory()) {

                builder.append(file.getName());
                builder.append("\n");

                printDirectory(file, builder);
            }
        }

        System.out.println(builder.toString());

        writeToFile(builder, projectFolder);
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

    private void printDirectory(File folder, StringBuilder builder) {

        File[] files = folder.listFiles();

        Arrays.sort(files);

        for (File file : files) {

            String fileName = file.getName();

            if (file.isDirectory() && fileName.contains(".")) {

                builder.append(" - ");
                builder.append(fileName);
                builder.append("\n");
            }
        }
    }

    private void writeToFile(StringBuilder builder, File projectFolder) {
        File contentsFile = new File(projectFolder.getAbsolutePath() + "/LessonList.txt");

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(contentsFile);
            printWriter.write(builder.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
    }
}
