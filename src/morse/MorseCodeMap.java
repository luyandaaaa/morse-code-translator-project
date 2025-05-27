package src.morse;
import java.util.*;

public class MorseCodeMap {
    public static final Map<Character, String> LETTER_TO_MORSE = new HashMap<>();
    public static final Map<String, Character> MORSE_TO_LETTER = new HashMap<>();

    static {
        String[] letters = {
            "A", ".-",    "B", "-...",  "C", "-.-.",
            "D", "-..",   "E", ".",     "F", "..-.",
            "G", "--.",   "H", "....",  "I", "..",
            "J", ".---",  "K", "-.-",   "L", ".-..",
            "M", "--",    "N", "-.",    "O", "---",
            "P", ".--.",  "Q", "--.-",  "R", ".-.",
            "S", "...",   "T", "-",     "U", "..-",
            "V", "...-",  "W", ".--",   "X", "-..-",
            "Y", "-.--",  "Z", "--..",
            "0", "-----", "1", ".----", "2", "..---",
            "3", "...--", "4", "....-", "5", ".....",
            "6", "-....", "7", "--...", "8", "---..",
            "9", "----.",
            ".", ".-.-.-", ",", "--..--", "?", "..--..",
            "'", ".----.", "!", "-.-.--", "/", "-..-.",
            "(", "-.--.", ")", "-.--.-", "&", ".-...",
            ":", "---...", ";", "-.-.-.", "=", "-...-",
            "+", ".-.-.", "-", "-....-", "_", "..--.-",
            "\"", ".-..-.", "$", "...-..-", "@", ".--.-.",
            " ", "/"
        };
        for (int i = 0; i < letters.length; i += 2) {
            char letter = letters[i].charAt(0);
            String morse = letters[i + 1];
            LETTER_TO_MORSE.put(letter, morse);
            MORSE_TO_LETTER.put(morse, letter);
        }
    }
}