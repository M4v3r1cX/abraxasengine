package com.bsodsoftware.abraxas.engine.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

    public enum STATUS {
        PLAYING, STOPPED
    }

    private Clip clip;
    private STATUS status;
    private AudioInputStream audioInputStream;
    private String location;
    private boolean loop;

    public Audio(String location, boolean loop) {
        this.location = location;
        this.loop = loop;
        this.status = STATUS.STOPPED;
        this.load();
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
        this.clip.start();
        this.status = STATUS.PLAYING;
    }

    public void stop() {
        this.clip.stop();
        this.clip.close();
    }

    public STATUS getStatus() {
        return status;
    }
}
