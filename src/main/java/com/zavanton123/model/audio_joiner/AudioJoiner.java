package com.zavanton123.model.audio_joiner;

import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioJoiner {

    public void process(File soundsFolder) {

        try {

            File targetFolder = new File(soundsFolder.getParent(),
                    soundsFolder.getName() + "-joined");

            if (!targetFolder.exists()) {
                targetFolder.mkdirs();
            }

            File noiseFile = new File(getClass().getClassLoader()
                    .getResource("noise_one_sec.wav").getFile());

            File[] files = soundsFolder.listFiles();
            for (File file : files) {

                if (file.getName().endsWith(".wav") && !file.equals(noiseFile)) {

                    AudioInputStream noiseClip = AudioSystem.getAudioInputStream(noiseFile);
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

    private  File findNoiseFile(File sourceFolder) {

        File[] files = sourceFolder.listFiles();
        for (File file : files) {

            if (file.getName().endsWith(".wav") && file.getName().contains("noise")) {

                return file;
            }
        }

        return null;
    }
}