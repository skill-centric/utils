package com.zavanton123.model.audioSplitter;

import java.io.File;

public class AudioCutOffProcessor {

    public void processAllAudioFiles(File soundsFolder) {

        int cutOffDuration = getCutOffDuration();

        File projectFolder = soundsFolder.getParentFile();
        processSoundsFolder(cutOffDuration, soundsFolder, projectFolder);

        System.out.println("Success!");
    }

    private int getCutOffDuration() {
//        System.out.print("Please, enter the number of milliseconds to cut off: ");
//        Scanner scanner = new Scanner(System.in);
//        return scanner.nextInt();

        // TODO
        return 200;
    }

    private void processSoundsFolder(int cutOffDuration, File soundsFolder, File projectFolder) {

        File[] files = soundsFolder.listFiles();
        for (File file : files) {

            if (file.getName().endsWith(".wav")) {
                processAudio(cutOffDuration, file, soundsFolder, projectFolder);
            }
        }
    }

    private void processAudio(int millesec, File soundFile, File soundsFolder, File projectFolder) {

        String resultFolderName = soundsFolder.getName() + "-cutoff";
        File targetDir = new File(projectFolder.getAbsolutePath(), resultFolderName);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        try {
            // Get the wave file from the embedded resources
            WavFile inputWavFile = WavFile.openWavFile(soundFile);

            // Get the number of audio channels in the wav file
            int numChannels = inputWavFile.getNumChannels();

            // set the maximum number of frames for a target file,
            // based on the number of milliseconds assigned for each file
            int frames = (int) inputWavFile.getSampleRate() * millesec / 1000;

            // Create a buffer of maxFramesPerFile frames
            double[] buffer = new double[(int) (inputWavFile.getNumFrames() * numChannels)];

            inputWavFile.readFrames(buffer, frames);

            // Read frames into buffer
            int framesRead = inputWavFile.readFrames(buffer,
                    (int) (inputWavFile.getNumFrames() - frames - (frames / 4)));
            WavFile secondWavFile = WavFile.newWavFile(
                    new File(targetDir, soundFile.getName()),
                    inputWavFile.getNumChannels(),
                    framesRead,
                    inputWavFile.getValidBits(),
                    inputWavFile.getSampleRate());


            // Write the buffer
            secondWavFile.writeFrames(buffer, framesRead);
            secondWavFile.close();

            // Close the input file
            inputWavFile.close();

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
