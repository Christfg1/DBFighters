package com.pluralsight.services;

import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;

public class SoundService {

    private static HashMap<String, Clip> activeSounds = new HashMap<>();

    public static void playSound(String soundName) {
        stopSound(soundName);

        try {
            File soundFile = new File("sounds/" + soundName + ".wav");

            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            activeSounds.put(soundName, clip);

        } catch (Exception e) {
            System.out.println("Could not play sound: " + soundName);
        }
    }

    public static void playSoundAndWait(String soundName) {
        stopSound(soundName);

        try {
            File soundFile = new File("sounds/" + soundName + ".wav");

            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            activeSounds.put(soundName, clip);

            Thread.sleep(clip.getMicrosecondLength() / 1000);

            clip.stop();
            clip.close();
            activeSounds.remove(soundName);

        } catch (Exception e) {
            System.out.println("Could not play sound: " + soundName);
        }
    }

    public static void playLoop(String soundName) {
        if (activeSounds.containsKey(soundName)) {
            return;
        }

        try {
            File soundFile = new File("sounds/" + soundName + ".wav");

            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            activeSounds.put(soundName, clip);

        } catch (Exception e) {
            System.out.println("Could not loop sound: " + soundName);
        }
    }

    public static void stopSound(String soundName) {
        if (activeSounds.containsKey(soundName)) {
            Clip clip = activeSounds.get(soundName);

            clip.stop();
            clip.close();

            activeSounds.remove(soundName);
        }
    }

    public static void stopTransformationSounds() {
        stopSound("gokuultrainstinct");
        stopSound("vegetatransforms");
        stopSound("brolytransforms");
        stopSound("friezatransforms");
        stopSound("piccolotransforms");
        stopSound("piccoloauraforming");
    }

    public static void stopAllSounds() {
        for (Clip clip : activeSounds.values()) {
            clip.stop();
            clip.close();
        }

        activeSounds.clear();
    }
}