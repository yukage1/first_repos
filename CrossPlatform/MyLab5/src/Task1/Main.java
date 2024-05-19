package Task1;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static final Task1.Main instance = new Task1.Main();
    public Main(){
        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        JFrame jFrame = new JFrame("TCP Window");
        jFrame.setBounds((screenSize.width-640)/2,(screenSize.height-480)/2,640,480);
        jFrame.setLayout(new BorderLayout());
        ScrollPane scrollPane = new ScrollPane();
        TextField textField = new TextField();
        textField.setText("test text.");
        textField.setBounds(0,0,400,200);
        textField.setFont(new Font("Bahnschrift",Font.PLAIN,14));


        textField.setBackground(Color.black);
        textField.setForeground(Color.white);

        textField.setEditable(false);
        scrollPane.add(textField);

        JPanel topPane = new JPanel();
        JPanel nestedPane = new JPanel();
        nestedPane.setBounds((640-200)/2, 10,200,20);
        nestedPane.setLayout(new GridLayout(1,2));
        nestedPane.add(new JLabel("Working Port:"));
        nestedPane.add(new JTextField("12345"));
        topPane.add(nestedPane);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1,3));
        Button btn1 = new Button("Start Task1.Server");
        Button btn2 = new Button("Stop Task1.Server");
        Button btn3 = new Button("Close Task1.Server");

        btn1.setFont(new Font("Times New Roman",Font.BOLD,12));
        btn2.setFont(new Font("Times New Roman",Font.BOLD,12));
        btn3.setFont(new Font("Times New Roman",Font.BOLD,12));
        bottomPanel.add(btn1); bottomPanel.add(btn2); bottomPanel.add(btn3);

        jFrame.add(bottomPanel,BorderLayout.SOUTH);
        jFrame.add(topPane,BorderLayout.NORTH);
        jFrame.add(new JPanel(), BorderLayout.WEST);
        jFrame.add(new JPanel(), BorderLayout.EAST);
        jFrame.add(scrollPane,BorderLayout.CENTER);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
    }
    public static void main(String[] args) {



    }
}

