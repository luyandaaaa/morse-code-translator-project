package morse;

import java.util.Scanner;

public class MorseCodeCLI {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("📡 Welcome to the R2-D2 Morse Code CLI");

            while (true) {
                System.out.println("\nChoose an option:\n1. Encode\n2. Decode\n3. Exit");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Enter text to encode: ");
                        String text = scanner.nextLine();
                        System.out.println("Encoded Morse: " + MorseCodeTranslator.lettersToMorseCode(text));
                        break;
                    case "2":
                        System.out.print("Enter Morse code to decode: ");
                        String code = scanner.nextLine();
                        System.out.println("Decoded Text: " + MorseCodeTranslator.morseCodeToLetters(code));
                        break;
                    case "3":
                        System.out.println("Exiting. May the Force be with you!");
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        }
    }
}
