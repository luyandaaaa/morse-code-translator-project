package src.morse;

public class MorseCodeTranslator {

    public static String lettersToMorseCode(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            if (MorseCodeMap.LETTER_TO_MORSE.containsKey(c)) {
                sb.append(MorseCodeMap.LETTER_TO_MORSE.get(c)).append(" ");
            }
        }
        return sb.toString().trim();
    }

    public static String morseCodeToLetters(String code) {
        StringBuilder sb = new StringBuilder();
        for (String word : code.split(" ")) {
            if (MorseCodeMap.MORSE_TO_LETTER.containsKey(word)) {
                sb.append(MorseCodeMap.MORSE_TO_LETTER.get(word));
            } else if (word.equals("/")) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
