package com.bsodsoftware.abraxas.engine.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioEngine {

    private final boolean MUTE = true;

    public enum STATUS {
        PLAYING, STOPPED
    }

    private Clip clip;
    private STATUS status;
    private AudioInputStream audioInputStream;
    private String location;
    private boolean loop;

    public AudioEngine(String location, boolean loop) {
        if (!MUTE) {
            this.location = location;
            this.loop = loop;
            this.load();
        }
        this.status = STATUS.STOPPED;
    }

    private void load() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(this.location));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if (this.loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void play() {
        if (!MUTE) {
            this.clip.start();
            this.status = STATUS.PLAYING;
        }
    }

    public void stop() {
        if (!MUTE) {
            this.clip.stop();
            this.clip.close();
        }
    }

    public STATUS getStatus() {
        return status;
    }
}
