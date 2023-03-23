package org.msic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main extends JFrame {
    private List<Process> processes = new ArrayList<>();

    public Main() {
        // Create the GUI
        JPanel panel = new JPanel();
        JButton startButton = new JButton("Start Process");
        panel.add(startButton);

        JButton stopButton = new JButton("Stop Process");
        panel.add(stopButton);

        add(panel);

        // Add an ActionListener to the start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Start a process
                    Process process;
                    process = Runtime.getRuntime().exec("notepad.exe");

                    // Create file if file doesn't exists
                    String desktopPath = System.getProperty("user.home") + "/Desktop";  // Uncomment and use this; this is for you .3.
                    File file = new File(desktopPath + "/output.txt");  // Uncomment and use this; this is for you .3.
//                    File file = new File("D:\\JavaProjects" + "/output.txt"); // Delete this; it will not run for you

                    // Save the opened process in a file
                    ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "tasklist /FI \"IMAGENAME eq notepad.exe\"");
                    builder.redirectOutput(file);
                    builder.start();

                    processes.add(process);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Add an ActionListener to the stop button
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (processes.isEmpty()) {
                    JOptionPane.showMessageDialog(Main.this, "No process is currently running");
                    return;
                }

                Process poppedProcess = processes.remove(0);
                poppedProcess.destroy();

                JOptionPane.showMessageDialog(Main.this, "Process "+ poppedProcess.pid() +" terminated");
            }
        });

        // Set the frame properties
        setTitle("Process Manager");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Main ex = new Main();
        ex.setVisible(true);
    }
}