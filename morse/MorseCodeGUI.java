package morse;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import javax.sound.sampled.*;

public class MorseCodeGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("R2-D2 Morse Translator");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea inputArea = new JTextArea(5, 30);
        JTextArea outputArea = new JTextArea(5, 30);
        outputArea.setEditable(false);

        JButton encodeButton = new JButton("Encode");
        JButton decodeButton = new JButton("Decode");
        JButton playButton = new JButton("Play Morse");

        encodeButton.addActionListener(e -> {
            String input = inputArea.getText();
            String morse = MorseCodeTranslator.lettersToMorseCode(input);
            outputArea.setText(morse);
        });

        decodeButton.addActionListener(e -> {
            String input = inputArea.getText();
            String text = MorseCodeTranslator.morseCodeToLetters(input);
            outputArea.setText(text);
        });

        playButton.addActionListener(e -> {
            String morse = outputArea.getText();
            if (!morse.isEmpty()) {
                playMorseCode(morse);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(2, 1));
        top.add(new JScrollPane(inputArea));
        top.add(new JScrollPane(outputArea));

        JPanel buttons = new JPanel();
        buttons.add(encodeButton);
        buttons.add(decodeButton);
        buttons.add(playButton);

        panel.add(top, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void playMorseCode(String morseCode) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            for (char ch : morseCode.toCharArray()) {
                switch (ch) {
                    case '.':
                        playSound("resources/dot.wav");
                        sleep(100);
                        break;
                    case '-':
                        playSound("resources/dash.wav");
                        sleep(100);
                        break;
                    case ' ':
                        sleep(200); // space between letters
                        break;
                    case '/':
                        sleep(400); // pause between words
                        break;
                }
            }
        });
        executor.shutdown();
    }

    private static void playSound(String soundFile) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new java.io.File(soundFile));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}
