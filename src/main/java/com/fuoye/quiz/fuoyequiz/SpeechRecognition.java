package com.fuoye.quiz.fuoyequiz;

import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.IOException;

public class SpeechRecognition {
    public static void main (String args[]){
        LibVosk.setLogLevel(LogLevel.DEBUG);

        // Load the model - you need to download this separately
        try (Model model = new Model("model")) {
            // Create a recognizer
            try (Recognizer recognizer = new Recognizer(model, 16000)) {

                // Set up audio format for 16kHz mono 16bit signed PCM
                AudioFormat format = new AudioFormat(16000, 16, 1, true, false);

                // Set up microphone input
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                if (!AudioSystem.isLineSupported(info)) {
                    System.out.println("Line not supported");
                    System.exit(0);
                }

                TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
                microphone.open(format);
                microphone.start();

                System.out.println("Start speaking... (Press Ctrl+C to exit)");

                // Create a buffer for reading the audio data
                byte[] buffer = new byte[4096];
                while (true) {
                    int count = microphone.read(buffer, 0, buffer.length);
                    if (count > 0) {
                        // Process the audio data
                        if (recognizer.acceptWaveForm(buffer, count)) {
                            // Get the result
                            String result = recognizer.getResult();
                            System.out.println(result);
                        } else {
                            // Get partial result
                            String partial = recognizer.getPartialResult();
                            System.out.println(partial);
                        }
                    }
                }
            } catch (IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
