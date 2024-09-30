package yourtraining;

import java.io.IOException;
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
}
