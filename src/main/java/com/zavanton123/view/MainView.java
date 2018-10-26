package com.zavanton123.view;

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

import java.io.File;

public class MainView extends Application implements MvpView {

    private static final String DESKTOP = "/home/zavanton/Desktop";
    public static final int MIN_BUTTON_WIDTH = 500;
    public static final int MIN_BUTTON_HEIGHT = 50;
    public static final int VERTICAL_BOX_SPACING = 25;
    public static final int SCENE_WIDTH = 960;
    public static final int SCENE_HEIGHT = 700;
    public static final String TITLE = "Skill Centric Utilities ";

    MvpPresenter presenter;

    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        presenter = new MainPresenter();
        presenter.setView(this);

        fileChooser = createFileChooser();
        directoryChooser = createDirectoryChooser();

        Button lessonListButton =
                setupLessonListButton(primaryStage);

        Button numberedLessonListButton =
                setupNumberedLessonListButton(primaryStage);

        Button renderVideoButton =
                setupRenderVideoButton(primaryStage);

        Button cutoffJoinButton =
                setupCutoffJoinButton(primaryStage);

        Button lessonSetupButton =
                setupLessonSetupButton(primaryStage);

        Button createFoldersFromFileButton =
                setupCreateFoldersFromFileButton(primaryStage);

        Button convertPdfToPngButton =
                setupConvertPdfToPngButton(primaryStage);

        Button makePdfAndPngButton = setupMakePdfAndPngButton(primaryStage);

        Button exportSlidesButton = setupExportAssetsButton(primaryStage);

        Scene scene = setupScene(lessonListButton,
                numberedLessonListButton,
                renderVideoButton,
                cutoffJoinButton,
                lessonSetupButton,
                createFoldersFromFileButton,
                convertPdfToPngButton,
                makePdfAndPngButton,
                exportSlidesButton);

