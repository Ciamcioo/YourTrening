package gui;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;
import org.junit.jupiter.api.Test;
import java.lang.reflect.*;

public class MenuGUITest {
    private MenuGUI mainMenu = MenuGUI.getIntsance();
    private static final Method[] privMethods = MenuGUITest.class.getDeclaredMethods();

    @Test 
    public void basicFrameInitalizationTest() {
        JFrame resultFrame = new JFrame();
        Container startPanel = resultFrame.getContentPane();
        String frameTitle = "Your training";
        Method initalizeFrame = findPrivateMethod("initializeFrame");
        if (initalizeFrame == null)
            fail("Method not found");
        initalizeFrame.setAccessible(true);
        try {
            resultFrame = (JFrame) initalizeFrame.invoke(mainMenu); 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
            fail(exception.getCause());
        }
        
        assertEquals(frameTitle, resultFrame.getTitle());
        assertEquals(new Dimension(300, 300), resultFrame.getSize());
        assertEquals(JFrame.EXIT_ON_CLOSE, resultFrame.getDefaultCloseOperation());
        assertFalse(startPanel.equals(resultFrame.getContentPane()));
        
    }

    @Test
    public void basicPanelInitalizationTest() {
        JPanel resultPanel = new JPanel();
        Method mainPanelInitalization = findPrivateMethod("mainPanelInitalization");
        if (mainPanelInitalization == null)
            fail("Method not found");
        mainPanelInitalization.setAccessible(true);
        try {
            resultPanel = (JPanel) mainPanelInitalization.invoke(mainMenu);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
            fail(exception.getCause());
        }
        assertTrue(resultPanel.getLayout() instanceof BoxLayout);
    }

    @Test
    public void basicTitleLabelInitalization() {
        JLabel resultLabel = new JLabel();
        String title = "Your traning";
        Method initalizeTitleLabel = findPrivateMethod("initalizeTitleLabel");
        if (initalizeTitleLabel == null)
            fail("Mehtod not found");
        initalizeTitleLabel.setAccessible(true);
        try {
            resultLabel = (JLabel) initalizeTitleLabel.invoke(mainMenu);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
            fail(exception.getCause());
        }
        assertEquals(new Font("Times New Roman", Font.ITALIC, 24), resultLabel.getFont());
        assertEquals(title, resultLabel.getText());
    }

    /**
     * Method seeks for method which name matches with the passed arguemnt.  
     * @param name Name of the method to find
     * @return Reference to the Method object if there is method with such a name. In other case method returns null.
     */
    private Method findPrivateMethod(String name) {
        for (Method method : privMethods) {
            if (method.getName().equals(name)) 
                return method;
        }
        return null;
    }
}