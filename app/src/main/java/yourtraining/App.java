package yourtraining;

import menu.Menu;

/**
 * App class executes program. 
 */
public class App {
    private Menu menu = null;
    
    /**
     * Constructor initalizes the object with the instance of menu.
     */
    public App() {
        menu = Menu.getInstance();
    }

    /**
     * Main function which invokes the program to run.
     * @param args arguemnts passed from terminal passed by the user
     */
    public static void main(String[] args) {
        App app = new App();
        app.menu.menuRunner();
    }
}