        setupPrimaryStage(primaryStage, scene);
    }

    private Button setupExportAssetsButton(Stage primaryStage) {

        Button exportAssetsButton = new Button("Export Assets");
        exportAssetsButton.setMinWidth(MIN_BUTTON_WIDTH);
        exportAssetsButton.setMinHeight(MIN_BUTTON_HEIGHT);
        exportAssetsButton.setOnAction(e -> {
            directoryChooser.setTitle("Choose the lesson folder");
            File lessonFolder = directoryChooser.showDialog(primaryStage);
            presenter.handleExportSlidesButton(lessonFolder);
        });
        return exportAssetsButton;
    }

    private Button setupMakePdfAndPngButton(Stage primaryStage) {

        Button makePdfAndPngButton = new Button("Make PDF and PNG from PPT");
        makePdfAndPngButton.setMinWidth(MIN_BUTTON_WIDTH);
        makePdfAndPngButton.setMinHeight(MIN_BUTTON_HEIGHT);
        makePdfAndPngButton.setOnAction(e -> {


            File slidesFile = fileChooser.showOpenDialog(primaryStage);

            presenter.handleMakePdfAndPng(slidesFile);
        });
        return makePdfAndPngButton;
    }

    private Button setupConvertPdfToPngButton(Stage primaryStage) {

        Button convertPdfToPngButton = new Button("Convert PDF to PNG");
        convertPdfToPngButton.setMinWidth(MIN_BUTTON_WIDTH);
        convertPdfToPngButton.setMinHeight(MIN_BUTTON_HEIGHT);
        convertPdfToPngButton.setOnAction(e -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose the PDF folder");
            fileChooser.setInitialDirectory(new File(DESKTOP));
            File pdfFile = fileChooser.showOpenDialog(primaryStage);

            presenter.handleConvertPdfToPng(pdfFile);
        });
        return convertPdfToPngButton;
    }

    private Button setupCreateFoldersFromFileButton(Stage primaryStage) {

        Button createFoldersFromFileButton = new Button("Create Folders");
        createFoldersFromFileButton.setMinWidth(MIN_BUTTON_WIDTH);
        createFoldersFromFileButton.setMinHeight(MIN_BUTTON_HEIGHT);
        createFoldersFromFileButton.setOnAction(e -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose the Text File with Course Structure");
            fileChooser.setInitialDirectory(new File(DESKTOP));
            File courseStructureFile = fileChooser.showOpenDialog(primaryStage);

            presenter.handleCreateFoldersFromFile(courseStructureFile);
        });
        return createFoldersFromFileButton;
    }

    private Button setupLessonSetupButton(Stage primaryStage) {
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

    private Button setupCutoffJoinButton(Stage primaryStage) {
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

    private Button setupLessonListButton(Stage primaryStage) {
        Button lessonListButton = new Button("Print Lesson List");
        lessonListButton.setMinWidth(MIN_BUTTON_WIDTH);
        lessonListButton.setMinHeight(MIN_BUTTON_HEIGHT);

        lessonListButton.setOnAction(e -> {
            File projectFolder = directoryChooser.showDialog(primaryStage);
            presenter.handlePrintLessonList(projectFolder);
        });
        return lessonListButton;
    }

    private Button setupNumberedLessonListButton(Stage primaryStage) {
        Button numberedLessonListButton = new Button("Print Numbered Lessons");
        numberedLessonListButton.setMinWidth(MIN_BUTTON_WIDTH);
        numberedLessonListButton.setMinHeight(MIN_BUTTON_HEIGHT);
        numberedLessonListButton.setOnAction(e -> {
            File projectFolder = directoryChooser.showDialog(primaryStage);
            presenter.handlePrintNumberedLessonList(projectFolder);
        });
        return numberedLessonListButton;
    }

    private Button setupRenderVideoButton(Stage primaryStage) {

        Button renderVideoButton = new Button("Render Video");
        renderVideoButton.setMinWidth(MIN_BUTTON_WIDTH);
        renderVideoButton.setMinHeight(MIN_BUTTON_HEIGHT);

        renderVideoButton.setOnAction(e -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose kdenlive file");
            File kdenliveFile = fileChooser.showOpenDialog(primaryStage);
            presenter.handlerRenderVideo(kdenliveFile);
        });
        return renderVideoButton;
    }

    private Scene setupScene(Button lessonListButton,
                             Button numberedLessonListButton,
                             Button exportVideoButton,
                             Button cutoffJoinButton,
                             Button lessonSetupButton,
                             Button createFoldersFromFileButton,
                             Button convertPdfToPngButton,
                             Button makePdfAndPngButton,
                             Button exportSlidesButton) {

        VBox vBox = new VBox(lessonListButton,
                numberedLessonListButton,
                exportVideoButton,
                cutoffJoinButton,
                lessonSetupButton,
                createFoldersFromFileButton,
                convertPdfToPngButton,
                makePdfAndPngButton,
                exportSlidesButton);

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

    @Override
    public void showNotValidPdfFile() {

        // TODO
        System.out.println("Not a valid pdf folder");
    }

    @Override
    public void showPdfToPngConversionSuccess() {

        // TODO
        System.out.println("Converted PDF to PNG!");
    }

    @Override
    public void showCreatePdfSuccess() {

        // TODO
        System.out.println("PDF is created!");
    }

    @Override
    public void showCreatePdfFail() {

        // TODO
        System.out.println("Failed to create PDF");
    }

    @Override
    public void showNotSlidesFile() {

        // TODO
        System.out.println("Not a Presentation File");
    }

    @Override
    public void showPdfToPngConversionFail() {

        // TODO
        System.out.println("Failed to Convert PDF to PNG");
    }

    private FileChooser createFileChooser() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        fileChooser.setInitialDirectory(new File(DESKTOP));
        return fileChooser;
    }

    private DirectoryChooser createDirectoryChooser() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose a Folder");
        directoryChooser.setInitialDirectory(new File(DESKTOP));
        return directoryChooser;
    }
}
