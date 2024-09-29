package gui;

import java.awt.Font;
import javax.annotation.Nonnull;
import javax.swing.*;
import menu.ActionType;
import menu.Menu;

public class MenuGUI {
    private static final int HEIGHT = 300, WIDTH = 300; 
    private static MenuGUI instance = null;
    @Nonnull
    private Menu menu;
    private JFrame frame;
    private JLabel titleLabel;
    private Box buttonArea; 

    
    /**
     *  Method of singleton design patern returning existing instance of a object or creating a new one. 
     * @param menu arguemnt passed to the constructor of object in case there is need to create object
     * @return returns newly created object of MenuiGUI or one that already existed.
     */
    public static MenuGUI getIntsance(Menu menu) {
        if (instance == null) {
            instance = new MenuGUI(menu);
        }
        return instance;
    }
    
    /**
     *  Constructor of MenuGUI class allowing to create a new gui object with all the initalization of swing components.
     *  Additionaly function sets the menu field which coresponds to backend system of gui. 
     * @param menu object handling the logic of menu
     */
    private MenuGUI(Menu menu) {
        this.menu = menu;
        frame = initializeFrame(); 
        titleLabel = initalizeTitleLabel();
        buttonArea = initalizeMainMenuButtons();
        addComponentsToContentPane();
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

    /**
     * Method initalize the Box panel containing the main menu buttons.
     * @return method returns the box area with the buttons.
     */
    private Box initalizeMainMenuButtons() {
        Box area = new Box(BoxLayout.PAGE_AXIS); 
        area.add(mainMenuButtonFactory("Load Traning", ActionType.LOAD_TRANING));
        area.add(mainMenuButtonFactory("Start Trening", ActionType.START_TRANING));
        area.add(mainMenuButtonFactory("Exit", ActionType.EXIT));
        return area;
    }

    /**
     *  Factory method creating button basied on name arugemnt and input arguemtn representen by the button. Method create action listener which will passed input represented by the button.
     * @param buttonName name of the button
     * @param validInput input represnted by button
     * @return returns created button based on the arguments
     */
    private JButton mainMenuButtonFactory(String buttonName, ActionType action) {
        JButton button = new JButton(buttonName);
        button.setSize(WIDTH/5, HEIGHT/10);
        button.addActionListener(event -> {
            menu.performeMenuAction(action);
        }); 
        return button;
    }


    /**
     * Method adds components to content pane of frame
     */
    private void addComponentsToContentPane() {
        frame.getContentPane().add(titleLabel);
        frame.getContentPane().add(buttonArea);
    }

    
}
