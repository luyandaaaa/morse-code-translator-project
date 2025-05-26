package morse;

import javax.swing.*;
import java.awt.*;

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

        encodeButton.addActionListener(e -> {
            String input = inputArea.getText();
            outputArea.setText(MorseCodeTranslator.lettersToMorseCode(input));
        });

        decodeButton.addActionListener(e -> {
            String input = inputArea.getText();
            outputArea.setText(MorseCodeTranslator.morseCodeToLetters(input));
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(2, 1));
        top.add(new JScrollPane(inputArea));
        top.add(new JScrollPane(outputArea));

        JPanel buttons = new JPanel();
        buttons.add(encodeButton);
        buttons.add(decodeButton);

        panel.add(top, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }
}
