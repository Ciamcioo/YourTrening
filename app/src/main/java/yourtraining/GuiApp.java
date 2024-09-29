package yourtraining;

import menu.Menu;
import gui.MenuGUI;
import javax.swing.*;
/**
 * GuiApp
 */
public class GuiApp {
  private Menu menu;

  public GuiApp() {
    menu = Menu.getInstance();
    MenuGUI.getIntsance(menu);
  }
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new GuiApp());
  }
}
