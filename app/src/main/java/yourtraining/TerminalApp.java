package yourtraining;

import menu.Menu;

/**
 * App class executes program in Terminal. 
 */
public class TerminalApp {
    private Menu menu = null;
    
    /**
     * Constructor initalizes the object with the instance of menu.
     */
    public TerminalApp() {
        menu = Menu.getInstance();
    }

    /**
     * Main function which invokes the program to run.
     * @param args arguemnts passed from terminal passed by the user
     */
    public static void main(String[] args) {
        TerminalApp app = new TerminalApp();
        app.menu.menuRunner();
    }
}
