package KPP.Lab1.GUI;


import KPP.Lab1.Inspector;

import java.awt.*;
import javax.swing.*;

public class FrameInspector extends JFrame {
    private static JPanel panel;
    public FrameInspector() {
        super("JAVA GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createGUI();

        setSize(900, 650);
        setVisible(true);
        setResizable(true);
    }

    public static void main(String[] args) {
        new FrameInspector();
    }

    private void createGUI() {
        JTextField enter;
        TextArea textArea = new TextArea();
        panel = new JPanel();
        enter = new JTextField(40);

        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 30, 5));

        JButton buttonRun = new JButton("Run");
        JButton buttonClean = new JButton("Clean");
        JButton buttonExit = new JButton("Exit");

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(0, 3, 5,3));
        buttons.add(buttonRun);
        buttons.add(buttonClean);
        buttons.add(buttonExit);

        buttonRun.setBackground(Color.GREEN);
        buttonClean.setBackground(Color.BLUE);
        buttonExit.setBackground(Color.RED);


        panel.add(new JLabel("Name of class(full path):"));
        panel.add(enter);
        panel.add(buttons);


        add(panel, BorderLayout.NORTH);

        add(textArea);


        buttonRun.addActionListener(e -> {
            String nameOfCLass;
            nameOfCLass = enter.getText();
            if (!nameOfCLass.isEmpty()) {
                textArea.setText(Inspector.task1(nameOfCLass));
            }
            System.out.println("err");
        });

        buttonClean.addActionListener(e -> {
            textArea.setText("");
            enter.setText("");
        });

        buttonExit.addActionListener(e -> {
             dispose();
             System.exit(0);
        });
    }
}
