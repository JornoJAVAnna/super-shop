package com.my.boy.supershop;

import com.sun.tools.javac.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;


@SpringBootApplication
public class SuperShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperShopApplication.class, args);
//        playSound("/myboy.wav");
    }

//    private static void playSound(String soundFile) {
//        try {
//            URL soundURL = SuperShopApplication.class.getResource(soundFile);
//            if (soundURL == null) {
//                return;
//            }
//            AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL);
//            Clip clip = AudioSystem.getClip();
//            clip.open(audio);
//            clip.start();
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            e.printStackTrace();
//        }
//    }

}
