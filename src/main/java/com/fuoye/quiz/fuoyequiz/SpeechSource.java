package com.fuoye.quiz.fuoyequiz;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class SpeechSource {

    private static final String VOICE_NAME = "kevin";
    private static Voice voice;

    // Static initializer to set up the voice
    static {
        // Set the property to point to the voice directory
        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        // Get the voice instance
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(VOICE_NAME);
        if (voice != null) {
            voice.allocate();
        } else {
            System.err.println("Voice " + VOICE_NAME + " not found!");
        }
    }

    /**
     * Speaks the given text using FreeTTS.
     *
     * @param text the text to speak
     */
    public static void speakText(String text) {
        if (voice != null) {
            voice.speak(text);
        } else {
            System.err.println("No voice allocated to speak the text.");
        }
    }

    /**
     * Optionally, you can add a method to deallocate the voice resources.
     * This may be called when your application exits.
     */
    public static void shutdown() {
        if (voice != null) {
            voice.deallocate();
        }
    }
}
