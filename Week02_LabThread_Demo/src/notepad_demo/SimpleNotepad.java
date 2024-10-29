package notepad_demo;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SimpleNotepad extends JFrame implements ActionListener {
    private JTextArea textArea;
    
    public SimpleNotepad() {
        super("Simple Notepad");
        textArea = new JTextArea(24, 54);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        JMenuBar menuBar = new JMenuBar();
        String[] menuNames = {"File", "Edit", "Format", "Help"};
        
        for (int i = 0; i < menuNames.length; i++) {
            JMenu menu = new JMenu(menuNames[i]);
            menu.addActionListener(this);
            
            if (i == 0) {
                JMenuItem openItem = new JMenuItem("Open");
                JMenuItem saveItem = new JMenuItem("Save");
                JMenuItem exitItem = new JMenuItem("Exit");
                // Add action listeners for these menu items
                
                menu.add(openItem);
                menu.add(saveItem);
                menu.add(exitItem);
            } else if (i == 1) {
                // Add edit-related menu items (cut, copy, paste, clear)
            } else if (i == 2) {
                // Add format-related menu items (font selection, text wrap)
            } else {
                JMenuItem aboutItem = new JMenuItem("About Simple Notepad");
                // Add action listener for the "About" menu item
                
                menu.add(aboutItem);
            }
            
            menuBar.add(menu);
        }
        
        add(menuBar);
        add(scrollPane, BorderLayout.SOUTH);
        setVisible(true);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    // Implement actionPerformed method to handle menu actions
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleNotepad());
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
