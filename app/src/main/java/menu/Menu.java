package menu;

import java.io.*;

public final class Menu {
    private static final String MENU_CONTECST = "------ Your trainign main menu ------\n1. Load training\n2. Start training\n3. Exit application\n";
    private static final int MENU_SIZE = 3, BREAK_HADNLING_INPUT = -1 ;
    private static Menu instance;
    private static final InputStream inputStream = System.in;

    private Menu() {
    }

    public static Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    public void menuRunner() {
        int input = -1; 
        boolean shouldStop = false;
        
        do {
            System.out.println(MENU_CONTECST);
            input = handleInput(new InputStreamReader(inputStream)); 
            shouldStop = performeMenuAction(input);

        } while(shouldStop);
    }

    /**
     * Method handles the value recived from the input stream. The value from the stream will be retuned in case it is correct. The second requirment is to provide the correct value in no more than 3 attmepts, after that handling of the function will stop.
     * @param stream input stream which is the source of an input
     * @return  in case of correct input the value of input is returned, if 3 attempts of reading from stream will be unsucesful hadnling of the input is terminated by returning -1
     */
    private int handleInput(InputStreamReader stream) {
        boolean isNotValid = false;
        int input = -1, attempts = 3; 
        do {
            input = getInput(stream); 
            isNotValid = !validateInput(input);
        } while(isNotValid && attempts-- > 0 );
        if (isNotValid)
            return BREAK_HADNLING_INPUT;
        return input;
    }

    /**
     * Method gets integer value from provided input stream which is input
     * @param stream input stream which is the source of input 
     * @return returns the value from the stream as an Integer. In case the value cannot be converted to Integer 0 is returned.
     */
    private int getInput(InputStreamReader stream) {
        BufferedReader reader = new BufferedReader(stream);
        System.out.println("> ");
        try {
            return Integer.parseInt(reader.readLine());
        } catch(Exception e) {
            return 0;
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

    private boolean performeMenuAction(int input) {
        switch(input) {
            case 1 -> { return false; }
            case 2 -> { 
                
                return false;
            } 
            case 3 -> { return true; }
        }
        return false;
    }

    // TODO write closing method for the reader    
}