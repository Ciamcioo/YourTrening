package menu;

import java.io.*;
import traning.Traning;

public final class Menu {
    private static final String MENU_CONTECST = "------ Your trainign main menu ------\n1. Load training\n2. Start training\n3. Exit application\n",
                                PATH_LOAD_CONTECST = "Provide absolute path to traning file\n";
    private static final int MENU_SIZE = 3, BREAK_HADNLING_INPUT = -1 ;

    private static Menu instance;
    private InputStream inputStream = System.in;
    private Traning traning = null;

    /**
     * Method implements the Singleton design pattern to ensure that the only one instance of the Menu class is created. 
     * @return the referance to the Menu object.
     */
    public static Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    private Menu() {
        traning = new Traning();
    }

    /**
     * Method handling running process for the menu, which containes a several methods providing sets of action to properly handle processing of the application. 
     */
    public void menuRunner() {
        int input = -1; 
        boolean shouldStop = false;

        do {
            System.out.print(MENU_CONTECST);
            input = handleInput(inputStream); 
            shouldStop = performeMenuAction(input);
            getKeyPress();
            clearTerminal();
        } while(!shouldStop);
        closeStream(inputStream);
    }

    /**
     * Method handles the value recived from the input stream. The value from the stream will be retuned in case it is correct. The second requirment is to provide the correct value in no more than 3 attmepts, after that handling of the function will stop.
     * @param stream input stream which is the source of an input
     * @return  in case of correct input the value of input is returned, if 3 attempts of reading from stream will be unsucesful hadnling of the input is terminated by returning -1
     */
    private int handleInput(InputStream stream) {
        boolean isNotValid = false;
        int input = -1, attempts = 3; 
        do {
            input = getIntegerInput(stream); 
            isNotValid = !validateInput(input);
        } while(isNotValid && attempts-- > 0 );
        if (isNotValid)
            return BREAK_HADNLING_INPUT;
        return input;
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
     * Method reads input from the stream which is provided as an arguemnt. 
     * @param stream input stream which is source of input
     * @return returns the value from the stream. In case of exception the value return is null.
     */
    private String getInput(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream)); 
        System.out.println("> ");
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        } finally {
            closeStream(reader);
        }
    }

    /**
     * Method validates input provided as an argument if it matches the range of Menu options
     * @param input input provided from the specific input stream 
     * @return ture if input matches the range of Menu, false in other case
     */
    private boolean validateInput(int input) {
        if (input > 0 && input <= MENU_SIZE) {
            return true;
        }  
        return false;
    }

    boolean performeMenuAction(int input) {
        switch(input) {
            case 1 -> { 
                clearTerminal();
                String path = getPathToTraning(); 
                System.out.println(traning.loadTraning(path));
                return false; 
            }
            case 2 -> { 
                if (!traning.checkIfTraningIsLoaded()) 
                    System.out.println("Traning is not loaded. Firstly load traning");
                else
                    traning.procesTraining();
                return false;
            } 
            case 3 -> { return true; }
        }
        return false;
    }

    /**
     * Function responsible for closing stream whithin the range of traning class. 
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
     * @return method returns the path to traning file
     */
    String getPathToTraning() {
        System.out.println(PATH_LOAD_CONTECST);
        String path = getInput(inputStream);
        return path;
    }

    /**
     *  Method clears terminal from containing data
     */
    public static void clearTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start();
            else {
                System.out.println("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final IOException exception) {
            System.out.println("Error during clearing terminal");
        }

    }

    /**
     * Method waits for any key press
     */
    public static void getKeyPress() {
        System.out.println("Press any key...");
        try {
            System.in.read();
        } catch(IOException e) {
            return;
        }
    }

}