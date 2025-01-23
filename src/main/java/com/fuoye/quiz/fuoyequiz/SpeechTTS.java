package com.fuoye.quiz.fuoyequiz;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class SpeechTTS {
    public static void main(String[] args) {
        // Name of the voice to use (e.g., "kevin16" is a default voice)
        String voiceName = "kevin";

        System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        // Get the VoiceManager instance
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice voice = voiceManager.getVoice(voiceName);

        Voice[] voicelist = VoiceManager.getInstance().getVoices();
        for (Voice value : voicelist) {
            System.out.println("Voice no: " + value.getName());
        }
        if (voice != null) {
            voice.allocate(); // Allocate resources for the voice

            // Speak the given text
            voice.speak("Hello, this is an example of text to speech using FreeTTS.");

            voice.deallocate(); // Free resources
        } else {
            System.out.println("Voice not found!");
        }
    }
}
