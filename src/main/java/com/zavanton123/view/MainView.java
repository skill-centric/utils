package com.zavanton123.view;


import java.io.File;

import com.zavanton123.presenter.MainPresenter;
import com.zavanton123.presenter.MvpPresenter;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainView extends Application implements MvpView {

    private static final String DESKTOP = "/home/zavanton/Desktop";
    public static final int MIN_BUTTON_WIDTH = 500;
    public static final int MIN_BUTTON_HEIGHT = 50;
    public static final int VERTICAL_BOX_SPACING = 25;
    public static final int SCENE_WIDTH = 960;
    public static final int SCENE_HEIGHT = 600;
    public static final String TITLE = "Skill Centric Utilities ";

    MvpPresenter presenter;

    @Override
    public void start(Stage primaryStage) throws Exception {

        presenter = new MainPresenter();
        presenter.setView(this);

        DirectoryChooser directoryChooser = createDirectoryChooser();

        Button lessonListButton =
                setupLessonListButton(primaryStage, directoryChooser);

        Button numberedLessonListButton =
                setupNumberedLessonListButton(primaryStage, directoryChooser);

        Button exportVideoButton =
                setupExportVideoButton(primaryStage, directoryChooser);

        Button cutOffAudioButton =
                setupCutOffAudioButton(primaryStage, directoryChooser);

        Button joinAudioButton =
                setupJoinAudioButton(primaryStage, directoryChooser);

        Button cutoffJoinButton =
                setupCutoffJoinButton(primaryStage, directoryChooser);

        Button lessonSetupButton =
                setupLessonSetupButton(primaryStage, directoryChooser);

        Button createFoldersFromFileButton =
                setupCreateFoldersFromFileButton(primaryStage);

        Scene scene = setupScene(lessonListButton,
                numberedLessonListButton,
                exportVideoButton,
                cutOffAudioButton,
                joinAudioButton,
                cutoffJoinButton,
                lessonSetupButton,
                createFoldersFromFileButton);

        setupPrimaryStage(primaryStage, scene);
    }

    private Button setupCreateFoldersFromFileButton(Stage primaryStage) {

        Button createFoldersFromFileButton = new Button("Create Folders");
        createFoldersFromFileButton.setMinWidth(MIN_BUTTON_WIDTH);
        createFoldersFromFileButton.setMinHeight(MIN_BUTTON_HEIGHT);
        createFoldersFromFileButton.setOnAction(e -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose the Text File with Course Structure");
            File courseStructureFile = fileChooser.showOpenDialog(primaryStage);

            presenter.handleCreateFoldersFromFile(courseStructureFile);
        });
        return createFoldersFromFileButton;
    }

    private Button setupLessonSetupButton(Stage primaryStage, DirectoryChooser directoryChooser) {
        Button lessonSetupButton = new Button("Setup Lesson");
        lessonSetupButton.setMinWidth(MIN_BUTTON_WIDTH);
        lessonSetupButton.setMinHeight(MIN_BUTTON_HEIGHT);
        lessonSetupButton.setOnAction(e -> {
            directoryChooser.setTitle("Choose the lesson folder");
            File lessonFolder = directoryChooser.showDialog(primaryStage);
            presenter.handleSetupLesson(lessonFolder);
        });
        return lessonSetupButton;
    }

    private Button setupJoinAudioButton(Stage primaryStage, DirectoryChooser directoryChooser) {
        Button joinAudioButton = new Button("Join Audio Files");
        joinAudioButton.setMinWidth(MIN_BUTTON_WIDTH);
        joinAudioButton.setMinHeight(MIN_BUTTON_HEIGHT);
        joinAudioButton.setOnAction(e -> {
            directoryChooser.setTitle("Choose the lesson folder containing sounds directory");
            File lessonFolder = directoryChooser.showDialog(primaryStage);
            presenter.handleJoinAudioFiles(lessonFolder);
        });
        return joinAudioButton;
    }

    private Button setupCutoffJoinButton(Stage primaryStage, DirectoryChooser directoryChooser) {
        Button joinAudioButton = new Button("Cut Off + Join Audio Files");
        joinAudioButton.setMinWidth(MIN_BUTTON_WIDTH);
        joinAudioButton.setMinHeight(MIN_BUTTON_HEIGHT);
        joinAudioButton.setOnAction(e -> {
            directoryChooser.setTitle("Choose the sound folder containing sounds directory");
            File soundFolder = directoryChooser.showDialog(primaryStage);
            presenter.handleCutoffAndJoinFiles(soundFolder);
        });
        return joinAudioButton;
    }

    private Button setupCutOffAudioButton(Stage primaryStage, DirectoryChooser directoryChooser) {
        Button cutOffAudioButton = new Button("Cut Off Audio Files");
        cutOffAudioButton.setMinWidth(MIN_BUTTON_WIDTH);
        cutOffAudioButton.setMinHeight(MIN_BUTTON_HEIGHT);
        cutOffAudioButton.setOnAction(e -> {
            File lessonFolder = directoryChooser.showDialog(primaryStage);
            directoryChooser.setTitle("Choose the lesson folder containing sounds directory");
            presenter.handleAudioCutOff(lessonFolder);
        });
        return cutOffAudioButton;
    }

    private DirectoryChooser createDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Project Folder");
        directoryChooser.setInitialDirectory(new File(DESKTOP));
        return directoryChooser;
    }

    private Button setupLessonListButton(Stage primaryStage, DirectoryChooser directoryChooser) {
        Button lessonListButton = new Button("Print Lesson List");
        lessonListButton.setMinWidth(MIN_BUTTON_WIDTH);
        lessonListButton.setMinHeight(MIN_BUTTON_HEIGHT);

        lessonListButton.setOnAction(e -> {
            File projectFolder = directoryChooser.showDialog(primaryStage);
            presenter.handlePrintLessonList(projectFolder);
        });
        return lessonListButton;
    }

    private Button setupNumberedLessonListButton(Stage primaryStage, DirectoryChooser directoryChooser) {
        Button numberedLessonListButton = new Button("Print Numbered Lessons");
        numberedLessonListButton.setMinWidth(MIN_BUTTON_WIDTH);
        numberedLessonListButton.setMinHeight(MIN_BUTTON_HEIGHT);
        numberedLessonListButton.setOnAction(e -> {
            File projectFolder = directoryChooser.showDialog(primaryStage);
            presenter.handlePrintNumberedLessonList(projectFolder);
        });
        return numberedLessonListButton;
    }

    private Button setupExportVideoButton(Stage primaryStage, DirectoryChooser directoryChooser) {

        Button exportVideoButton = new Button("Export Video");
        exportVideoButton.setMinWidth(MIN_BUTTON_WIDTH);
        exportVideoButton.setMinHeight(MIN_BUTTON_HEIGHT);

        exportVideoButton.setOnAction(e -> {
            File projectFolder = directoryChooser.showDialog(primaryStage);
            presenter.handleExportVideos(projectFolder);
        });
        return exportVideoButton;
    }

    private Scene setupScene(Button lessonListButton,
                             Button numberedLessonListButton,
                             Button exportVideoButton, Button cutOffAudioButton,
                             Button joinAudioButton,
                             Button cutoffJoinButton,
                             Button lessonSetupButton,
                             Button createFoldersFromFileButton) {

        VBox vBox = new VBox(lessonListButton,
                numberedLessonListButton,
                exportVideoButton,
                cutOffAudioButton,
                joinAudioButton,
                cutoffJoinButton,
                lessonSetupButton,
                createFoldersFromFileButton);

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(VERTICAL_BOX_SPACING);
        return new Scene(vBox, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private void setupPrimaryStage(Stage primaryStage, Scene scene) {

        String version = getClass().getPackage().getImplementationVersion();

        primaryStage.setScene(scene);
        primaryStage.setTitle(TITLE + version);
        primaryStage.show();

        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void showNoFolderSelected() {

        // TODO
        System.out.println("No Folder has been selected!");
    }

    @Override
    public void showPrintLessonListSuccess() {

        // TODO
        System.out.println("Contents List is printed!");
    }

    @Override
    public void showNotLessonsFolder() {

        // TODO
        System.out.println("Not a Lessons Folder!");
    }

    @Override
    public void showExportVideoSuccess() {

        // TODO
        System.out.println("Videos are exported");
    }

    @Override
    public void showExportVideoFail() {

        // TODO
        System.out.println("Failed to export videos!");
    }

    @Override
    public void showJoinAudioFilesSuccess() {

        // TODO
        System.out.println("Audio Files Are Joined!");
    }
}
