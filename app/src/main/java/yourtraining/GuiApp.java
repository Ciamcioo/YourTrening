package yourtraining;

import gui.MenuGUI;
import javax.swing.*;

/**
 * GuiApp
 */
public class GuiApp {
  public GuiApp() {
    MenuGUI.getIntsance();
  }
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new GuiApp());
  }
}
