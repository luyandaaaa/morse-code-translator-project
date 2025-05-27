package src.morse;

import java.util.Scanner;

public class MorseCodeCLI {
    // ANSI color codes for terminal coloring
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\u001B[1m";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Clear screen and display welcome banner
            clearScreen();
            printBanner();
            
            while (true) {
                System.out.println("\n" + CYAN + BOLD + "Main Menu:" + RESET);
                System.out.println(YELLOW + "1." + RESET + " Encode text to Morse code");
                System.out.println(YELLOW + "2." + RESET + " Decode Morse code to text");
                System.out.println(YELLOW + "3." + RESET + " Display Morse code chart");
                System.out.println(YELLOW + "4." + RESET + " Exit");
                System.out.print("\n" + GREEN + "➤ Enter your choice (1-4): " + RESET);

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        encodeMenu(scanner);
                        break;
                    case "2":
                        decodeMenu(scanner);
                        break;
                    case "3":
                        displayMorseChart();
                        pause(scanner);
                        break;
                    case "4":
                        System.out.println("\n" + PURPLE + "Exiting. May the Force be with you!" + RESET);
                        return;
                    default:
                        System.out.println(RED + "\n⚠ Invalid option. Please try again." + RESET);
                        pause(scanner);
                }
            }
        }
    }

    private static void encodeMenu(Scanner scanner) {
        System.out.println("\n" + BLUE + BOLD + "Text to Morse Code Encoding" + RESET);
        System.out.println(CYAN + "Enter text to encode (or 'back' to return):" + RESET);
        System.out.print(GREEN + "➤ " + RESET);
        String text = scanner.nextLine().trim();

        if (text.equalsIgnoreCase("back")) {
            return;
        }

        String morse = MorseCodeTranslator.lettersToMorseCode(text);
        System.out.println("\n" + YELLOW + BOLD + "Encoded Morse Code:" + RESET);
        System.out.println(PURPLE + morse + RESET);
        
        System.out.println("\n" + CYAN + "Would you like to hear the Morse code? (y/n)" + RESET);
        System.out.print(GREEN + "➤ " + RESET);
        String playChoice = scanner.nextLine().trim().toLowerCase();
        
        if (playChoice.equals("y")) {
            playMorseCodeInCLI(morse);
        }
        
        pause(scanner);
    }

    private static void decodeMenu(Scanner scanner) {
        System.out.println("\n" + BLUE + BOLD + "Morse Code to Text Decoding" + RESET);
        System.out.println(CYAN + "Enter Morse code to decode (or 'back' to return):" + RESET);
        System.out.println(YELLOW + "Note:" + RESET + " Use space between letters and '/' between words");
        System.out.print(GREEN + "➤ " + RESET);
        String code = scanner.nextLine().trim();

        if (code.equalsIgnoreCase("back")) {
            return;
        }

        String text = MorseCodeTranslator.morseCodeToLetters(code);
        System.out.println("\n" + YELLOW + BOLD + "Decoded Text:" + RESET);
        System.out.println(PURPLE + text + RESET);
        pause(scanner);
    }

    private static void displayMorseChart() {
        clearScreen();
        System.out.println(BLUE + BOLD + "Morse Code Reference Chart\n" + RESET);
        System.out.println(YELLOW + BOLD + "Letters:" + RESET);
        System.out.println("A: .-    B: -...  C: -.-.  D: -..   E: .     F: ..-.");
        System.out.println("G: --.   H: ....  I: ..    J: .---  K: -.-   L: .-..");
        System.out.println("M: --    N: -.    O: ---   P: .--.  Q: --.-  R: .-.");
        System.out.println("S: ...   T: -     U: ..-   V: ...-  W: .--   X: -..-");
        System.out.println("Y: -.--  Z: --..\n");
        
        System.out.println(YELLOW + BOLD + "Numbers:" + RESET);
        System.out.println("0: ----- 1: .---- 2: ..--- 3: ...-- 4: ....-");
        System.out.println("5: ..... 6: -.... 7: --... 8: ---.. 9: ----.\n");
        
        System.out.println(YELLOW + BOLD + "Punctuation:" + RESET);
        System.out.println(".: .-.-.-  ,: --..--  ?: ..--..  ': .----.");
        System.out.println("!: -.-.--  /: -..-.   (: -.--.   ): -.--.-");
        System.out.println("&: .-...   : ---...   ;: -.-.-.  =: -...-");
        System.out.println("+: .-.-.   -: -....-  _: ..--.-  \": .-..-.");
        System.out.println("$: ...-..- @: .--.-.  Space: /");
    }

    private static void playMorseCodeInCLI(String morse) {
        System.out.println("\n" + CYAN + "Playing Morse code..." + RESET);
        for (char ch : morse.toCharArray()) {
            switch (ch) {
                case '.':
                    System.out.print("• ");
                    beep(100, 600);
                    break;
                case '-':
                    System.out.print("— ");
                    beep(300, 600);
                    break;
                case ' ':
                    System.out.print("  ");
                    try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                    break;
                case '/':
                    System.out.print(" / ");
                    try { Thread.sleep(400); } catch (InterruptedException ignored) {}
                    break;
            }
        }
        System.out.println("\n" + GREEN + "Playback complete!" + RESET);
    }

    private static void beep(int duration, int frequency) {
        try {
            java.awt.Toolkit.getDefaultToolkit().beep();
            Thread.sleep(duration);
        } catch (Exception ignored) {}
    }

    private static void pause(Scanner scanner) {
        System.out.print("\n" + CYAN + "Press Enter to continue..." + RESET);
        scanner.nextLine();
        clearScreen();
        printBanner();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void printBanner() {
        System.out.println(BLUE + BOLD + "┌───────────────────────────────────────────────┐");
        System.out.println("│" + YELLOW + "          📡 R2-D2 Morse Code Translator " + BLUE + "       │");
        System.out.println("│" + PURPLE + "   Convert between text and Morse code easily!  " + BLUE + "│");
        System.out.println("└───────────────────────────────────────────────┘" + RESET);
    }
}
