package notepad_demo;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleNotepadGUI {
    private JFrame frame;
    private JTextArea textArea;
    private JButton loadButton;

    public SimpleNotepadGUI() {
        frame = new JFrame("Simple Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        loadButton = new JButton("Load File");
        loadButton.addActionListener(e -> loadFileInBackground());
        frame.add(loadButton, BorderLayout.SOUTH);
    }

    private void loadFileInBackground() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            FileLoader fileLoader = new FileLoader(fileChooser.getSelectedFile());
            executorService.submit(fileLoader);
            executorService.shutdown();
        }
    }

    private class FileLoader implements Runnable {
        private File file;

        public FileLoader(File file) {
            this.file = file;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                SwingUtilities.invokeLater(() -> {
                    textArea.setText(sb.toString());
                    loadButton.setEnabled(false);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public static void main(String[] args) {
        SimpleNotepadGUI notepad = new SimpleNotepadGUI();
        notepad.show();
    }
}