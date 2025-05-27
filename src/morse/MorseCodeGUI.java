package src.morse;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import javax.sound.sampled.*;

public class MorseCodeGUI {
    public static void main(String[] args) {
        // Create the main frame with a themed title
        JFrame frame = new JFrame("🚀 R2-D2 Morse Translator ✨");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window

        // Set a dark theme color palette
        Color darkBlue = new Color(20, 30, 60);
        Color mediumBlue = new Color(40, 60, 120);
        Color lightBlue = new Color(100, 180, 255);
        Color accentOrange = new Color(255, 150, 50);

        // Create a gradient background panel
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, darkBlue, getWidth(), getHeight(), new Color(10, 20, 40));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create title label
        JLabel titleLabel = new JLabel("R2-D2 Morse Code Translator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(lightBlue);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Create text areas with styling
        JTextArea inputArea = new JTextArea(5, 30);
        styleTextArea(inputArea, "Enter text or Morse code here...");
        
        JTextArea outputArea = new JTextArea(5, 30);
        styleTextArea(outputArea, "Translation will appear here...");
        outputArea.setEditable(false);

        // Create buttons with styling
        JButton encodeButton = createStyledButton("Encode", mediumBlue, lightBlue);
        JButton decodeButton = createStyledButton("Decode", mediumBlue, lightBlue);
        JButton playButton = createStyledButton("Play Morse", accentOrange, Color.WHITE);
        JButton clearButton = createStyledButton("Clear", new Color(80, 20, 20), lightBlue);

        // Button panel with grid layout
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(encodeButton);
        buttonPanel.add(decodeButton);
        buttonPanel.add(playButton);
        buttonPanel.add(clearButton);

        // Text areas panel
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        textPanel.setOpaque(false);
        
        JPanel inputPanel = createTitledPanel("Input", inputArea);
        JPanel outputPanel = createTitledPanel("Output", outputArea);
        
        textPanel.add(inputPanel);
        textPanel.add(outputPanel);

        // Add action listeners
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
            } else {
                JOptionPane.showMessageDialog(frame, "No Morse code to play!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            inputArea.setText("");
            outputArea.setText("");
        });

        // Add components to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createTitledPanel(String title, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        JLabel label = new JLabel(title);
        label.setForeground(new Color(200, 200, 255));
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(new JScrollPane(component), BorderLayout.CENTER);
        
        return panel;
    }

    private static void styleTextArea(JTextArea textArea, String placeholder) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setForeground(new Color(240, 240, 240));
        textArea.setBackground(new Color(30, 40, 70));
        textArea.setCaretColor(new Color(100, 180, 255));
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 80, 120), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        textArea.setText(placeholder);
    }

    private static JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
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