package com.zavanton123.model.audioJoiner;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

public class AudioJoiner {

    public void process(File soundsFolder) {

        try {

            File targetFolder = new File(soundsFolder.getParent(),
                    soundsFolder.getName() + "-joined");

            if (!targetFolder.exists()) {
                targetFolder.mkdirs();
            }

            String noiseFileName = "noise_one_sec.wav";

            File[] files = soundsFolder.listFiles();
            for (File file : files) {

                if (file.getName().endsWith(".wav")) {

                    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(noiseFileName);
                    AudioInputStream noiseClip = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
                    AudioInputStream audioClip = AudioSystem.getAudioInputStream(file);

                    AudioInputStream joinedStream = new AudioInputStream(
                            new SequenceInputStream(audioClip, noiseClip),
                            audioClip.getFormat(),
                            audioClip.getFrameLength() + noiseClip.getFrameLength());

                    AudioSystem.write(joinedStream,
                            AudioFileFormat.Type.WAVE, new File(targetFolder.getAbsolutePath() + "/" +
                                    "joined-" + file.getName()));
                    noiseClip.close();
                    joinedStream.close();
                }

            }
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private File findNoiseFile(File sourceFolder) {

        File[] files = sourceFolder.listFiles();
        for (File file : files) {

            if (file.getName().endsWith(".wav") && file.getName().contains("noise")) {

                return file;
            }
        }

        return null;
    }
}