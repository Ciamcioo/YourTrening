package menu;

import java.io.*;

import training.Training;
import yourtraining.TerminalApp;

/**
 * Menu class is responsible for handling menu actions. Class takes the input from their user and takes action based on it.
 */
public final class Menu  {
    private static final String MENU_CONTEXT = "------ Your training main menu ------\n1. Load training\n2. Start training\n3. Exit application\n",
                                PATH_LOAD_CONTEXT = "Provide absolute path to training file\n";
    private static final int MENU_SIZE = 3, BREAK_HANDLING_INPUT = -1 ;

    private static Menu instance;
    private InputStream inputStream = System.in;
    private Training training = null;

    /**
     * Method implements the Singleton design pattern to ensure that the only one instance of the Menu class is created. 
     * @return the reference to the Menu object.
     */
    public static Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    /**
     * Private constructor used when new instance of menu class is created
     */
    private Menu() {
        training = new Training();
    }

    /**
     * Method handling running process for the menu, which contains a several methods providing sets of action to properly handle processing of the application. 
     */
    public void menuRunner() {
        int input = -1; 
        boolean shouldStop = false;

        do {
            System.out.print(MENU_CONTEXT);
            input = handleInput(inputStream); 
            shouldStop = performMenuAction(ActionType.convertIntegerInputToActionType(input));
            TerminalApp.getKeyPress();
            TerminalApp.clearTerminal();
        } while(!shouldStop);
        closeStream(inputStream);
    }

    /**
     * Method handles the value received from the input stream. The value from the stream will be returned in case it is correct. The second requirement is to provide the correct value in no more than 3 attempts, after that handling of the function will stop.
     * @param stream input stream which is the source of an input
     * @return  in case of correct input the value of input is returned, if 3 attempts of reading from stream will be unsuccessful handling of the input is terminated by returning -1
     */
    private int handleInput(InputStream stream) {
        boolean isNotValid = false;
        int integerInput = -1, attempts = 3; 
        do {
            integerInput = getIntegerInput(stream); 
            isNotValid = !validateInput(integerInput);
        } while(isNotValid && attempts-- > 0 );
        if (isNotValid)
            return BREAK_HANDLING_INPUT;
        return integerInput;
    }

    /**
     * Method converts string value to integer value.
     * @param stream input stream which is the source of input 
     * @return returns the Integer converted from String value read from stream. In case the value cannot be converted to Integer 0 is returned.
     */
    private int getIntegerInput(InputStream stream) {
        try {
            return Integer.parseInt(getInput(stream));
        } catch(Exception e) {
            return 0;
        }
    }


    /**
     * Method reads input from the stream which is provided as an argument. 
     * @param stream input stream which is source of input
     * @return returns the value from the stream. In case of exception the value return is null.
     */
    private String getInput(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream)); 
        System.out.print("> ");
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        } 
    }

    /**
     * Method validates input provided as an argument if it matches the range of Menu options
     * @param input input provided from the specific input stream 
     * @return true if input matches the range of Menu, false in other case
     */
    private boolean validateInput(int input) {
        if (input > 0 && input <= MENU_SIZE) {
            return true;
        }  
        return false;
    }


    /**
     * Method takes action on training object based on the input provided as an argument. 
     * @param input integer representing the action that will be taken on training objet
     * @return method returns true when performing an action on specified training object needs to be stopt. In other case function returns false.
     */
    public boolean performMenuAction(ActionType input) {
        switch(input) {
            case LOAD_TRAINING -> { 
                String path = getPathToTraining(); 
                System.out.print(training.loadTraining(path));
                return false; 
            }
            case START_TRAINING -> { 
                if (!training.checkIfTrainingIsLoaded()) 
                    System.out.println("Training is not loaded. Firstly load training");
                else
                    training.processTraining();
                return false;
            } 
            case  EXIT -> { return true; }
        }
        return false;
    }

    /**
     * Function responsible for closing stream within the range of training class. 
     * @param stream object implementing Closeable interface which will be closed 
     * @return Method returns true if closing action was a success, in other case method returns false
     */
    private boolean closeStream(Closeable stream) {
        try {
            stream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Method prints information about path and gets the path from stream 
     * @return method returns the path to training file
     */
    String getPathToTraining() {
        System.out.print(PATH_LOAD_CONTEXT);
        String path = getInput(inputStream);
        return path;
    }
}
