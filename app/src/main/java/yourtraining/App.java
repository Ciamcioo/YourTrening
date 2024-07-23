package yourtraining;

import menu.Menu;

public class App {
    private Menu menu = null;
    
    public App() {
        menu = Menu.getInstance();
    }
    public static void main(String[] args) {
        App app = new App();
        app.menu.menuRunner();
    }
}
