package com.zavanton123.model.lesson_list;


import com.zavanton123.utils.NoLessonsFolderException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class NumberedLessonMaker {

    public void printNumberedLessons(File projectFolder) {

        StringBuilder builder = new StringBuilder();

        boolean hasLessonsFolder = checkContainsLessons(projectFolder);
        if (!hasLessonsFolder) {
            throw new NoLessonsFolderException();
        }
        File folder = new File(projectFolder.getAbsolutePath() + "/Lessons");
        File[] files = folder.listFiles();

        Arrays.sort(files);

        int count = 1;

        for (File file : files) {

            if (file.isDirectory()) {

                builder.append(file.getName());
                builder.append("\n");

                printDirectory(file, builder, count);
            }

            count++;
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

    private static void printDirectory(File folder, StringBuilder builder, int count) {

        File[] files = folder.listFiles();

        Arrays.sort(files);

        for (File file : files) {

            String fileName = file.getName();

            if (file.isDirectory() && fileName.contains(".")) {

                builder.append(count);
                builder.append(".");
                builder.append(fileName);
                builder.append("\n");
            }
        }
    }

    private static void writeToFile(StringBuilder builder, File projectFolder) {
        File contentsFile = new File(projectFolder.getAbsolutePath() + "/NumberedLessonList.txt");

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