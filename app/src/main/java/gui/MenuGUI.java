package gui;

import java.io.*;
import java.awt.Dimension;
import java.awt.Font;
import javax.annotation.Nonnull;
import javax.swing.*;
import menu.ActionType;
import training.Training;

public class MenuGUI {
    static final int HEIGHT = 300, WIDTH = 300; 
    private static MenuGUI instance = null;
    @Nonnull
    private Training training;
    private TrainingPanel trainingPanel;
    private JFrame frame;
    private JLabel titleLabel;
    private Box buttonArea; 

    
    /**
     *  Method of singleton design pattern returning existing instance of a object or creating a new one. 
     * @param menu argument passed to the constructor of object in case there is need to create object
     * @return returns newly created object of MenuGUI or one that already existed.
     */
    public static MenuGUI getInstance() {
        if (instance == null) {
            instance = new MenuGUI();
        }
        return instance;
    }
    
    /**
     *  Constructor of MenuGUI class allowing to create a new gui object with all the initialization of swing components.
     *  Additionally function sets the menu field which corresponds to backend system of gui. 
     * @param menu object handling the logic of menu
     */
    private MenuGUI() {
        this.training = new Training();
        frame = initializeFrame(); 
        trainingPanel = new TrainingPanel();
        titleLabel = initializeTitleLabel();
        buttonArea = initializeMainMenuButtons();
        addComponentsToContentPane();
        frame.setVisible(true);
    }

    /**
     * Method initialize JFrame object which is frame of the application. 
     * @return Reference to initialize frame
     */
    private JFrame initializeFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("Your training");
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setSize(WIDTH*2, HEIGHT*2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanelInitialization());
        return frame;
    }

    
    /**
     *  Method initialize the panel which contains the base functionality of main menu 
     * @return Reference to initialize main menu panel
     */
    private JPanel mainPanelInitialization() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        return panel;
    }

    /**
     *  Method initialize label containing the title of an application which will be displayed on the screen of the user. 
     * @return Reference to tittle JLabel.
     */
    private JLabel initializeTitleLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        label.setText("Your training");
        return label;
    }

    /**
     * Method initialize the Box panel containing the main menu buttons.
     * @return method returns the box area with the buttons.
     */
    private Box initializeMainMenuButtons() {
        Box area = new Box(BoxLayout.PAGE_AXIS); 
        area.add(mainMenuButtonFactory(ActionType.LOAD_TRAINING));
        area.add(mainMenuButtonFactory(ActionType.START_TRAINING));
        area.add(mainMenuButtonFactory(ActionType.EXIT));
        return area;
    }

    /**
     *  Factory method creating button  based on name and input argument representing by the button. Method create action listener which will passed input represented by the button.
     * @param buttonName name of the button
     * @param validInput input represented by button
     * @return returns created button based on the arguments
     */
    private JButton mainMenuButtonFactory(ActionType action) {
        JButton button = new JButton(action.getOperationName());
        button.setSize(WIDTH/5, HEIGHT/10);
        button.addActionListener(event -> {
            createResponseAction(action).run();
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

    /**
     * Method is responsible for receiving from user a file representing the training file. File is retrieved from user with use of user interface.
     * @return File which was chosen by the user with use of UI
     */
    private File getTrainingFile(JFileChooser fileChooser) {
      String appLocalization = System.getProperty("user.dir"); // user.dir property is a property which specifies the directory from which java application was run 
      File defaultDirecotry = new File(appLocalization, "resources"); 
      fileChooser.setCurrentDirectory(defaultDirecotry);;
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
        return fileChooser.getSelectedFile();
      return null;
    }

    private void startTraning() {

    }
    
}
