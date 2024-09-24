package gui;

import java.awt.Font;
import javax.swing.*;

public class MenuGUI {
    private static final int HEIGHT = 300, WIDTH = 300; 
    private static MenuGUI instance = null;

    private JFrame frame;
    private JLabel titleLabel;
    private Box buttonArea; 

    
    public static MenuGUI getIntsance() {
        if (instance == null) {
            instance = new MenuGUI();
        }
        return instance;
    }

    private MenuGUI() {
        frame = initializeFrame(); 
        titleLabel = initalizeTitleLabel();
        buttonArea = new Box(BoxLayout.PAGE_AXIS);
        frame.setVisible(true);
    }

    /**
     * Method initalize JFrame object which is frame of the application. 
     * @return Reference to initalize frame
     */
    private JFrame initializeFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("Your training");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanelInitalization());
        return frame;
    }

    
    /**
     *  Method initalize the panel which contains the base functionality of main manu 
     * @return Reference to initalize main menu panel
     */
    private JPanel mainPanelInitalization() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        return panel;
    }

    /**
     *  Method initalize label containg the title of an application which will be dispalyed on the screen of the user. 
     * @return Reference to tittle JLabel.
     */
    private JLabel initalizeTitleLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        label.setText("Your traning");
        return label;
    }

    private void initalizeMainMenuButtons() {
        JButton loadTraning = mainMenuButtonFactory("Load Traning");
    }

    private JButton mainMenuButtonFactory(String name) {
        JButton button = new JButton(name);
        button.setSize(WIDTH/5, HEIGHT/10);
        buttonArea.add(Box.createHorizontalGlue());
        buttonArea.add(button);
        buttonArea.add(Box.createHorizontalGlue());
        return button;
    }
    
}