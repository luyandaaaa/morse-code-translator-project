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
                        beep(800, 100); // dot
                        sleep(100);
                        break;
                    case '-':
                        beep(800, 300); // dash
                        sleep(100);
                        break;
                    case ' ':
                        sleep(200); // space between characters
                        break;
                    case '/':
                        sleep(400); // pause between words
                        break;
                }
            }
        });
        executor.shutdown();
    }

    private static void beep(int hz, int durationMs) {
        float sampleRate = 44100;
        byte[] buf = new byte[1];
        AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
        try (SourceDataLine sdl = AudioSystem.getSourceDataLine(af)) {
            sdl.open(af);
            sdl.start();
            for (int i = 0; i < durationMs * (sampleRate / 1000); i++) {
                double angle = i / (sampleRate / hz) * 2.0 * Math.PI;
                buf[0] = (byte) (Math.sin(angle) * 127);
                sdl.write(buf, 0, 1);
            }
            sdl.drain();
            sdl.stop();
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
